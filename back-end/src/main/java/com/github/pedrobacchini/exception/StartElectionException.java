package com.github.pedrobacchini.exception;

import org.springframework.http.HttpStatus;

public class StartElectionException extends ApiException {

    private static final long serialVersionUID = 7678537522823950924L;

    public StartElectionException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
