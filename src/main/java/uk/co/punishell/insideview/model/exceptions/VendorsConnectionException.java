package uk.co.punishell.insideview.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class VendorsConnectionException extends RuntimeException {

    public VendorsConnectionException(String message) {super(message);}
}
