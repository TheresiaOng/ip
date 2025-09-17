package katsu.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class DeadlineTest {

    @Test
    public void testDeadlineCreation() {
        LocalDateTime deadlineTime = LocalDateTime.of(2024, 1, 15, 23, 59);
        Deadline deadline = new Deadline("Submit report", deadlineTime);

        assertEquals("Submit report", deadline.toString());
        assertEquals(deadlineTime, deadline.getComparableDate());
    }
}