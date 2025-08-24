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

    public void warnFileNotFound() {
        wrap("Error: Could not find tasks.txt file.");
    }

    public void warnLoadInvalidTask() {
        wrap("Error: Invalid task format found in tasks.txt.");
    }

    public void warnInvalidTask(String message) {
        wrap(message);
    }

    public void listTasks(String taskListStr) {
        wrap(taskListStr);
    }

    public void notifyAddTask(String taskName, int numOfTasks) {
        wrap(String.format(TravisConstants.NEW_TASK, taskName) +
                String.format(TravisConstants.TOTAL_TASKS, numOfTasks));
    }
}
