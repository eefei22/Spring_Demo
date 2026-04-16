package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}


/*
Purpose:
 - explicitly represent missing resource scenarios

Case:
 - journal does not exist for that user

 */