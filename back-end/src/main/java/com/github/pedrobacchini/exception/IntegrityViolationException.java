package com.github.pedrobacchini.exception;

import org.springframework.http.HttpStatus;

public class IntegrityViolationException extends ApiException {

    private static final long serialVersionUID = -6262484321076285244L;

    public IntegrityViolationException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
