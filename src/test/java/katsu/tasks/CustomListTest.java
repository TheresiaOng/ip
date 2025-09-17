package katsu.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomListTest {

    @Test
    public void testEmptyList() {
        CustomList list = new CustomList();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddTask() {
        CustomList list = new CustomList();
        ToDo todo = new ToDo("Test task");

        list.add(todo, true);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    public void testMarkCompleted() {
        CustomList list = new CustomList();
        ToDo todo = new ToDo("Test task");
        list.add(todo, true);

        assertDoesNotThrow(() -> list.markCompleted("1", ""));
    }

    @Test
    public void testDeleteTask() {
        CustomList list = new CustomList();
        ToDo todo = new ToDo("Test task");
        list.add(todo, true);

        assertEquals(1, list.size());
        assertDoesNotThrow(() -> list.deleteTask("1"));
        assertEquals(0, list.size());
    }
}