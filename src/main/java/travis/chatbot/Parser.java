package travis.chatbot;

import travis.constants.Enums;
import travis.constants.RegexConstants;
import travis.constants.TaskListConstants;
import travis.exceptions.InvalidTaskException;
import travis.tasks.Deadline;
import travis.tasks.Event;
import travis.tasks.Task;
import travis.tasks.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for parsing user inputs.
 * The <code>Parser</code> uses regular expressions to parse the inputs, throwing exceptions if the format is invalid.
 */
public class Parser {
    // Patterns to match user commands
    private static final Pattern markAsDonePattern = Pattern.compile(RegexConstants.MARK_AS_DONE_REGEX);
    private static final Pattern markAsNotDonePattern = Pattern.compile(RegexConstants.MARK_AS_NOT_DONE_REGEX);
    private static final Pattern deleteTaskPattern = Pattern.compile(RegexConstants.DELETE_TASK_REGEX);

    // Patterns to match task formats
    private static final Pattern toDoPattern = Pattern.compile(RegexConstants.TO_DO_REGEX);
    private static final Pattern deadlinePattern = Pattern.compile(RegexConstants.DEADLINE_REGEX);
    private static final Pattern eventPattern = Pattern.compile(RegexConstants.EVENT_REGEX);

    /**
     * Parses the user's commands.
     * If an existing command is not found, it treats the input as a new task to be added.
     */
    public static boolean parse(Travis travis, String input) throws InvalidTaskException {
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
        } else {
            try {
                Task task = Parser.parseTask(input);
                travis.addTask(task);
            } catch (InvalidTaskException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses the task input.
     * If the task is of an invalid format, an <code>InvalidTaskException</code> is thrown.
     */
    public static Task parseTask(String input) throws InvalidTaskException {
        Matcher toDoMatcher = toDoPattern.matcher(input);
        Matcher deadlineMatcher = deadlinePattern.matcher(input);
        Matcher eventMatcher = eventPattern.matcher(input);

        Task task;
        if (toDoMatcher.find()) {
            String taskDescription = toDoMatcher.group(Enums.RegexGroup.TASK_NAME.getGroup());
            task = new ToDo(taskDescription);
            return task;
        } else if (deadlineMatcher.find()) {
            String taskDescription = deadlineMatcher.group(Enums.RegexGroup.TASK_NAME.getGroup());
            String deadline = deadlineMatcher.group(Enums.RegexGroup.DEADLINE.getGroup());
            try {
                LocalDate date = LocalDate.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE);
                task = new Deadline(taskDescription, date);
                return task;
            } catch (DateTimeParseException e) {
                throw new InvalidTaskException(String.format(TaskListConstants.UNKNOWN_DEADLINE, deadline));
            }

        } else if (eventMatcher.find()) {
            String taskDescription = eventMatcher.group(Enums.RegexGroup.TASK_NAME.getGroup());
            String start = eventMatcher.group(Enums.RegexGroup.START_DATE.getGroup());
            String end = eventMatcher.group(Enums.RegexGroup.END_DATE.getGroup());
            task = new Event(taskDescription, start, end);
            return task;
        } else {
            throw new InvalidTaskException(TaskListConstants.UNKNOWN_INPUT);
        }
    }
}
