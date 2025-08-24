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
}
