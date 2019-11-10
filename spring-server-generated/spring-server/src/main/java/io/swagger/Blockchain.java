package io.swagger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

import io.swagger.model.Details;
import io.swagger.model.Ticket;

@Component
@org.springframework.context.annotation.Scope("singleton")
public class Blockchain {

  private LinkedList<Ticket> chain;

  @org.springframework.beans.factory.annotation.Autowired
  public Blockchain() {
    this.chain = new LinkedList<Ticket>();
    this.chain.add(this.generateGenesisBlock());
  }

  private Ticket generateGenesisBlock() {
    Ticket ticket;
    try {
      ticket = new Ticket(new Details(), "invalid", "0", "secret");
      ticket.ticketId();
      return ticket;
    } catch (NoSuchAlgorithmException | IOException e) {
      System.out.println(e);
      //e.printStackTrace();

    }
    return null;
  }

  public void addBlock(Ticket block){
    chain.add(block);
  }

  public String getLatestBlockHash(){
    return chain.getLast().getHash();
  }

  public Ticket getLatestBlock(){
    return chain.getLast();
  }

  public Ticket getBlock(int ticketId){
    for (int i = 1; i < chain.size(); i++){
      if (chain.get(i).getTicketId() == ticketId){
        return chain.get(i);
      }
    }
    return null;    
  }

  public Ticket getBlockReverse(int ticketId){
    for (int i = chain.size()-1; i > 0; i--){
      if (chain.get(i).getTicketId() == ticketId){
        return chain.get(i);
      }
    }
    return null;    
  }

  public String toString(){
    return chain.toString();
  }

}