import exceptions.InvalidTaskException;
import exceptions.TaskNotFoundException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskList {
    private static final Pattern toDoPattern = Pattern.compile(Constants.TO_DO_REGEX);
    private static final Pattern deadlinePattern = Pattern.compile(Constants.DEADLINE_REGEX);
    private static final Pattern eventPattern = Pattern.compile(Constants.EVENT_REGEX);
    private final Task[] tasks;
    private int numOfTasks;

    public TaskList() {
        int MAX_SIZE = 100;
        this.tasks = new Task[MAX_SIZE];
        this.numOfTasks = 0;
    }

    public String addTask(String input) throws InvalidTaskException {
        Matcher toDoMatcher = toDoPattern.matcher(input);
        Matcher deadlineMatcher = deadlinePattern.matcher(input);
        Matcher eventMatcher = eventPattern.matcher(input);

        Task newTask;
        if (toDoMatcher.find()) {
            String taskDescription = toDoMatcher.group(1);
            newTask = new ToDo(taskDescription);
        } else if (deadlineMatcher.find()) {
            String taskDescription = deadlineMatcher.group(1);
            String deadline = deadlineMatcher.group(2);
            newTask = new Deadline(taskDescription, deadline);
        } else if (eventMatcher.find()) {
            String taskDescription = eventMatcher.group(1);
            String start = eventMatcher.group(2);
            String end = eventMatcher.group(3);
            newTask = new Event(taskDescription, start, end);
        } else {
            throw new InvalidTaskException(Constants.UNKNOWN_INPUT);
        }

        this.tasks[this.numOfTasks] = newTask;
        this.numOfTasks++;
        return Constants.NEW_TASK_ADDED + newTask + String.format(Constants.TOTAL_NUMBER_OF_TASKS, this.numOfTasks);
    }

    public void markTaskAsDone(int taskNumber) throws TaskNotFoundException {
        if (taskNumber >= 0 && taskNumber < this.numOfTasks) {
            this.tasks[taskNumber].markAsDone();
        } else if (this.numOfTasks == 0) {
            throw new TaskNotFoundException(
                    Constants.COULD_NOT_FIND_TASK + (taskNumber + 1) + "\n" +
                            Constants.TRY_ADDING_TASKS);
        } else {
            throw new TaskNotFoundException(
                    Constants.COULD_NOT_FIND_TASK + (taskNumber + 1) + " \n" +
                            Constants.SELECT_TASK_WITHIN_RANGE + "1 to " + this.numOfTasks);
        }
    }

    public void markTaskAsNotDone(int taskNumber) {
        this.tasks[taskNumber].markAsNotDone();
    }

    public String displayTask(int taskNumber) {
        return this.tasks[taskNumber].toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.numOfTasks; i++) {
            String taskDescription = (i + 1) + ". " + this.tasks[i].toString();
            if (i < this.numOfTasks - 1) {
                taskDescription += "\n";
            }
            output.append(taskDescription);
        }
        return output.toString();
    }
}
