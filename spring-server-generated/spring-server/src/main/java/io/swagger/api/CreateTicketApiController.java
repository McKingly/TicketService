package io.swagger.api;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.Blockchain;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Ticket;
import io.swagger.model.TicketCreation;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

@Controller
public class CreateTicketApiController implements CreateTicketApi {

    private static final Logger log = LoggerFactory.getLogger(CreateTicketApiController.class);
    
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @Autowired
    private Blockchain chain;

    @org.springframework.beans.factory.annotation.Autowired
    public CreateTicketApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Ticket> createTicket(
            @ApiParam(value = "Ticket information needed to create a ticket", required = true) @Valid @RequestBody TicketCreation body) {
        
        String accept = request.getHeader("Accept");
        
        System.out.println(chain.toString());

        if (accept != null && accept.contains("application/json")) {
           
            try {
               
                Ticket newTicket = new Ticket(body.getDetails(), "inactive", chain.getLatestBlockHash(), body.getSecret());
                chain.addBlock(newTicket);
                return new ResponseEntity<Ticket>( newTicket, HttpStatus.CREATED);

            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Ticket>(HttpStatus.INTERNAL_SERVER_ERROR);

            } catch (NoSuchAlgorithmException e) {
                log.error("Hash generation problem", e);
                return new ResponseEntity<Ticket>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }
}
