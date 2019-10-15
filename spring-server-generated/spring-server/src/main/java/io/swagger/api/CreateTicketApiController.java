package io.swagger.api;

import io.swagger.model.Ticket;
import io.swagger.model.TicketCreation;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
      
                byte[] encodedhash = digest.digest();
                /*+new Timestamp(new Date().getTime()).toString()+*/
                return new ResponseEntity<Ticket>(objectMapper.readValue("{  \"details\" : { "+body.getDetails()+"},  \"ticket_id\" : 0,  \"hash\" : \""+bytesToHex(encodedhash)+"\",\"timestamp\" : \"2019-10-14T10:03:57.756Z\",\"status\" : \"inactive\"}",
                        Ticket.class), HttpStatus.CREATED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Ticket>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                log.error("Hash generation problem", e);
                return new ResponseEntity<Ticket>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        System.out.println("Testing");
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }

    //REMOVE FUNCTION FROM THIS PLACE
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
