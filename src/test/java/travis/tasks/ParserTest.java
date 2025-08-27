package travis.tasks;

import org.junit.jupiter.api.Test;
import travis.chatbot.Parser;
import travis.chatbot.Travis;
import travis.exceptions.InvalidTaskException;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private final static Travis travis = new Travis("");

    @Test
    public void userCommandParsing_validList_returnsTrue() {
        boolean status = Parser.parse(travis, "list");
        assertTrue(status);
    }

    @Test
    public void userCommandParsing_invalidList_returnsFalse() {
        boolean status = Parser.parse(travis, "lisT");
        assertFalse(status);
    }

    @Test
    public void userCommandParsing_validBye_returnsTrue() {
        boolean status = Parser.parse(travis, "bye");
        assertTrue(status);
    }

    @Test
    public void userCommandParsing_invalidBye_returnsFalse() {
        boolean status = Parser.parse(travis, "by e");
        assertFalse(status);
    }

    @Test
    public void userCommandParsing_validMarkAsDone_returnsTrue() {
        boolean status = Parser.parse(travis, "mark 1");
        assertTrue(status);
    }

    @Test
    public void userCommandParsing_invalidMarkAsDone_returnsFalse() {
        boolean status = Parser.parse(travis, "mark1");
        assertFalse(status);
    }

    @Test
    public void userCommandParsing_validMarkAsNotDone_returnsTrue() {
        boolean status = Parser.parse(travis, "unmark 1");
        assertTrue(status);
    }

    @Test
    public void userCommandParsing_invalidMarkAsNotDone_returnsFalse() {
        boolean status = Parser.parse(travis, "unmark1");
        assertFalse(status);
    }

    @Test
    public void userCommandParsing_validDeleteTask_returnsTrue() {
        boolean status = Parser.parse(travis, "delete 1");
        assertTrue(status);
    }

    @Test
    public void userCommandParsing_invalidDeleteTask_returnsFalse() {
        boolean status = Parser.parse(travis, "delete1");
        assertFalse(status);
    }

    @Test
    public void taskCommandParsing_validToDo_returnsToDoString() {
        Task task = Parser.parseTask("todo buy bread");
        assertEquals("[T][?] buy bread", task.toString());
    }

    @Test
    public void taskCommandParsing_invalidToDoPrefix_exceptionThrown() {
        try {
            Task task = Parser.parseTask("tod buy bread");
        } catch (InvalidTaskException e) {
            assertEquals("""
            Oops, I had trouble understanding your message :(
            Were you trying to add a task?
            Begin your input with one of the following words to add a task: \
            "todo", "deadline", "event".""", e.getMessage());
        }
    }

    @Test
    public void taskCommandParsing_validDeadline_returnsDeadlineString() {
        Task task = Parser.parseTask("deadline submit homework /by 2025-08-04");
        assertEquals("[D][?] submit homework (by: Aug 04 2025)", task.toString());
    }

    @Test
    public void taskCommandParsing_invalidDeadlinePrefix_exceptionThrown() {
        try {
            Task task = Parser.parseTask("dedline submit homework /by 2025-08-04");
        } catch (InvalidTaskException e) {
            assertEquals("""
            Oops, I had trouble understanding your message :(
            Were you trying to add a task?
            Begin your input with one of the following words to add a task: \
            "todo", "deadline", "event".""", e.getMessage());
        }
    }

    @Test
    public void taskCommandParsing_validEvent_returnsEventString() {
        Task task = Parser.parseTask("event meeting /from 4pm /to 6pm");
        assertEquals("[E][?] meeting (from: 4pm to: 6pm)", task.toString());
    }

    @Test
    public void taskCommandParsing_invalidEventPrefix_exceptionThrown() {
        try {
            Task task = Parser.parseTask("evnt meeting /from 4pm /to 6pm");
        } catch (InvalidTaskException e) {
            assertEquals("""
            Oops, I had trouble understanding your message :(
            Were you trying to add a task?
            Begin your input with one of the following words to add a task: \
            "todo", "deadline", "event".""", e.getMessage());
        }
    }
}
