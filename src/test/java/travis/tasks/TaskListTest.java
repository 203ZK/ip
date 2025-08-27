package travis.tasks;

import org.junit.jupiter.api.Test;
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
    public void getTask_foundTask_returnsTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("buy bread"));
        TaskList taskList = new TaskList();
        taskList.setTaskList(tasks);

        Task foundTask = taskList.getTask(0);
        assertEquals("buy bread", foundTask.description);
    }

    @Test
    public void addTask_validToDo_toDoAdded() {
        TaskList taskList = new TaskList();
        ToDo task = new ToDo("buy bread");
        taskList.addTask(task);
        assertEquals("1. [T][?] buy bread", taskList.toString());
    }
}
