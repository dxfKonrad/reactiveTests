package com.kb.reactiveTests.exception;

import org.springframework.web.client.HttpClientErrorException;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
