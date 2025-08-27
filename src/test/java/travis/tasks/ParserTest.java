package travis.tasks;

import org.junit.jupiter.api.Test;
import travis.chatbot.Parser;
import travis.chatbot.Travis;

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
}
