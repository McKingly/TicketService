package io.swagger.model;

import java.util.HashMap;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.springframework.validation.annotation.Validated;

/**
 * Details
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-15T09:40:00.324Z")

public class Details extends HashMap<String, Object>  {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }
  
  @Override
  public String toString() {
    return super.toString();
    /* StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("}");
    return sb.toString(); */
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

