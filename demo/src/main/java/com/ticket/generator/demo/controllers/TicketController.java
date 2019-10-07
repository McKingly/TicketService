package com.ticket.generator.demo.controllers;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.TimeZone;

import com.tomgibara.ticket.*;
import com.tomgibara.ticket.Ticket.Granularity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@RestController
@RequestMapping("ticket")
public class TicketController {
    
  private TicketSpec spec = TicketSpec.newDefaultBuilder()
  .setGranularity(Granularity.MILLISECOND)
  .setOriginYear(2016)
  .setTimeZone(TimeZone.getDefault())
  .setHashLength(32)
  .build();

  private TicketFactory<Void, Void> factory = TicketConfig.getDefault().withSpecifications(spec).newFactory();
  
  @GetMapping(value = "/createTicket", produces = "application/json")
  public String getTicket(@RequestBody Map<String, String> json) {
    System.out.println(json);
    byte[] salt = json.get("secret").getBytes(StandardCharsets.UTF_8);

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedhash = digest.digest(salt);
      System.out.println(bytesToHex(encodedhash));
      System.out.println(MessageDigest.isEqual(encodedhash, encodedhash));
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    //TicketFactory<Void, Void> factory = TicketConfig.getDefault().withSpecifications(spec);//.newFactory(secret);
    //Ticket<Void, Void> ticket = factory.machine().ticket();
    //factory.decodeTicket(ticket.toString());
    

    //System.out.println(ticket);
    //System.out.println(ticket.getTimestamp());
    //System.out.println(ticket.getSequenceNumber());
    //System.out.println(factory);
    return "Death";
  }

    @PostMapping(value = "/validateTicket", produces = "application/json")
    @ResponseBody
    public boolean validateTicket(@RequestBody Map<String, String> json, produces = MediaType.APPLICATION_JSON_VALUE) {
      //Checks to see if the ticket is valid.
      String hash = json.get("hash");
      String secret = json.get("secret");
      
      boolean valide = MessageDigest.isEqual(json.get("hash"), digestb)
      
      //Checks to see if the ticket hasn't expired 
      if (json.get("time").equalsIgnoreCase("inactive"))

      // Checks to see if the ticket is active
      // If not, activates the ticket for a specific time frame
      if (json.get("status").equalsIgnoreCase("incative"))
      // Else return true
      return valide;
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