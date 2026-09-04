package percy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaskList}, focusing on the boundary logic of
 * {@code isValidIndex} and the list-mutating operations.
 */
public class TaskListTest {

    @Test
    public void isValidIndex_emptyList_alwaysFalse() {
        TaskList tasks = new TaskList();
        assertFalse(tasks.isValidIndex(0));
        assertFalse(tasks.isValidIndex(-1));
    }

    @Test
    public void isValidIndex_checksBothEnds() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("a"));
        tasks.add(new Todo("b"));

        assertTrue(tasks.isValidIndex(0));
        assertTrue(tasks.isValidIndex(1));
        assertFalse(tasks.isValidIndex(2));   // one past the last valid index
        assertFalse(tasks.isValidIndex(-1));  // below the first
    }

    @Test
    public void remove_returnsRemovedTaskAndShiftsTheRest() {
        TaskList tasks = new TaskList();
        Todo a = new Todo("a");
        Todo b = new Todo("b");
        Todo c = new Todo("c");
        tasks.add(a);
        tasks.add(b);
        tasks.add(c);

        Task removed = tasks.remove(1);

        assertSame(b, removed);
        assertEquals(2, tasks.size());
        assertSame(a, tasks.get(0));
        assertSame(c, tasks.get(1));
    }

    @Test
    public void seededConstructor_wrapsTheGivenList() {
        ArrayList<Task> seed = new ArrayList<>();
        seed.add(new Todo("x"));
        seed.add(new Deadline("y", java.time.LocalDate.of(2020, 1, 1)));

        TaskList tasks = new TaskList(seed);

        assertEquals(2, tasks.size());
        assertEquals("x", tasks.get(0).getRawDescription());
    }
}
