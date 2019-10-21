package io.swagger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

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
      return ticket;
    } catch (NoSuchAlgorithmException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public void addBlock(Ticket block){
    chain.add(block);
  }

  public String getLatestBlockHash(){
    return chain.getLast().getHash();
  }

  public String toString(){
    return chain.toString();
  }

}