package travis.chatbot;

import travis.constants.Enums;
import travis.constants.RegexConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern markAsDonePattern = Pattern.compile(RegexConstants.MARK_AS_DONE_REGEX);
    private static final Pattern markAsNotDonePattern = Pattern.compile(RegexConstants.MARK_AS_NOT_DONE_REGEX);
    private static final Pattern deleteTaskPattern = Pattern.compile(RegexConstants.DELETE_TASK_REGEX);

    public static boolean parse(Travis travis, String input) {
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
        } else if (input.equals("bye")) {
            travis.setIsExiting(true);
            return false;
        } else {
            travis.addTask(input);
        }
        return true;
    }
}
