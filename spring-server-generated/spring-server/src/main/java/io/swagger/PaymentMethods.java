package io.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.swagger.model.Details;
import io.swagger.model.createTicketAuthResponse;
import io.swagger.model.PaymentModels.PaymentResponse;

import java.util.LinkedHashMap;

public class PaymentMethods {
  
  private static final Logger log = LoggerFactory.getLogger(PaymentMethods.class);

  private static final String url = "http://192.168.85.208/payments/";

  private static RestTemplate restTemplate = new RestTemplate();
  private static HttpHeaders headers;
  private static HttpEntity<Details> request;
  
  private static ResponseEntity<PaymentResponse> response;
  private static PaymentResponse responseBody;

  private static int tmp = 0; 

  public static PaymentResponse createPayment(String authToken, String sellerName) throws Exception{
    //Constructing the headers of the request
    headers = new HttpHeaders(); 
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", authToken);
                
    //Constructing the body of the request
    Details requestBody = new Details();
    
    requestBody.put("request_id", Integer.toString(tmp++));

    switch(sellerName){
      case("CP"):
        requestBody.put("seller_id", PaymentMethods.getSellerId("CP")); // CP
        break;
      case("Transdev"):
        requestBody.put("seller_id", PaymentMethods.getSellerId("Transdev")); // Transdev
        break;
      case("metro"):
        requestBody.put("seller_id", PaymentMethods.getSellerId("metro")); // Transdev
        break;
      default:
        requestBody.put("seller_id", PaymentMethods.getSellerId("Transdev")); // Transdev
    }

    requestBody.put("reference", "Travel ticket");
    requestBody.put("currency", "EUR");
        
    //Constructing the request
    request = new HttpEntity<Details>(requestBody, headers);

    log.info("POST request: " + url);
    response = restTemplate.postForEntity(url, request, PaymentResponse.class);
    responseBody = response.getBody();
    
    log.info("Response code : " + responseBody.getCode());
    return responseBody;
  }

  public static PaymentResponse addTransactionToPayment(String authToken, int amount, String reference, String paymentId) throws Exception{
    //Constructing the headers of the request
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", authToken);
    
    reference = (reference != null ) ? reference : "Travel Ticket";

    Details requestBody = new Details();
    requestBody.put("amount", amount);
    requestBody.put("reference", reference);

    request = new HttpEntity<Details>(requestBody, headers);

    log.info("POST request: " + url+paymentId+"/transactions");
    response = restTemplate.postForEntity(url+paymentId+"/transactions",
        request, PaymentResponse.class);

    responseBody = response.getBody();
    log.info("Response code : " + responseBody.getCode());

    return responseBody;
  }

  public static createTicketAuthResponse requestAuth(String authToken, int amount, String reference, String paymentId) throws Exception{
    /*
    //Constructing the headers of the request
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", authToken);
    
    reference = (reference != null ) ? reference : "Travel Ticket";

    Details requestBody = new Details();
    requestBody.put("amount", amount);
    requestBody.put("reference", reference);

    request = new HttpEntity<Details>(requestBody, headers);

    */

    log.info("POST request: " + url+paymentId+"/authorize");
    response = restTemplate.postForEntity(url+paymentId+"/authorize",request, PaymentResponse.class);
    
    responseBody = response.getBody();
    log.info("Response received with code : " + responseBody.getCode());

    createTicketAuthResponse data = new createTicketAuthResponse();

    data.url((String)responseBody.getMessage().get("message"));
    data.paymentId(paymentId);

    System.out.println(responseBody);

    return data;
  }

  public static PaymentResponse confirmAuthorization(String authToken, String paymentId) throws Exception{                    
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", authToken);                    

    request = new HttpEntity<>(headers);
    
    log.info("POST request: " + url+paymentId);
    //response = restTemplate.exchange(url+paymentId, HttpMethod.GET, request, PaymentResponse.class);
    response = restTemplate.exchange(url+"30a9de2a-16e4-49bc-a1c7-5e4afdf407a2", HttpMethod.GET, request, PaymentResponse.class);

    responseBody = response.getBody();
    log.info("Response received with code : " + responseBody.getCode());
    
    /*
    Details paymentData = new Details();
    paymentData.put("reference", value);
    paymentData.put("amount", 45);*/

    return responseBody;
  }

  public static PaymentResponse executePayment(String authToken, String paymentId) throws Exception {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", authToken); 

    request = new HttpEntity<>(headers);

    log.info("POST request: " + url+paymentId+"/execute");
    response = restTemplate.postForEntity(url+paymentId+"/execute", request, PaymentResponse.class);
    //response = restTemplate.postForEntity(url+"30a9de2a-16e4-49bc-a1c7-5e4afdf407a2"+"/execute", request, PaymentResponse.class);
    responseBody = response.getBody();
    log.info("Response received with code : " + responseBody.getCode());

    return responseBody;
  }

  private static String getSellerId(String sellerName){
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<PaymentResponse> response = restTemplate.getForEntity("http://192.168.85.208/account/trans", PaymentResponse.class);
    LinkedHashMap r = (LinkedHashMap) response.getBody().getMessage().get("carriers");
    return (String) r.get(sellerName);

  }

}