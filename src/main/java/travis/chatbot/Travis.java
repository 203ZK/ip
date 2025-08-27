package travis.chatbot;

import travis.tasks.TaskList;
import travis.constants.TaskListConstants;
import travis.exceptions.InvalidTaskException;
import travis.exceptions.TaskNotFoundException;
import travis.storage.Storage;
import travis.tasks.Task;

import java.io.IOException;

/**
 * Class for the actual Travis chatbot. A <code>Travis</code> object contains its own
 * <code>Ui</code>, <code>Storage</code> and <code>TaskList</code> fields.
 */
public class Travis {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExiting;

    public Travis(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList();
        this.isExiting = false;

        try {
            this.taskList.setTaskList(this.storage.loadTasks());
        } catch (IOException e) {
            this.ui.warnFileNotFound();
        } catch (InvalidTaskException e) {
            this.ui.warnLoadInvalidTask();
        }
    }

    public boolean isRunning() {
        return !this.isExiting;
    }

    public void setIsExiting(boolean isExiting) {
        this.isExiting = isExiting;
    }

    /**
     * Lists all existing tasks.
     */
    public void listTasks() {
        this.ui.listTasks(this.taskList.toString());
    }

    /**
     * Adds a new task to the task list.
     * Prints an error message if the input has an invalid format.
     */
    public void addTask(Task newTask) {
        this.taskList.addTask(newTask);
        this.ui.notifyAddTask(newTask.toString(), this.taskList.getTaskCount());
        this.storage.updateTaskFile(this.taskList.getTaskList());
    }

    /**
     * Deletes a task from the task list with the given index.
     * Prints an error message if the index is invalid.
     * @param taskNumberStr Index of the task to be deleted.
     */
    public void deleteTask(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task deletedTask = this.taskList.deleteTask(taskNumber);
            this.ui.notifyDeleteTask(deletedTask.toString(), this.taskList.getTaskCount());
        } catch (TaskNotFoundException e) {
            this.ui.warnMessage(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    /**
     * Marks a task with the given index as done.
     * @param taskNumberStr Index of the task to be marked.
     */
    public void markTaskAsDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task completedTask = this.taskList.markTaskAsDone(taskNumber);
            this.ui.notifyMarkTaskAsDone(completedTask.toString());
        } catch (TaskNotFoundException e) {
            this.ui.warnMessage(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    /**
     * Marks a task with the given index as not done yet.
     * @param taskNumberStr Index of the task to be unmarked.
     */
    public void markTaskAsNotDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task incompleteTask = this.taskList.markTaskAsNotDone(taskNumber);
            this.ui.notifyMarkTaskAsNotDone(incompleteTask.toString());
        } catch (TaskNotFoundException e) {
            this.ui.warnMessage(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    public void filterTasks(String searchInput) {
        String filteredTasks = this.taskList.filterByTaskName(searchInput);
        this.ui.listTasks(filteredTasks);
    }

    /**
     * Runs the Ui of the chatbot.
     */
    private void run() {
        this.ui.runUi(this);
    }

    public static void main(String[] args) {
        Travis travis = new Travis(TaskListConstants.FILE_PATH);
        travis.run();
    }
}
