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
 * Parses user inputs.
 * The <code>Parser</code> uses regular expressions to parse the inputs, throwing exceptions if the format is invalid.
 */
public class Parser {
    // Patterns to match user commands
    private static final Pattern PATTERN_MARK_AS_DONE = Pattern.compile(RegexConstants.REGEX_MARK_AS_DONE);
    private static final Pattern PATTERN_MARK_AS_NOT_DONE = Pattern.compile(RegexConstants.REGEX_MARK_AS_NOT_DONE);
    private static final Pattern PATTERN_DELETE_TASK = Pattern.compile(RegexConstants.REGEX_DELETE_TASK);
    private static final Pattern PATTERN_FIND_TASK = Pattern.compile(RegexConstants.REGEX_FIND_TASK);

    // Patterns to match task formats
    private static final Pattern PATTERN_TO_DO = Pattern.compile(RegexConstants.REGEX_TO_DO);
    private static final Pattern PATTERN_DEADLINE = Pattern.compile(RegexConstants.REGEX_DEADLINE);
    private static final Pattern PATTERN_EVENT = Pattern.compile(RegexConstants.REGEX_EVENT);

    /**
     * Parses the user's commands.
     * If an existing command is not found, it treats the input as a new task to be added.
     */
    public static String parse(Travis travis, String input) throws InvalidTaskException {
        Matcher markAsDoneMatcher = PATTERN_MARK_AS_DONE.matcher(input);
        Matcher markAsNotDoneMatcher = PATTERN_MARK_AS_NOT_DONE.matcher(input);
        Matcher deleteTaskMatcher = PATTERN_DELETE_TASK.matcher(input);
        Matcher findTaskMatcher = PATTERN_FIND_TASK.matcher(input);

        if (findTaskMatcher.find()) {
            return travis.filterTasks(findTaskMatcher.group(Enums.RegexGroup.TASK_NAME.getGroup()));
        } else if (markAsDoneMatcher.matches()) {
            return travis.markTaskAsDone(markAsDoneMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
        } else if (markAsNotDoneMatcher.matches()) {
            return travis.markTaskAsNotDone(markAsNotDoneMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
        } else if (deleteTaskMatcher.matches()) {
            return travis.deleteTask(deleteTaskMatcher.group(Enums.RegexGroup.TASK_INDEX.getGroup()));
        } else if (input.equals("list")) {
            return travis.listTasks();
        } else {
            Task task = Parser.parseTask(input);
            return travis.addTask(task);
        }
    }

    /**
     * Parses the task input.
     * If the task is of an invalid format, an <code>InvalidTaskException</code> is thrown.
     */
    public static Task parseTask(String input) throws InvalidTaskException {
        Matcher toDoMatcher = PATTERN_TO_DO.matcher(input);
        Matcher deadlineMatcher = PATTERN_DEADLINE.matcher(input);
        Matcher eventMatcher = PATTERN_EVENT.matcher(input);

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
