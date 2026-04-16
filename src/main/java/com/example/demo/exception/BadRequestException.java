package com.example.demo.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

/*
Purpose:
 - separate client-side input errors from other kinds of failures.

For cases where:
 - request body is null
 - title is null or blank
 - content is null or blank
 - userId or journalId is null

 */