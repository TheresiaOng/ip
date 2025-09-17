package katsu.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new ToDo("Test task");
        assertEquals("Test task", task.toString());
        assertFalse(task.isComplete());
    }

    @Test
    public void testMarkCompleted() {
        Task task = new ToDo("Test task");
        task.markCompleted();
        assertTrue(task.isComplete());
    }

    @Test
    public void testMarkUncompleted() {
        Task task = new ToDo("Test task");
        task.markCompleted();
        task.markUncompleted();
        assertFalse(task.isComplete());
    }

    @Test
    public void testPrintTask() {
        Task task = new ToDo("Test task");
        assertEquals("[T][ ] Test task", task.printTask());
        task.markCompleted();
        assertEquals("[T][X] Test task", task.printTask());
    }

    @Test
    public void testFormatSave() {
        Task task = new ToDo("Test task");
        assertEquals("T | 0 | Test task", task.formatSave());
        task.markCompleted();
        assertEquals("T | 1 | Test task", task.formatSave());
    }

    @Test
    public void testHasKeyword() {
        Task task = new ToDo("Test task with keyword");
        assertTrue(task.hasKeyword("keyword"));
        assertFalse(task.hasKeyword("nonexistent"));
    }
}