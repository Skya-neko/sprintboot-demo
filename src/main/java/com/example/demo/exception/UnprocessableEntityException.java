package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends APIResponseException {

    public UnprocessableEntityException(String msg) {
        super(msg);
        this.setStatusCode(422);
    }
}
