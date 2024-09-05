package com.example.demo.exception;

public class APIResponseException extends RuntimeException {
    private int statusCode = 400;

    public APIResponseException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
