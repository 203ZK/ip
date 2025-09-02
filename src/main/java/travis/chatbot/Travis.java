package travis.chatbot;

import travis.tasks.TaskList;
import travis.constants.TaskListConstants;
import travis.exceptions.InvalidTaskException;
import travis.exceptions.TaskNotFoundException;
import travis.storage.Storage;
import travis.tasks.Task;

import java.io.IOException;

/**
 * Represents the actual Travis chatbot. A <code>Travis</code> object contains its own
 * <code>Ui</code>, <code>Storage</code> and <code>TaskList</code> fields.
 */
public class Travis {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    public Travis(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList();

        try {
            this.taskList.setTaskList(this.storage.loadTasks());
        } catch (IOException e) {
            this.ui.warnFileNotFound();
        } catch (InvalidTaskException e) {
            this.ui.warnLoadInvalidTask();
        }
    }

    /**
     * Lists all existing tasks.
     */
    public String listTasks() {
        return this.taskList.toString();
    }

    /**
     * Adds a new task to the task list.
     * Prints an error message if the input has an invalid format.
     */
    public String addTask(Task newTask) {
        this.taskList.addTask(newTask);
        this.storage.updateTaskFile(this.taskList.getTaskList());
        return this.ui.notifyAddTask(newTask.toString(), this.taskList.getTaskCount());
    }

    /**
     * Deletes a task from the task list with the given index.
     * Prints an error message if the index is invalid.
     * @param taskNumberStr Index of the task to be deleted.
     */
    public String deleteTask(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task deletedTask = this.taskList.deleteTask(taskNumber);
            return this.ui.notifyDeleteTask(deletedTask.toString(), this.taskList.getTaskCount());
        } catch (TaskNotFoundException e) {
            return this.ui.warnMessage(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    /**
     * Marks a task with the given index as done.
     * @param taskNumberStr Index of the task to be marked.
     */
    public String markTaskAsDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task completedTask = this.taskList.markTaskAsDone(taskNumber);
            return this.ui.notifyMarkTaskAsDone(completedTask.toString());
        } catch (TaskNotFoundException e) {
            return this.ui.warnMessage(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    /**
     * Marks a task with the given index as not done yet.
     * @param taskNumberStr Index of the task to be unmarked.
     */
    public String markTaskAsNotDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task incompleteTask = this.taskList.markTaskAsNotDone(taskNumber);
            return this.ui.notifyMarkTaskAsNotDone(incompleteTask.toString());
        } catch (TaskNotFoundException e) {
            return this.ui.warnMessage(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    public String filterTasks(String searchInput) {
        return this.taskList.filterByTaskName(searchInput);
    }

    /**
     * Runs the Ui of the chatbot.
     */
    private void run() {
        this.ui.runUi(this);
    }

    public String getGreeting() {
        return this.ui.greet();
    }

    public static void main(String[] args) {
        Travis travis = new Travis(TaskListConstants.FILE_PATH);
        travis.run();
    }
}
