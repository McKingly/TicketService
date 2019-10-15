/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.9).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.ApiRequest;
import io.swagger.model.Ticket;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

@Api(value = "validateTicket", description = "the validateTicket API")
public interface ValidateTicketApi {

    @ApiOperation(value = "Checks to see if ticket is valid", nickname = "validateTicket", notes = "Multiple status values can be provided with comma separated strings", response = Ticket.class, tags={ "ticket", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ticket validated successfully", response = Ticket.class),
        @ApiResponse(code = 201, message = "Ticket is valid"),
        @ApiResponse(code = 400, message = "Invalid request"),
        @ApiResponse(code = 404, message = "Invaled ticket"),
        @ApiResponse(code = 405, message = "Ticket expired") })
    @RequestMapping(value = "/validateTicket",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Ticket> validateTicket(@ApiParam(value = "Information needed to create a ticket" ,required=true )  @Valid @RequestBody ApiRequest body);

}
