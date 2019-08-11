package uk.co.punishell.insideview.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
public class BindingFormException extends RuntimeException {

    public BindingFormException(String message) {
        super(message);
    }
}
