package io.swagger.api;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.Auxiliary;
import io.swagger.Blockchain;

import io.swagger.InvalidTicketException;

import io.swagger.annotations.ApiParam;

import io.swagger.model.Ticket;
import io.swagger.model.ValidateTicketResponse;
import io.swagger.model.ApiRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

@Controller
public class ValidateTicketApiController implements ValidateTicketApi {

    private static final Logger log = LoggerFactory.getLogger(ValidateTicketApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private Blockchain chain;

    @org.springframework.beans.factory.annotation.Autowired
    public ValidateTicketApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        this.request = request;
    }

    public ResponseEntity<ValidateTicketResponse> validateTicket(
            @ApiParam(value = "Information needed to create a ticket", required = true) @Valid @RequestBody ApiRequest body) {
        
        log.info("URL: "+ request.getRequestURI());
        log.info(body.toString());
        
        String accept = request.getHeader("Accept");

        if (accept != null && accept.contains("application/json")) {
            log.info("Header conditions accepted.");

            Ticket ticket = body.getTicket();
            
            log.info(ticket.toString());

            try {
                objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            
                log.info("Trying to generate hash.");    
                String hash = Auxiliary.computeHash(ticket.getTicketId(), ticket.getTimestamp().toString(), ticket.getDetails(), 
                    ticket.getStatus(), ticket.getPreviousHash(), body.getSecret());
                
                // Checks to see if the 
                log.info("Checking to see if the hash exists.");    
                if(hash.equals(ticket.getHash())){
                    log.info("Ticket is real.");

                    int ticketId = ticket.getTicketId();

                    Ticket block = chain.getBlockReverse(ticketId);

                    ValidateTicketResponse response = new ValidateTicketResponse();

                    if(block.getStatus().equalsIgnoreCase("expired")){
                        log.error("Ticket has expired");

                        response = response.message("Ticket is expired");

                        return new ResponseEntity<ValidateTicketResponse>(response, HttpStatus.OK);
                    }
                    else if(block.getStatus().equalsIgnoreCase("valid")){
                        // If ticket has already been validated
                        // Check to see if it has expired
                        if( Auxiliary.difOffsetDateTime(block.getTimestamp(), org.threeten.bp.OffsetDateTime.now()) < 0){
                            log.error("Ticket has expired");
                            
                            //Adding new block to the blockchain
                            Ticket expiredTicket = new Ticket(ticket.getDetails(),"expired", chain.getLatestBlockHash(),body.getSecret());
                            chain.addBlock(expiredTicket.ticketId(ticket.getTicketId()));

                            response = response.message("Ticket has expired");

                            return new ResponseEntity<ValidateTicketResponse>(response, HttpStatus.OK);
                        }
                        // If it hasn't expired then it still is valid
                        else {
                            log.info("Ticket has already been validated.");

                            response = response.message("Ticket has already been validated.");

                            return new ResponseEntity<ValidateTicketResponse>(response, HttpStatus.OK);
                        }
                    }
                    else if(block.getStatus().equalsIgnoreCase("inactive")){
                        log.info("Ticket has been validated.");
                        
                        Ticket validatedTicket = new Ticket(ticket.getDetails(),"valid", chain.getLatestBlockHash(),body.getSecret());
                        chain.addBlock(validatedTicket.ticketId(ticket.getTicketId()));

                        response = response.message("Ticket was validated");

                        return new ResponseEntity<ValidateTicketResponse>(response, HttpStatus.CREATED);
                    }
                    else{
                        throw new InvalidTicketException();
                    }
                }
                else{
                    throw new InvalidTicketException();
                }
            }catch (InvalidTicketException e) {
                log.error("Ticket is invalid:",  e.toString());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }catch (NullPointerException e) {
                log.error("Bad request:",  e.toString());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e) {
                log.error("Error ocurred: ",  e.toString());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } 
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}

