package io.swagger.model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import io.swagger.Auxiliary;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ticket
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

public class Ticket {

  private static Integer blockchainId = 0;

  private Integer blockId = null;

  @JsonProperty("ticket_id")
  private Integer ticketId = null;

  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

  @JsonProperty("details")
  private Details details = null;

  @JsonProperty("hash")
  private String hash = null;

  @JsonProperty("previousHash")
  private String previousHash = null;

  @JsonProperty("status")
  private String status = null;

  public Ticket(Details details, String status, String previousHash, String secret)
      throws NoSuchAlgorithmException, IOException {
    this.blockId = blockchainId++;
    this.ticketId = this.blockId;
    this.timestamp = OffsetDateTime.now();
    this.details = details;
    this.status = status;
    this.previousHash = previousHash;
    this.hash = Auxiliary.computeHash(this.ticketId, this.timestamp.toString(), details, status, previousHash, secret);
  }

  public Ticket(int ticketId, Details details, String status, String previousHash, String secret)
      throws NoSuchAlgorithmException, IOException {
    this.blockId = blockchainId++;
    this.ticketId = ticketId;
    this.timestamp = OffsetDateTime.now();
    this.details = details;
    this.status = status;
    this.previousHash = previousHash;
    this.hash = Auxiliary.computeHash(this.ticketId, this.timestamp.toString(), details, status, previousHash, secret);
  }

  public Ticket(){}

  public Ticket ticketId() {
    this.ticketId = this.blockId;
    return this;
  }

  public Ticket ticketId(int ticketId){
    this.ticketId = ticketId;
    return this;
  }

  /**
   * Get ticketId
   * @return ticketId
  **/
  @ApiModelProperty(value = "")

  public Integer getTicketId() {
    return ticketId;
  }

  public void setTicketId(Integer ticketId) {
    this.ticketId = ticketId;
  }


  public Ticket timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Ticket details(Details details) {
    this.details = details;
    return this;
  }

  /**
   * Get details
   * @return details
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Details getDetails() {
    return details;
  }

  public void setDetails(Details details) {
    this.details = details;
  }

  public Ticket hash(String hash) {
    this.hash = hash;
    return this;
  }

  /**
   * Get hash
   * @return hash
  **/
  @ApiModelProperty(example = "afd49088c68c2c17c329aedd6fbbe53a4d16b0ee8f187", value = "")


  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public Ticket status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(example = "inactive", value = "")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Ticket previousHash(String previousHash){
    this.previousHash = previousHash;
    return this;
  }
  
  public String getPreviousHash(){
    return previousHash;
  }

  /**
   * @param previousHash the previousHash to set
   */
  public void setPreviousHash(String previousHash) {
    this.previousHash = previousHash;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ticket ticket = (Ticket) o;
    return Objects.equals(this.ticketId, ticket.ticketId) &&
        Objects.equals(this.timestamp, ticket.timestamp) &&
        Objects.equals(this.details, ticket.details) &&
        Objects.equals(this.hash, ticket.hash) &&
        Objects.equals(this.status, ticket.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticketId, timestamp, details, hash, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" \"Block Id\": \" ").append(toIndentedString(blockId)).append("\"\n,");
    sb.append(" \"ticketId\": \" ").append(toIndentedString(ticketId)).append("\"\n,");
    sb.append(" \"timestamp\": \" ").append(toIndentedString(timestamp)).append("\"\n,");
    sb.append(" \"details\": ").append(toIndentedString(details)).append("\n,");
    sb.append(" \"status\": \" ").append(toIndentedString(status)).append("\"\n");
    sb.append(" \"previousHash\": \" ").append(toIndentedString(previousHash)).append("\"\n");
    sb.append(" \"hash\": \" ").append(toIndentedString(hash)).append("\"\n,");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

