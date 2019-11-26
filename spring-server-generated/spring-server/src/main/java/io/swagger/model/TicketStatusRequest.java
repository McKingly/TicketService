package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * TicketStatusResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

public class TicketStatusRequest {
  @JsonProperty("secret")
  private String secret = null;

  @JsonProperty("tickets")
  private Details[] ticket_array = null;

  public TicketStatusRequest secret(String secret) {
    this.secret = secret;
    return this;
  }

  /**
   * Get secret
   * @return secret
  **/
  @ApiModelProperty(example = "secret", value = "")


  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public TicketStatusRequest ticket(Details[] ticket_array) {
    this.ticket_array = ticket_array;
    return this;
  }

  /**
   * Get ticket
   * @return ticket
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Details[] getTicketArray() {
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
    TicketStatusRequest response = (TicketStatusRequest) o;
    return Objects.equals(this.secret, response.secret) &&
        Objects.equals(this.ticket_array, response.ticket_array);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secret, ticket_array);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n{\n");
    sb.append("    secret: ").append(toIndentedString(secret)).append("\n");
    sb.append("    ticket: [\n");
    for( Details ticket : ticket_array) {
      sb.append("\t\t"+toIndentedString(ticket)).append("\n");
    }
    sb.append("    ]\n}");
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

