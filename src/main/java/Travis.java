import constants.Enums;
import constants.RegexConstants;
import constants.TravisConstants;
import exceptions.InvalidTaskException;
import exceptions.TaskNotFoundException;
import tasks.Task;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Travis {
    private static final Pattern markAsDonePattern = Pattern.compile(RegexConstants.MARK_AS_DONE_REGEX);
    private static final Pattern markAsNotDonePattern = Pattern.compile(RegexConstants.MARK_AS_NOT_DONE_REGEX);
    private static final Pattern deleteTaskPattern = Pattern.compile(RegexConstants.DELETE_TASK_REGEX);
    private final TaskList tasks;

    public Travis() {
        this.tasks = new TaskList();
        try {
            this.tasks.readTasks();
        } catch (IOException e) {
            System.out.println("Error reading tasks file");
        }
    }

    private static void greet() {
        wrap(TravisConstants.GREETING);
    }

    private static void farewell() {
        wrap(TravisConstants.FAREWELL);
    }

    private static void wrap(String content) {
        System.out.println(
                TravisConstants.HORIZONTAL_LINE + "\n" + content + "\n" + TravisConstants.HORIZONTAL_LINE + "\n");
    }

    private void listTasks() {
        wrap(this.tasks.toString());
    }

    private void updateTaskFile() {
        try {
            this.tasks.saveTasks();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addTask(String input) {
        try {
            Task newTask = this.tasks.addTask(input);
            wrap(String.format(TravisConstants.NEW_TASK, newTask) +
                    String.format(TravisConstants.TOTAL_TASKS, this.tasks.getTaskCount()));
        } catch (InvalidTaskException e) {
            wrap(e.getMessage());
        } finally {
            this.updateTaskFile();
        }
    }

    private void deleteTask(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task deletedTask = this.tasks.deleteTask(taskNumber);
            wrap(String.format(TravisConstants.DELETED_TASK, deletedTask) +
                    String.format(TravisConstants.TOTAL_TASKS, this.tasks.getTaskCount()));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        } finally {
            this.updateTaskFile();
        }
    }

    private void markTaskAsDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task completedTask = this.tasks.markTaskAsDone(taskNumber);
            wrap(String.format(TravisConstants.MARKED_AS_DONE, completedTask));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        } finally {
            this.updateTaskFile();
        }
    }

    private void markTaskAsNotDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task incompleteTask = this.tasks.markTaskAsNotDone(taskNumber);
            wrap(String.format(TravisConstants.MARKED_AS_NOT_DONE, incompleteTask));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        } finally {
            this.updateTaskFile();
        }
    }

    public static void main(String[] args) {
        Travis travis = new Travis();
        greet();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        while (!input.equals("bye")) {
            Matcher markAsDoneMatcher = markAsDonePattern.matcher(input);
            Matcher markAsNotDoneMatcher = markAsNotDonePattern.matcher(input);
            Matcher deleteTaskMatcher = deleteTaskPattern.matcher(input);

            if (markAsDoneMatcher.matches()) {
                travis.markTaskAsDone(markAsDoneMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
            } else if (markAsNotDoneMatcher.matches()) {
                travis.markTaskAsNotDone(markAsNotDoneMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
            } else if (deleteTaskMatcher.matches()) {
                travis.deleteTask(deleteTaskMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
            } else if (input.equals("list")) {
                travis.listTasks();
            } else {
                try {
                    travis.addTask(input);
                } catch (InvalidTaskException e) {
                    wrap(e.getMessage());
                }
            }

            input = scanner.nextLine().trim();
        }

        farewell();
        scanner.close();
    }
}
