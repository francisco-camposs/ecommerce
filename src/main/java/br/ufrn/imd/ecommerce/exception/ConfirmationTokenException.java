package br.ufrn.imd.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConfirmationTokenException extends RuntimeException {

    public ConfirmationTokenException(String message) {
        super(message);
    }

    public ConfirmationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}