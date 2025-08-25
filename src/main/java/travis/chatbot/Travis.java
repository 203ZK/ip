package travis.chatbot;

import travis.Parser;
import travis.tasks.TaskList;
import travis.constants.Enums;
import travis.constants.RegexConstants;
import travis.constants.TaskListConstants;
import travis.constants.TravisConstants;
import travis.exceptions.InvalidTaskException;
import travis.exceptions.TaskNotFoundException;
import travis.storage.Storage;
import travis.tasks.Task;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Travis {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExiting;

    public Travis() {
        this.ui = new Ui();
        this.storage = new Storage(TaskListConstants.FILE_PATH);
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

    public void listTasks() {
        this.ui.listTasks(this.taskList.toString());
    }

    public void addTask(String input) {
        try {
            Task newTask = this.taskList.addTask(input);
            this.ui.notifyAddTask(newTask.toString(), this.taskList.getTaskCount());
        } catch (InvalidTaskException e) {
            this.ui.warnMessage(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
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

    public void setIsExiting(boolean isExiting) {
        this.isExiting = isExiting;
    }

    private void run() {
        this.ui.runUi(this);
    }

    public static void main(String[] args) {
        Travis travis = new Travis();
        travis.run();
    }
}
