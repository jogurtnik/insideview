package uk.co.punishell.insideview.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
public class VendorsException extends IOException {

    public VendorsException(String message) {super(message);}
}
