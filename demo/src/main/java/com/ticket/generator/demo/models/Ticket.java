package com.ticket.generator.demo.models;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ticket {

  private int ticket_id = 1;
  private String timestamp;
  private Map<String, String> details = new HashMap<String, String>();
  private String hash;
  private String status = "inactive";

  public void addResponse(Map map){
    //details.put(key, value);
    details.putAll(map);
  }

  public void setHash(String hash){
    this.hash = hash;
  }

  public void setTimestamp(String timestamp){
    this.timestamp = timestamp;
  }
}