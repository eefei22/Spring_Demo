package com.example.demo;
import com.example.demo.component.TimeComponent;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TimeComponentTest {

    private final TimeComponent timeComponent = new TimeComponent();

    @Test
    void shouldReturnMorningBeforeNoon() {
        assertEquals("morning", timeComponent.getPartOfDay(LocalTime.of(9, 0)));
    }

    @Test
    void shouldReturnAfternoonFromNoonUntilBeforeFivePm() {
        assertEquals("afternoon", timeComponent.getPartOfDay(LocalTime.of(12, 0)));
        assertEquals("afternoon", timeComponent.getPartOfDay(LocalTime.of(15, 59)));
    }

    @Test
    void shouldReturnEveningFromFivePmOnwards() {
        assertEquals("evening", timeComponent.getPartOfDay(LocalTime.of(17, 0)));
        assertEquals("evening", timeComponent.getPartOfDay(LocalTime.of(20, 30)));
    }
}