package io.swagger.api;

import io.swagger.model.Details;
import io.swagger.model.Ticket;
import io.swagger.model.TicketStatusResponse;
import io.swagger.model.TicketStatusRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.Blockchain;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-04T16:07:00.596Z")

@Controller
public class CheckTicketStatusApiController implements CheckTicketStatusApi {

    private static final Logger log = LoggerFactory.getLogger(CheckTicketStatusApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private Blockchain chain;

    @org.springframework.beans.factory.annotation.Autowired
    public CheckTicketStatusApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<TicketStatusResponse> checkTicketStatus(
        @ApiParam(value = "Ticket information" ,required=true )  @Valid @RequestBody TicketStatusRequest body) {

        log.info("URL: "+ request.getRequestURI());

        log.info(body.toString());

        String accept = request.getHeader("Accept");
        ArrayList<Details> ticket_list = new ArrayList<Details>();
        if (accept != null && accept.contains("application/json")) {
            for (Details ticket : body.getTicketArray()){
                if(ticket.get("id")==null || ticket.get("hash")==null){
                    return new ResponseEntity<TicketStatusResponse>(HttpStatus.BAD_REQUEST);
                }
                else{
                    
                    Ticket block = chain.getBlock((Integer)ticket.get("id"));
                    if (ticket.get("hash").equals(block.getHash())){

                        block = chain.getBlockReverse((Integer)ticket.get("id"));    

                        Details d = new Details();
                        d.put("id", ticket.get("id"));
                        d.put("status", block.getStatus());
                        ticket_list.add(d);
                    } 
                }
            }

            TicketStatusResponse ticketList = new TicketStatusResponse();
            ticketList.setTicket(ticket_list);
            
            return new ResponseEntity<TicketStatusResponse>(ticketList, HttpStatus.OK);
        }
        return new ResponseEntity<TicketStatusResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
}
