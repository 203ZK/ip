import exceptions.InvalidTaskException;
import exceptions.TaskNotFoundException;
import tasks.Task;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Travis {
    private static final Pattern markAsDonePattern = Pattern.compile(Constants.MARK_AS_DONE_REGEX);
    private static final Pattern markAsNotDonePattern = Pattern.compile(Constants.MARK_AS_NOT_DONE_REGEX);
    private static final Pattern deleteTaskPattern = Pattern.compile(Constants.DELETE_TASK_REGEX);
    private final TaskList tasks;

    public Travis() {
        this.tasks = new TaskList();
    }

    private static void greet() {
        wrap(Constants.GREETING);
    }

    private static void farewell() {
        wrap(Constants.FAREWELL);
    }

    private static void wrap(String content) {
        System.out.println(Constants.HORIZONTAL_LINE + "\n" + content + "\n" + Constants.HORIZONTAL_LINE + "\n");
    }

    private void listTasks() {
        wrap(this.tasks.toString());
    }

    private void addTask(String input) {
        try {
            Task newTask = this.tasks.addTask(input);
            wrap(String.format(Constants.NEW_TASK, newTask) +
                    String.format(Constants.TOTAL_TASKS, this.tasks.getTaskCount()));
        } catch (InvalidTaskException e) {
            wrap(e.getMessage());
        }
    }

    private void deleteTask(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task deletedTask = this.tasks.deleteTask(taskNumber);
            wrap(String.format(Constants.DELETED_TASK, deletedTask) +
                    String.format(Constants.TOTAL_TASKS, this.tasks.getTaskCount()));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        }
    }

    private void markTaskAsDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task completedTask = this.tasks.markTaskAsDone(taskNumber);
            wrap(String.format(Constants.MARKED_AS_DONE, completedTask));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        }
    }

    private void markTaskAsNotDone(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            Task incompleteTask = this.tasks.markTaskAsNotDone(taskNumber);
            wrap(String.format(Constants.MARKED_AS_NOT_DONE, incompleteTask));
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
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
                travis.markTaskAsDone(markAsDoneMatcher.group(Constants.RegexGroup.TASK_INDEX.getGroup()));
            } else if (markAsNotDoneMatcher.matches()) {
                travis.markTaskAsNotDone(markAsNotDoneMatcher.group(Constants.RegexGroup.TASK_INDEX.getGroup()));
            } else if (deleteTaskMatcher.matches()) {
                travis.deleteTask(deleteTaskMatcher.group(Constants.RegexGroup.TASK_INDEX.getGroup()));
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
