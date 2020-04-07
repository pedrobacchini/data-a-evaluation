package com.github.pedrobacchini.exception;

import org.springframework.http.HttpStatus;

public class FileException extends ApiException {

    private static final long serialVersionUID = -1772320525171757808L;

    public FileException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
