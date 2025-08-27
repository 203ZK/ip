package travis.tasks;

import org.junit.jupiter.api.Test;
import travis.exceptions.InvalidTaskException;
import travis.exceptions.TaskNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void getTask_noExistingTasks_exceptionThrown() {
        TaskList taskList = new TaskList();
        try {
            taskList.getTask(0);
        } catch (TaskNotFoundException e) {
            assertEquals("Could not find task with task number: 1\n" +
                    "Try adding some tasks!", e.getMessage());
        }
    }

    @Test
    public void getTask_cannotFindTask_exceptionThrown() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("buy bread"));
        TaskList taskList = new TaskList();
        taskList.setTaskList(tasks);

        try {
            taskList.getTask(1);
        } catch (TaskNotFoundException e) {
            assertEquals("Could not find task with task number: 2 \n" +
                    "Please select a task number from the range 1 to 1.", e.getMessage());
        }
    }

    @Test
    public void getTask_foundTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("buy bread"));
        TaskList taskList = new TaskList();
        taskList.setTaskList(tasks);

        Task foundTask = taskList.getTask(0);
        assertEquals("buy bread", foundTask.description);
    }

//    @Test
//    public void addTask_invalidToDoCommand_exceptionThrown() {
//        TaskList taskList = new TaskList();
//        taskList.addTask("tod submit worksheet");
//        assertEquals("""
//                Oops, I had trouble understanding your message :(
//                Were you trying to add a task?
//                Begin your input with one of the following words to add a task: \
//                "todo", "deadline", "event".""", e.getMessage());
//    }
//
//    @Test
//    public void addTask_invalidDeadlineCommand_exceptionThrown() {
//        TaskList taskList = new TaskList();
//        try {
//            taskList.addTask("deadlie submit worksheet /by 2025-08-24");
//        } catch (InvalidTaskException e) {
//            assertEquals("""
//                Oops, I had trouble understanding your message :(
//                Were you trying to add a task?
//                Begin your input with one of the following words to add a task: \
//                "todo", "deadline", "event".""", e.getMessage());
//        }
//    }
//
//    @Test
//    public void addTask_invalidDeadlineBy_exceptionThrown() {
//        TaskList taskList = new TaskList();
//        try {
//            taskList.addTask("deadline submit worksheet / by 2025-08-24");
//        } catch (InvalidTaskException e) {
//            assertEquals("""
//                Oops, I had trouble understanding your message :(
//                Were you trying to add a task?
//                Begin your input with one of the following words to add a task: \
//                "todo", "deadline", "event".""", e.getMessage());
//        }
//    }
//
//    @Test
//    public void addTask_invalidDeadlineDate_exceptionThrown() {
//        TaskList taskList = new TaskList();
//        try {
//            taskList.addTask("deadline submit worksheet /by 2025-08-4");
//        } catch (InvalidTaskException e) {
//            assertEquals("Sorry, it looks like 2025-08-4 isn't a valid date!", e.getMessage());
//        }
//    }
//
//    @Test
//    public void addTask_invalidEventCommand_exceptionThrown() {
//        TaskList taskList = new TaskList();
//        try {
//            taskList.addTask("eent meeting /from Friday /to Saturday");
//        } catch (InvalidTaskException e) {
//            assertEquals("""
//                Oops, I had trouble understanding your message :(
//                Were you trying to add a task?
//                Begin your input with one of the following words to add a task: \
//                "todo", "deadline", "event".""", e.getMessage());
//        }
//    }
//
//    @Test
//    public void addTask_invalidEventFrom_exceptionThrown() {
//        TaskList taskList = new TaskList();
//        try {
//            taskList.addTask("event meeting /frm Friday /to Saturday");
//        } catch (InvalidTaskException e) {
//            assertEquals("""
//                Oops, I had trouble understanding your message :(
//                Were you trying to add a task?
//                Begin your input with one of the following words to add a task: \
//                "todo", "deadline", "event".""", e.getMessage());
//        }
//    }
//
//    @Test
//    public void addTask_invalidEventTo_exceptionThrown() {
//        TaskList taskList = new TaskList();
//        Event task = new Event();
//        try {
//            taskList.addTask("event meeting /from Friday /t o Saturday");
//        } catch (InvalidTaskException e) {
//            assertEquals("""
//                Oops, I had trouble understanding your message :(
//                Were you trying to add a task?
//                Begin your input with one of the following words to add a task: \
//                "todo", "deadline", "event".""", e.getMessage());
//        }
//    }
//
//    @Test
//    public void addTask_validToDo() {
//        TaskList taskList = new TaskList();
//        ToDo task = new ToDo("todo buy bread");
//        taskList.addTask(task);
//        assertEquals("1. [T][?] buy bread", taskList.toString());
//    }
}
