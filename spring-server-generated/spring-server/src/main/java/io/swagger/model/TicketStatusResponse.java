package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * TicketList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

public class TicketStatusResponse   {

  @JsonProperty("ticket")
  private List<Details> tickets = new ArrayList<Details>();

  public TicketStatusResponse ticket_array(ArrayList<Details> tickets) {
    this.tickets = tickets;
    return this;
  }

  /**
   * Get ticket array
   * @return ticket array
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Details> getTicket() {
    return tickets;
  }

  public void setTicket(List<Details> tickets) {
    this.tickets = tickets;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketStatusResponse ticketList = (TicketStatusResponse) o;
    return Objects.equals(this.tickets, ticketList.tickets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tickets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    sb.append("tickets: [ \n");
    for (Details d : tickets){
      sb.append(d).append("\n");
    }
    sb.append("]}");
    //System.out.println(sb);
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

