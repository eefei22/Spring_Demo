package com.example.demo.component;

import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class TimeComponent {

    public String getPartOfDay() {
        LocalTime currentTime = LocalTime.now();
        return getPartOfDay(currentTime);
    }

    public String getPartOfDay(LocalTime time) {
        if (time.isBefore(LocalTime.NOON)) {
            return "morning";
        } else if (time.isBefore(LocalTime.of(17, 0))) {
            return "afternoon";
        } else {
            return "evening";
        }
    }
}