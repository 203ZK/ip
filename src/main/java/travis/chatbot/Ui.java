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
        System.out.println("Error: Could not find tasks.txt file.");
    }

    public void warnLoadInvalidTask() {
        System.out.println("Error: Invalid task format found in tasks.txt.");
    }
}
