package travis.tasks;

import org.junit.jupiter.api.Test;
import travis.chatbot.Parser;
import travis.chatbot.Travis;
import travis.exceptions.InvalidTaskException;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private final Travis travis = new Travis("");

    // ----------------------------
    // Tests for parse (integration-style)
    // ----------------------------

    @Test
    public void userCommandParsing_invalidCommand_throwsInvalidTaskException() {
        assertThrows(InvalidTaskException.class,
                () -> Parser.parse(travis, "lisT"));
    }

    @Test
    public void userCommandParsing_validMarkAsDone_returnsConfirmation() throws InvalidTaskException {
        travis.addTask(Parser.parseTask("todo buy milk"));
        String output = Parser.parse(travis, "mark 1");
        assertEquals("""
                        Nice! I've marked this task as done:
                        [T][X] buy milk""",
                output);
    }

    @Test
    public void userCommandParsing_invalidMarkAsDone_throwsInvalidTaskException() {
        assertThrows(InvalidTaskException.class,
                () -> Parser.parse(travis, "mark1"));
    }

    // ----------------------------
    // Tests for parseTask (unit tests)
    // ----------------------------

    @Test
    public void taskCommandParsing_validToDo_returnsToDoString() throws InvalidTaskException {
        Task task = Parser.parseTask("todo buy bread");
        assertEquals("[T][?] buy bread", task.toString());
    }

    @Test
    public void taskCommandParsing_invalidToDoPrefix_throwsInvalidTaskException() {
        InvalidTaskException e = assertThrows(InvalidTaskException.class,
                () -> Parser.parseTask("tod buy bread"));
        assertTrue(e.getMessage().contains("Oops, I had trouble"));
    }

    @Test
    public void taskCommandParsing_validDeadline_returnsDeadlineString() throws InvalidTaskException {
        Task task = Parser.parseTask("deadline submit homework /by 2025-08-04");
        assertEquals("[D][?] submit homework (by: Aug 04 2025)", task.toString());
    }

    @Test
    public void taskCommandParsing_invalidDeadlineDate_throwsInvalidTaskException() {
        InvalidTaskException e = assertThrows(InvalidTaskException.class,
                () -> Parser.parseTask("deadline submit homework /by 2025-08-4"));
        assertEquals("Sorry, it looks like 2025-08-4 isn't a valid date!", e.getMessage());
    }

    @Test
    public void taskCommandParsing_validEvent_returnsEventString() throws InvalidTaskException {
        Task task = Parser.parseTask("event meeting /from 4pm /to 6pm");
        assertEquals("[E][?] meeting (from: 4pm to: 6pm)", task.toString());
    }

    @Test
    public void taskCommandParsing_invalidEventPrefix_throwsInvalidTaskException() {
        InvalidTaskException e = assertThrows(InvalidTaskException.class,
                () -> Parser.parseTask("evnt meeting /from 4pm /to 6pm"));
        assertTrue(e.getMessage().contains("Oops, I had trouble"));
    }
}

