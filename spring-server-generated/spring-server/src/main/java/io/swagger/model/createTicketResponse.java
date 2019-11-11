package io.swagger.model;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Details;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * TicketCreation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

public class createTicketResponse  {

  @JsonProperty("tickets")
  private List<Ticket> tickets = new ArrayList<Ticket>();
  

  public createTicketResponse tickets(List<Ticket> tickets) {
    this.tickets = tickets;
    return this;
  }

  /**
   * Get details
   * @return details
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Ticket>  getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket>  tickets) {
    this.tickets = tickets;
  }

  public void addTicket(Ticket ticket){
    this.tickets.add(ticket);
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    createTicketResponse response = (createTicketResponse) o;
    return Objects.equals(this.tickets, response.tickets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tickets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n"); 
    sb.append("    tickets: [ ").append(toIndentedString(tickets)).append("\n");
    sb.append("]\n}");
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

