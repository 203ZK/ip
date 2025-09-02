package travis.chatbot;

import travis.constants.UiConstants;
import travis.exceptions.InvalidTaskException;

import java.util.Scanner;

/**
 * Represents the user interface of the chatbot.
 * Users interact with the chatbot through CLI commands via this class.
 */
public class Ui {
    private final Scanner SCANNER = new Scanner(System.in);

    public Ui() {}

    private static String wrap(String content) {
        return UiConstants.HORIZONTAL_LINE + "\n" + content + "\n" + UiConstants.HORIZONTAL_LINE + "\n";
    }

    public String greet() {
        return UiConstants.GREETING;
    }

    public void farewell() {
        wrap(UiConstants.FAREWELL);
    }

    /**
     * Repeatedly reads and parses user input until the "bye" command is entered.
     * @param travis <code>Travis</code> chatbot that is currently running.
     */
    public void runUi(Travis travis) {
        this.greet();
        String input = this.SCANNER.nextLine().trim();
        while (!input.equals("bye")) {
            try {
                Parser.parse(travis, input);
            } catch (InvalidTaskException e) {
                this.warnMessage(e.getMessage());
            } finally {
                input = SCANNER.nextLine().trim();
            }
        }
        this.SCANNER.close();
        this.farewell();
    }

    // ------------------- WARNINGS -------------------

    public String warnMessage(String message) {
        return message;
    }

    public void warnFileNotFound() {
        System.out.println(wrap(UiConstants.FILE_NOT_FOUND_ERROR));
    }

    public void warnLoadInvalidTask() {
        System.out.println(wrap(UiConstants.INVALID_TASK_FORMAT_ERROR));
    }

    // ------------------- RESPONSES -------------------

    public String listTasks(String taskListStr) {
        return wrap(taskListStr);
    }

    public String notifyAddTask(String taskName, int numOfTasks) {
        return String.format(UiConstants.NEW_TASK, taskName) +
                String.format(UiConstants.TOTAL_TASKS, numOfTasks);
    }

    public String notifyDeleteTask(String taskName, int numOfTasks) {
        return String.format(UiConstants.DELETED_TASK, taskName) +
                String.format(UiConstants.TOTAL_TASKS, numOfTasks);
    }

    public String notifyMarkTaskAsDone(String taskName) {
        return String.format(UiConstants.MARKED_AS_DONE, taskName);
    }

    public String notifyMarkTaskAsNotDone(String taskName) {
        return String.format(UiConstants.MARKED_AS_NOT_DONE, taskName);
    }
}
