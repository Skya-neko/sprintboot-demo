package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends APIResponseException {

    public NotFoundException(String msg) {
        super(msg);
        this.setStatusCode(404);
    }
}
