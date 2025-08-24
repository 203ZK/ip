package travis.chatbot;

import travis.constants.TravisConstants;

public class Ui {
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
