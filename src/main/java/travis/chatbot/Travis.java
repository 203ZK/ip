package travis.chatbot;

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
    private static final Pattern markAsDonePattern = Pattern.compile(RegexConstants.MARK_AS_DONE_REGEX);
    private static final Pattern markAsNotDonePattern = Pattern.compile(RegexConstants.MARK_AS_NOT_DONE_REGEX);
    private static final Pattern deleteTaskPattern = Pattern.compile(RegexConstants.DELETE_TASK_REGEX);


    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    public Travis() {
        this.ui = new Ui();
        this.storage = new Storage(TaskListConstants.FILE_PATH);
        this.taskList = new TaskList();

        try {
            this.taskList.setTaskList(this.storage.loadTasks());
        } catch (IOException e) {
            this.ui.warnFileNotFound();
        } catch (InvalidTaskException e) {
            this.ui.warnLoadInvalidTask();
        }
    }

    private static void wrap(String content) {
        System.out.println(
                TravisConstants.HORIZONTAL_LINE + "\n" + content + "\n" + TravisConstants.HORIZONTAL_LINE + "\n");
    }

    private void listTasks() {
        this.ui.listTasks(this.taskList.toString());
    }

    private void addTask(String input) {
        try {
            Task newTask = this.taskList.addTask(input);
            this.ui.notifyAddTask(newTask.toString(), this.taskList.getTaskCount());
        } catch (InvalidTaskException e) {
            this.ui.warnInvalidTask(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    private void deleteTask(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task deletedTask = this.taskList.deleteTask(taskNumber);
            wrap(String.format(TravisConstants.DELETED_TASK, deletedTask) +
                    String.format(TravisConstants.TOTAL_TASKS, this.taskList.getTaskCount()));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    private void markTaskAsDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task completedTask = this.taskList.markTaskAsDone(taskNumber);
            wrap(String.format(TravisConstants.MARKED_AS_DONE, completedTask));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    private void markTaskAsNotDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task incompleteTask = this.taskList.markTaskAsNotDone(taskNumber);
            wrap(String.format(TravisConstants.MARKED_AS_NOT_DONE, incompleteTask));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        } finally {
            this.storage.updateTaskFile(this.taskList.getTaskList());
        }
    }

    private void run() {
        this.ui.greet();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        while (!input.equals("bye")) {
            Matcher markAsDoneMatcher = markAsDonePattern.matcher(input);
            Matcher markAsNotDoneMatcher = markAsNotDonePattern.matcher(input);
            Matcher deleteTaskMatcher = deleteTaskPattern.matcher(input);

            if (markAsDoneMatcher.matches()) {
                this.markTaskAsDone(markAsDoneMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
            } else if (markAsNotDoneMatcher.matches()) {
                this.markTaskAsNotDone(markAsNotDoneMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
            } else if (deleteTaskMatcher.matches()) {
                this.deleteTask(deleteTaskMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
            } else if (input.equals("list")) {
                this.listTasks();
            } else {
                try {
                    this.addTask(input);
                } catch (InvalidTaskException e) {
                    wrap(e.getMessage());
                }
            }

            input = scanner.nextLine().trim();
        }

        scanner.close();
        this.ui.farewell();
    }

    public static void main(String[] args) {
        Travis travis = new Travis();
        travis.run();
    }
}
