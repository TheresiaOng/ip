package katsu.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    @Test
    public void testTodoCreation() {
        ToDo todo = new ToDo("Test todo");
        assertEquals("Test todo", todo.toString());
    }

    @Test
    public void testTodoPrintTask() {
        ToDo todo = new ToDo("Test todo");
        assertEquals("[T][ ] Test todo", todo.printTask());
    }

    @Test
    public void testTodoFormatSave() {
        ToDo todo = new ToDo("Test todo");
        assertEquals("T | 0 | Test todo", todo.formatSave());
    }
}