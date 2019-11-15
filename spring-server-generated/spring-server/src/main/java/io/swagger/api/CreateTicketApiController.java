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
import org.springframework.http.HttpMethod;
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
import io.swagger.model.createTicketAuthResponse;
import io.swagger.model.createTicketRequest;
import io.swagger.model.createTicketResponse;
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

    public ResponseEntity<createTicketResponse> createTicket(
            @ApiParam(value = "Ticket information needed to create a ticket", required = true) @Valid @RequestBody createTicketRequest body) {
        
        String accept = request.getHeader("Accept");
        String authToken = request.getHeader("auth_token");

        if (accept != null && accept.contains("application/json") && authToken != null) {
            try {

                final String url = "http://192.168.85.208/payments/";
                    
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", authToken);                    

                RestTemplate restTemplate = new RestTemplate();
                    
                HttpEntity entity = new HttpEntity<>(headers);
                
                /*  
                ResponseEntity<PaymentResponse> response;
                response = restTemplate.exchange(url+5, HttpMethod.GET, entity, PaymentResponse.class);

                PaymentResponse responseBody = response.getBody();
                */

                /*
                Details paymentData = new Details();
                paymentData.put("reference", value);
                paymentData.put("amount", 45);*/

                /*
                if(Integer.parseInt(responseBody.getCode()) == 201){
                    response = restTemplate.postForEntity(url+5+"/execute", request, PaymentResponse.class);
                    
                    responseBody = response.getBody();
                    if(Integer.parseInt(responseBody.getCode()) == 201){
                        
                        */ 
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
            }
        }
        return new ResponseEntity<createTicketResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<createTicketAuthResponse> createTicketAuth(
        @ApiParam(value = "Ticket information needed to create a ticket", required = true) @Valid @RequestBody createTicketRequest body) {
        
        String accept = request.getHeader("Accept");
        String authToken = request.getHeader("auth_token");

        if (accept != null && accept.contains("application/json") && authToken != null) {
            try {
                final String url = "http://192.168.85.208/payments/";

                RestTemplate restTemplate = new RestTemplate();
                
                HttpHeaders headers = new HttpHeaders();
                
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", (String) authToken);
                            
                Details paymentBody = new Details();
                paymentBody.put("currency", "EUR");
                paymentBody.put("seller_id", "d452c84b-19b1-4194-898b-46bb72a773fb");
                paymentBody.put("request_id",1);
                paymentBody.put("reference", "Travel");
                
                HttpEntity<Details> request;
                
                request = new HttpEntity<>(paymentBody, headers);

                ResponseEntity<PaymentResponse> response;
                PaymentResponse responseBody;
                    
                response = restTemplate.postForEntity(url, request, PaymentResponse.class);
                responseBody = response.getBody();
                
                if(responseBody.getCode().equals("201")){
                    Details requestBody = new Details();
                    requestBody.put("reference", paymentBody.get("reference"));
                    requestBody.put("amount", 45);

                    request = new HttpEntity<>(requestBody, headers);
                    String payment_id = (String)responseBody.getMessage().get("id");
                    response = restTemplate.postForEntity(url+responseBody.getMessage().get("id")+"/transactions",
                        request, PaymentResponse.class);

                    responseBody = response.getBody();
                    if(responseBody.getCode().equals("201")){
                        response = restTemplate.postForEntity(url+payment_id+"/authorize",request, PaymentResponse.class);
                        createTicketAuthResponse new_response = new createTicketAuthResponse();

                        new_response.url((String)response.getBody().getMessage().get("message"));
                        new_response.paymentId(Integer.parseInt(payment_id));

                        return new ResponseEntity<createTicketAuthResponse>(new_response, HttpStatus.OK);
                    }
                }

            } catch (HttpServerErrorException e) {
                System.out.println(e);
                return new ResponseEntity<createTicketAuthResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (HttpClientErrorException e) {
                System.out.println(e);
                return new ResponseEntity<createTicketAuthResponse>(HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<createTicketAuthResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
}
