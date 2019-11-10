package io.swagger.model;

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

public class TicketCreation   {
  @JsonProperty("secret")
  private String secret = null;

  @JsonProperty("details")
  private Details details = null;

  @JsonProperty("payment")
  private Details payment = null;


  public TicketCreation secret(String secret) {
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

  public TicketCreation details(Details details) {
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

  public TicketCreation payment(Details payment) {
    this.payment = payment;
    return this;
  }

  /**
   * Get details
   * @return details
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Details getPayment() {
    return payment;
  }

  public void setPayment(Details payment) {
    this.payment = payment;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketCreation ticketCreation = (TicketCreation) o;
    return Objects.equals(this.secret, ticketCreation.secret) &&
        Objects.equals(this.details, ticketCreation.details) &&
        Objects.equals(this.payment, ticketCreation.payment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secret, details, payment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    secret: ").append(toIndentedString(secret)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
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

