package io.swagger.api;

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
import io.swagger.model.ApiRequest;
import io.swagger.model.Ticket;
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
        //System.out.println(body);
        if (accept != null && accept.contains("application/json")) {
            //byte[] salt = body.getSecret().getBytes(StandardCharsets.UTF_8);
            //MessageDigest digest;
            
            Ticket ticket = body.getTicket();
            
            try {
                objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
                String hash = Auxiliary.computeHash(ticket.getTicketId(), ticket.getTimestamp().toString(), ticket.getDetails(), 
                    ticket.getStatus(), ticket.getPreviousHash(), body.getSecret());
                if(hash.equals(ticket.getHash())){
                    System.out.println("Ticket is real.");
                    System.out.println(ticket.getStatus());
                    if(ticket.getStatus().equalsIgnoreCase("valid")){
                        System.out.println("Ticket has already been validated.");
                        return new ResponseEntity<>(null,HttpStatus.OK);
                    }
                    else{
                        System.out.print("Ticket has been validated.");
                        Ticket validatedTicket = new Ticket(ticket.getDetails(),"valid", chain.getLatestBlockHash(),body.getSecret());
                        chain.addBlock(validatedTicket);
                        return new ResponseEntity<Ticket>(validatedTicket, HttpStatus.ACCEPTED);
                    }
                }
                else{
                    throw new InvalidTicketException();
                }
            }
            catch (Exception e) {
                //TODO: handle exception
                System.out.println(e);
                return new ResponseEntity<Ticket>(HttpStatus.PRECONDITION_FAILED);
            }
        }
        System.out.println("TESTING");
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }
}
