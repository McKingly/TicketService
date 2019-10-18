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

public class Details extends HashMap<String, String>  {

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
    return new JSONObject(this).toString();
  }
}

