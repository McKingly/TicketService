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

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import io.swagger.Blockchain;
import io.swagger.PaymentMethods;
import io.swagger.annotations.ApiParam;

import io.swagger.model.*;
import io.swagger.model.PaymentModels.PaymentResponse;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

@Controller
public class CreateTicketApiController implements CreateTicketApi {

    private static final Logger log = LoggerFactory.getLogger(CreateTicketApiController.class);
    
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    private int temp = 0;

    @Autowired
    private Blockchain chain;
    
    @org.springframework.beans.factory.annotation.Autowired
    public CreateTicketApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    
    public ResponseEntity<createTicketAuthResponse> createTicketAuth(
        @ApiParam(value = "Ticket information needed to create a ticket", required = true) @Valid @RequestBody createTicketRequest body) {
        
        log.info("URL: "+ request.getRequestURI());

        String accept = request.getHeader("Accept");
        String authToken = request.getHeader("AuthToken");

        log.info("Pre-conditions being tested.");

        if (accept != null && accept.contains("application/json") && authToken != null) {
            try {
                log.info("Header conditions accepted.");
                // Creates a payment associated with a seller 
                PaymentResponse response = PaymentMethods.createPayment(authToken);

                if(response.getCode().equals("201")){

                    String paymentId = (String)response.getMessage().get("id");

                    for (int i = 0; i < body.getDetails().size(); i++){
                        response = PaymentMethods.addTransactionToPayment(authToken,5,null,paymentId);
                        if(!response.getCode().equals("201")) 
                            throw new HttpServerErrorException(null, "Couldn't create a transaction.");
                    }
                    // Send response 
                    
                    return new ResponseEntity<createTicketAuthResponse>(PaymentMethods.requestAuth(authToken,5,null,paymentId), HttpStatus.OK);
                }

            } catch (HttpServerErrorException e) {
                System.out.println(e);
                return new ResponseEntity<createTicketAuthResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (HttpClientErrorException e) {
                System.out.println(e);
                return new ResponseEntity<createTicketAuthResponse>(HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                System.out.println(e);
                return new ResponseEntity<createTicketAuthResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<createTicketAuthResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    public ResponseEntity<createTicketResponse> createTicket(
            @ApiParam(value = "Ticket information needed to create a ticket", required = true) @Valid @RequestBody createTicketRequest body) {
        
        log.info("URL: "+ request.getRequestURI());

        log.info("Message Body: \n"+body.toString());
        
        String accept = request.getHeader("Accept");
        String authToken = request.getHeader("AuthToken");        
        String paymentId = request.getHeader("paymentId");
        
        log.info("Pre-conditions being tested.");

        if (accept != null && accept.contains("application/json") && authToken != null && paymentId != null) {
            try {
                PaymentResponse response;
                //PaymentResponse response = PaymentMethods.confirmAuthorization(authToken, paymen);
                
                //if(response.getCode().equals("201")){
                    
                    //response = PaymentMethods.executePayment(authToken, "4");
                    
                    //if(response.getCode().equals("201")){
                        
                        createTicketResponse responseList = new createTicketResponse();
                        
                        //Creating tickets based on the details parameter 
                        
                        for (Details d : body.getDetails()){
                            //Creating new ticket 
                            Ticket newTicket = new Ticket(d, "inactive", chain.getLatestBlockHash(), body.getSecret());
                            
                            //Adding them to the blockchain
                            chain.addBlock(newTicket.ticketId());

                            //Adding them to the response
                            responseList.addTicket(newTicket);
                        }

                        return new ResponseEntity<createTicketResponse>(responseList, HttpStatus.CREATED);
                    //}
            
                //}

            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<createTicketResponse>(HttpStatus.INTERNAL_SERVER_ERROR);

            } catch (NoSuchAlgorithmException e) {
                log.error("Hash generation problem", e);
                return new ResponseEntity<createTicketResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            
            } catch (Exception e) {
                log.error("", e);
                e.printStackTrace();
                return new ResponseEntity<createTicketResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<createTicketResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
}
