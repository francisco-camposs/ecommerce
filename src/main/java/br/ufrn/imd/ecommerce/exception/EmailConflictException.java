package br.ufrn.imd.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailConflictException extends RuntimeException {

    public EmailConflictException(String message) {
        super(message);
    }

    public EmailConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}