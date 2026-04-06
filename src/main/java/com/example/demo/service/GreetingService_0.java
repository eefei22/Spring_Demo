package com.example.demo.service;
import org.springframework.stereotype.Service;


@Service
public class GreetingService_0 {

    public String getGreeting() {
        return "Good day, welcome to Spring Boot.";
    }
}


// Without Dependency Injection, need to create the object manually