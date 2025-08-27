package travis.chatbot;

import travis.constants.UiConstants;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public Ui() {}

    private static void wrap(String content) {
        System.out.println(
                UiConstants.HORIZONTAL_LINE + "\n" + content + "\n" + UiConstants.HORIZONTAL_LINE + "\n");
    }

    public void greet() {
        wrap(UiConstants.GREETING);
    }

    public void farewell() {
        wrap(UiConstants.FAREWELL);
    }

    public void runUi(Travis travis) {
        this.greet();
        String input = this.scanner.nextLine().trim();
        while (travis.isRunning()) {
            if (input.equals("bye")) {
                break;
            }
            Parser.parse(travis, input);
            input = scanner.nextLine().trim();
        }
        this.scanner.close();
        this.farewell();
    }

    // ------------------- WARNINGS -------------------

    public void warnMessage(String message) {
        wrap(message);
    }

    public void warnFileNotFound() {
        wrap(UiConstants.FILE_NOT_FOUND_ERROR);
    }

    public void warnLoadInvalidTask() {
        wrap(UiConstants.INVALID_TASK_FORMAT_ERROR);
    }

    // ------------------- RESPONSES -------------------

    public void listTasks(String taskListStr) {
        wrap(taskListStr);
    }

    public void notifyAddTask(String taskName, int numOfTasks) {
        wrap(String.format(UiConstants.NEW_TASK, taskName) +
                String.format(UiConstants.TOTAL_TASKS, numOfTasks));
    }

    public void notifyDeleteTask(String taskName, int numOfTasks) {
        wrap(String.format(UiConstants.DELETED_TASK, taskName) +
                String.format(UiConstants.TOTAL_TASKS, numOfTasks));
    }

    public void notifyMarkTaskAsDone(String taskName) {
        wrap(String.format(UiConstants.MARKED_AS_DONE, taskName));
    }

    public void notifyMarkTaskAsNotDone(String taskName) {
        wrap(String.format(UiConstants.MARKED_AS_NOT_DONE, taskName));
    }
}
