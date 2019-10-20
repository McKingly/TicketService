package io.swagger.api;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.Auxiliary;
import io.swagger.annotations.ApiParam;
import io.swagger.model.ApiRequest;
import io.swagger.model.Ticket;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

@Controller
public class ValidateTicketApiController implements ValidateTicketApi {

    private static final Logger log = LoggerFactory.getLogger(ValidateTicketApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ValidateTicketApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Ticket> validateTicket(@ApiParam(value = "Information needed to create a ticket" ,required=true )  @Valid @RequestBody ApiRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            byte[] salt = body.getSecret().getBytes(StandardCharsets.UTF_8);
            Ticket ticket = body.getTicket();

            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
                digest.update(salt);
                
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(ticket.getDetails());
                out.flush();

                // Details
                //System.out.println(Auxiliary.bytesToHex(byteOut.toByteArray()));
                digest.update(byteOut.toByteArray());

                // Ticket ID
                //System.out.println(byteOut.toByteArray());
                digest.update(ByteBuffer.allocate(4).putInt(ticket.getTicketId()).array());

                // Timestamp
                objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
                System.out.println(" \n Timestamp : "+ ticket.getTimestamp().toString());
                //System.out.println(Auxiliary.bytesToHex(ticket.getTimestamp().toString().getBytes()));
                digest.update(ticket.getTimestamp().toString().getBytes());
                
                //Status
                //System.out.println(" \n Status");
                //System.out.println(Auxiliary.bytesToHex(ticket.getStatus().getBytes()));
                digest.update(ticket.getStatus().getBytes());

                // Check hash
                byte[] encodedhash = digest.digest();
                //System.out.println(" \n FINAL");
                System.out.println(Auxiliary.bytesToHex(encodedhash));

                if (!Auxiliary.bytesToHex(encodedhash).equals(ticket.getHash()))
                    throw new Exception("Invalid ticket.");

            } catch (Exception e) {
                //TODO: handle exception
                System.out.print(e);
                return new ResponseEntity<Ticket>(HttpStatus.PRECONDITION_FAILED);
            }
            //digest = MessageDigest.getInstance("SHA-256");
                //digest.update(salt);

                //ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                //ObjectOutputStream out = new ObjectOutputStream(byteOut);
                //out.writeObject(body.getDetails());
                //out.flush();

                //digest.update(byteOut.toByteArray());
      
                //byte[] encodedhash = digest.digest();

            body.getTicket().setStatus("validated");
            return new ResponseEntity<Ticket>(body.getTicket(), HttpStatus.ACCEPTED);
        }
        System.out.println("TESTING");
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }

}
