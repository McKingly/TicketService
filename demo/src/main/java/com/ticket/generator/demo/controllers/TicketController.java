package com.ticket.generator.demo.controllers;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.generator.demo.models.Ticket;
import com.tomgibara.ticket.*;
import com.tomgibara.ticket.Ticket.Granularity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;

@RestController
@RequestMapping("ticket")
public class TicketController {

  private TicketSpec spec = TicketSpec.newDefaultBuilder().setGranularity(Granularity.MILLISECOND).setOriginYear(2016)
      .setTimeZone(TimeZone.getDefault()).setHashLength(32).build();

  private TicketFactory<Void, Void> factory = TicketConfig.getDefault().withSpecifications(spec).newFactory();

  @GetMapping(value = "/createTicket", produces = "application/json")
  public String getTicket(@RequestBody Map<String, String> json) {
    System.out.println(json);

    byte[] salt = json.get("secret").getBytes(StandardCharsets.UTF_8);
    json.remove("secret");

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
      digest.update(salt);
      
      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(byteOut);
      out.writeObject(json);
      out.flush();

      digest.update(byteOut.toByteArray());
      
      byte[] encodedhash = digest.digest();
      System.out.println(bytesToHex(encodedhash));
      System.out.println(MessageDigest.isEqual(encodedhash, encodedhash));
      
      ObjectMapper mapper = new ObjectMapper();
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      
      Ticket response = new Ticket();
      response.setHash(bytesToHex(encodedhash));
      response.setTimestamp(new Timestamp(new Date().getTime()).toString());

      response.addResponse(json);
      return mapper.writeValueAsString(response);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally{

    }



    //TicketFactory<Void, Void> factory = TicketConfig.getDefault().withSpecifications(spec);//.newFactory(secret);
    //Ticket<Void, Void> ticket = factory.machine().ticket();
    //factory.decodeTicket(ticket.toString());
    

    //System.out.println(ticket);
    //System.out.println(ticket.getTimestamp());
    //System.out.println(ticket.getSequenceNumber());
    //System.out.println(factory);
    return "Error";
  }

    @PostMapping(value = "/validateTicket", produces = "application/json")
    @ResponseBody
    public boolean validateTicket(@RequestBody String json) {
      //Checks to see if the ticket is valid.
      Ticket ticket;
    try {
        ticket = new ObjectMapper().readValue(json, Ticket.class);
        System.out.println(ticket);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
      //String hash = json.get("hash");
      //String secret = json.get("secret");
      
      //boolean valide = MessageDigest.isEqual(json.get("hash"), digestb)
      
      //Checks to see if the ticket hasn't expired 
      //if (json.get("time").equalsIgnoreCase("inactive"))

      // Checks to see if the ticket is active
      // If not, activates the ticket for a specific time frame
      //if (json.get("status").equalsIgnoreCase("inactive"))
      // Else return true
      return true;
    }
 
    private static String bytesToHex(byte[] hash) {
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) hexString.append('0');
          hexString.append(hex);
      }
      return hexString.toString();
  }
}