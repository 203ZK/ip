package travis.chatbot;

import travis.Parser;
import travis.constants.TravisConstants;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public Ui() {}

    private static void wrap(String content) {
        System.out.println(
                TravisConstants.HORIZONTAL_LINE + "\n" + content + "\n" + TravisConstants.HORIZONTAL_LINE + "\n");
    }

    public void greet() {
        wrap(TravisConstants.GREETING);
    }

    public void farewell() {
        wrap(TravisConstants.FAREWELL);
    }

    public void runUi(Travis travis) {
        this.greet();
        String input = this.scanner.nextLine().trim();
        while (travis.isRunning()) {
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
        wrap("Error: Could not find tasks.txt file.");
    }

    public void warnLoadInvalidTask() {
        wrap("Error: Invalid task format found in tasks.txt.");
    }

    // ------------------- RESPONSES -------------------

    public void listTasks(String taskListStr) {
        wrap(taskListStr);
    }

    public void notifyAddTask(String taskName, int numOfTasks) {
        wrap(String.format(TravisConstants.NEW_TASK, taskName) +
                String.format(TravisConstants.TOTAL_TASKS, numOfTasks));
    }

    public void notifyDeleteTask(String taskName, int numOfTasks) {
        wrap(String.format(TravisConstants.DELETED_TASK, taskName) +
                String.format(TravisConstants.TOTAL_TASKS, numOfTasks));
    }

    public void notifyMarkTaskAsDone(String taskName) {
        wrap(String.format(TravisConstants.MARKED_AS_DONE, taskName));
    }

    public void notifyMarkTaskAsNotDone(String taskName) {
        wrap(String.format(TravisConstants.MARKED_AS_NOT_DONE, taskName));
    }
}
