package io.swagger.api;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.swagger.Blockchain;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Details;
import io.swagger.model.Ticket;
import io.swagger.model.TicketCreation;
import io.swagger.model.PaymentModels.PaymentResponse;

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
                
        if (accept != null && accept.contains("application/json")) {
           /*
            try {
                final String url = "http://192.168.85.208/payments/";

                RestTemplate restTemplate = new RestTemplate();
                
                HttpHeaders headers = new HttpHeaders();
                
                Details payment_body = body.getPayment();
                
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", (String) payment_body.get("auth_token"));
                
                payment_body.remove("auth_token");

                //System.out.println(payment_body);
                HttpEntity<Details> request;
                
                request = new HttpEntity<>(payment_body, headers);
     
                try {
                    ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(url, request, PaymentResponse.class);
                    System.out.println(response);
                    PaymentResponse response_body = response.getBody();
                    
                    if(response_body.getCode().equals("201")){
                        Details request_body = new Details();
                        request_body.put("reference", payment_body.get("reference"));
                        request_body.put("amount", 45);
                        System.out.println(request_body);
                        request = new HttpEntity<>(request_body, headers);
                        System.out.println(url+response_body.getMessage().get("id")+"/transactions/");
                        ResponseEntity<String> response2 = restTemplate.postForEntity(url+response_body.getMessage().get("id")+"/transactions/",
                         request, String.class);
                        
                        System.out.println(response2);
                    }

                
                } catch (HttpServerErrorException e) {
                    System.out.println(e);
                    return new ResponseEntity<Ticket>(HttpStatus.INTERNAL_SERVER_ERROR);
                } catch (HttpClientErrorException e) {
                    System.out.println(e);
                    return new ResponseEntity<Ticket>(HttpStatus.UNAUTHORIZED);
                }

            }finally{}
            */
            try {
               
                Ticket newTicket = new Ticket(body.getDetails(), "inactive", chain.getLatestBlockHash(), body.getSecret());
                chain.addBlock(newTicket.ticketId());
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
