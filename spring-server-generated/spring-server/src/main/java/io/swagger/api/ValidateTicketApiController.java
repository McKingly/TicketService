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

    public ResponseEntity<Ticket> validateTicket(@ApiParam(value = "Information needed to create a ticket" ,required=true )  @Valid @RequestBody ApiRequest body) {
        String accept = request.getHeader("Accept");

        if (accept != null && accept.contains("application/json")) {

            Ticket ticket = body.getTicket();
            
            try {
                objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
                String hash = Auxiliary.computeHash(ticket.getTicketId(), ticket.getTimestamp().toString(), ticket.getDetails(), 
                    ticket.getStatus(), ticket.getPreviousHash(), body.getSecret());
                
                // Checks to see if the 
                if(hash.equals(ticket.getHash())){
                    log.info("Ticket is real.");

                    int ticketId = ticket.getTicketId();

                    Ticket block = chain.getBlockReverse(ticketId);

                    if(block.getStatus().equalsIgnoreCase("expired")){
                        log.error("Ticket has expired");
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                    else if(block.getStatus().equalsIgnoreCase("valid")){
                        if( Auxiliary.difOffsetDateTime(block.getTimestamp(), org.threeten.bp.OffsetDateTime.now()) < 0){
                            log.error("Ticket has expired");
                            Ticket expiredTicket = new Ticket(ticket.getDetails(),"expired", chain.getLatestBlockHash(),body.getSecret());
                            chain.addBlock(expiredTicket.ticketId(ticket.getTicketId()));
                            return new ResponseEntity<>(HttpStatus.OK);
                        }
                        else {
                            log.info("Ticket has already been validated.");
                            return new ResponseEntity<>(HttpStatus.OK);
                        }
                    }
                    else if(block.getStatus().equalsIgnoreCase("inactive")){
                        log.info("Ticket has been validated.");
                        Ticket validatedTicket = new Ticket(ticket.getDetails(),"valid", chain.getLatestBlockHash(),body.getSecret());
                        chain.addBlock(validatedTicket.ticketId(ticket.getTicketId()));
                        return new ResponseEntity<Ticket>(validatedTicket, HttpStatus.ACCEPTED);
                    }
                    else{
                        throw new InvalidTicketException();
                    }
                }
                else{
                    throw new InvalidTicketException();
                }
            }
            catch (InvalidTicketException e) {
                log.error("Ticket is invalid:",  e.toString());
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } catch (NoSuchAlgorithmException e) {
                log.error("Error ocurred: ",  e.toString());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (IOException e) {
                log.error("VALIDATE TICKET",  e.toString());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
        }
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }
}

