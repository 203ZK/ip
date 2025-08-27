package travis.chatbot;

import travis.tasks.TaskList;
import travis.constants.TaskListConstants;
import travis.exceptions.InvalidTaskException;
import travis.exceptions.TaskNotFoundException;
import travis.storage.Storage;
import travis.tasks.Task;

import java.io.IOException;

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

    public void listTasks() {
        this.ui.listTasks(this.taskList.toString());
    }

    public void addTask(Task newTask) {
        this.taskList.addTask(newTask);
        this.ui.notifyAddTask(newTask.toString(), this.taskList.getTaskCount());
        this.storage.updateTaskFile(this.taskList.getTaskList());
    }

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

    private void run() {
        this.ui.runUi(this);
    }

    public static void main(String[] args) {
        Travis travis = new Travis(TaskListConstants.FILE_PATH);
        travis.run();
    }
}
