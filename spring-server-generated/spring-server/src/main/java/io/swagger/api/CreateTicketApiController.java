package io.swagger.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.Auxiliary;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Ticket;
import io.swagger.model.TicketCreation;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-14T21:38:57.474Z")

@Controller
public class CreateTicketApiController implements CreateTicketApi {

    private static final Logger log = LoggerFactory.getLogger(CreateTicketApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public CreateTicketApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Ticket> createTicket(
            @ApiParam(value = "Ticket information needed to create a ticket", required = true) @Valid @RequestBody TicketCreation body) {
        
        String accept = request.getHeader("Accept");
        
        if (accept != null && accept.contains("application/json")) {
            byte[] salt = body.getSecret().getBytes(StandardCharsets.UTF_8);
            MessageDigest digest;

            try {
                
                digest = MessageDigest.getInstance("SHA-256");
                digest.update(salt);

                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(body.getDetails());
                out.flush();

                digest.update(byteOut.toByteArray());
                
                int id = 45;
                digest.update(ByteBuffer.allocate(4).putInt(id).array());

                String timestamp = OffsetDateTime.now().toString();
                digest.update(timestamp.getBytes());
                
                String status = "inactive";
                digest.update(status.getBytes());

                byte[] encodedhash = digest.digest();

                return new ResponseEntity<Ticket>(
                    objectMapper.readValue("{ \"details\" : " + body.getDetails() + 
                        ", \"ticket_id\" : \"" + id + "\"" +
                        ", \"timestamp\" : \""+ timestamp + "\"" +
                        ", \"status\" : \"" + status + "\"" +
                        ", \"hash\" : \"" + Auxiliary.bytesToHex(encodedhash) + "\"" +
                        "}",Ticket.class), HttpStatus.CREATED);

            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Ticket>(HttpStatus.INTERNAL_SERVER_ERROR);

            } catch (NoSuchAlgorithmException e) {
                log.error("Hash generation problem", e);
                return new ResponseEntity<Ticket>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }
}
