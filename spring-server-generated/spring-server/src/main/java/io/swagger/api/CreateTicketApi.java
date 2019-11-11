/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.9).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Ticket;
import io.swagger.model.TicketCreation;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

@Api(value = "create", description = "the createTicket API")
public interface CreateTicketApi {

    @ApiOperation(value = "Create a new ticket", nickname = "createTicket", notes = "", response = Ticket.class, tags={ "ticket", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ticket created successfully", response = Ticket.class),
        @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/create",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Ticket> createTicket(@ApiParam(value = "Ticket information needed to create a ticket" ,required=true )  @Valid @RequestBody TicketCreation body);

    @ApiOperation(value = "Create a new ticket", nickname = "createTicket", notes = "", response = String.class, tags={ "ticket", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ticket created successfully", response = String.class),
        @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/createAuth",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<String> createTicketAuth(@ApiParam(value = "Ticket information needed to create a ticket" ,required=true )  @Valid @RequestBody TicketCreation body);


}
