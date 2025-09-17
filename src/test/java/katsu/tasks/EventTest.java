package katsu.tasks;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void testEventCreation() {
        LocalDateTime start = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 15, 12, 0);
        Event event = new Event("Meeting", start, end);

        assertEquals("Meeting", event.toString());
        assertEquals(start, event.getComparableDate());
    }

    @Test
    public void testEventPrintTask() {
        LocalDateTime start = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 15, 12, 0);
        Event event = new Event("Meeting", start, end);

        assertTrue(event.printTask().contains("[E]"));
        assertTrue(event.printTask().contains("Meeting"));
        assertTrue(event.printTask().contains("from:"));
        assertTrue(event.printTask().contains("to:"));
    }
}
