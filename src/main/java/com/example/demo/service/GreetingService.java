package com.example.demo.service;
import com.example.demo.component.TimeComponent;
import com.example.demo.component.TextFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class GreetingService {

    private final TimeComponent timeComponent;
    private final TextFormatter textFormatter;

    public GreetingService(TimeComponent timeComponent, TextFormatter textFormatter) {
        this.timeComponent = timeComponent;
        this.textFormatter = textFormatter;
    }

    public String getGreeting() {
        return textFormatter.format(
                "Good " + timeComponent.getPartOfDay() + ", welcome to Spring Boot.");
    }






    @Value("${app.greeting.message}")
    private String message;
    public String getConfigGreeting() {
        return message + ", good " + timeComponent.getPartOfDay();
    }
}