package com.example.demo.component;
import org.springframework.stereotype.Component;

@Component
public class TextFormatter {

    public String format(String message) {
        return "=== " + message.toUpperCase() + " ===";
    }
}
