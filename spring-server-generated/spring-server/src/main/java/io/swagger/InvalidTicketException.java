package io.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE, reason="Ticket isn't valid")  // 406
public class InvalidTicketException extends RuntimeException {}