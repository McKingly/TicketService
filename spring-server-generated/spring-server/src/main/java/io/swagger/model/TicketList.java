package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Ticket;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TicketList
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

public class TicketList   {

  @JsonProperty("tickets")
  private Details[] ticket_array = null;

  public TicketList ticket_array(Details[] ticket_array) {
    this.ticket_array = ticket_array;
    return this;
  }

  /**
   * Get ticket array
   * @return ticket array
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Details[] getTicket() {
    return ticket_array;
  }

  public void setTicket(Details[] ticket_array) {
    this.ticket_array = ticket_array;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketList ticketList = (TicketList) o;
    return Objects.equals(this.ticket_array, ticketList.ticket_array);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticket_array);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    System.out.println("HEADACHE");
    //sb.append("{\n");
    /*sb.append("tickets: [").append(toIndentedString(ticket_array)).append("\n");
    for (Details d : ticket_array){
      //sb.append(toIndentedString(d)).append("\n");
    }
    sb.append("]}");
    */
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

