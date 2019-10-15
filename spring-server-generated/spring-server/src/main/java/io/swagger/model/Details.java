package io.swagger.model;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Details
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

public class Details implements Serializable {
  @JsonProperty("purpose")
  private String purpose = null;

  @JsonProperty("company")
  private String company = null;

  @JsonProperty("seat")
  private String seat = null;

  @JsonProperty("destinations")
  private String destinations = null;

  @JsonProperty("price")
  private Long price = null;

  public Details purpose(String purpose) {
    this.purpose = purpose;
    return this;
  }

  /**
   * Get purpose
   * @return purpose
  **/
  @ApiModelProperty(example = "Transportation ticket", value = "")


  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public Details company(String company) {
    this.company = company;
    return this;
  }

  /**
   * Get company
   * @return company
  **/
  @ApiModelProperty(example = "Transdev", value = "")


  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public Details seat(String seat) {
    this.seat = seat;
    return this;
  }

  /**
   * Get seat
   * @return seat
  **/
  @ApiModelProperty(example = "A45", value = "")


  public String getSeat() {
    return seat;
  }

  public void setSeat(String seat) {
    this.seat = seat;
  }

  public Details destinations(String destinations) {
    this.destinations = destinations;
    return this;
  }

  /**
   * Get destinations
   * @return destinations
  **/
  @ApiModelProperty(example = "Aveiro", value = "")


  public String getDestinations() {
    return destinations;
  }

  public void setDestinations(String destinations) {
    this.destinations = destinations;
  }

  public Details price(Long price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(value = "")


  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Details details = (Details) o;
    return Objects.equals(this.purpose, details.purpose) &&
        Objects.equals(this.company, details.company) &&
        Objects.equals(this.seat, details.seat) &&
        Objects.equals(this.destinations, details.destinations) &&
        Objects.equals(this.price, details.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(purpose, company, seat, destinations, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    sb.append("\n \"purpose\": \"").append(toIndentedString(purpose)).append("\",\n");
    sb.append(" \"company\": \"").append(toIndentedString(company)).append("\",\n");
    sb.append(" \"seat\": \"").append(toIndentedString(seat)).append("\",\n");
    sb.append(" \"destinations\": \"").append(toIndentedString(destinations)).append("\",\n");
    sb.append(" \"price\": \"").append(toIndentedString(price)).append("\"\n");
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

