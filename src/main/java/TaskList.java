import exceptions.InvalidTaskException;
import exceptions.TaskNotFoundException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskList {
    private static final Pattern toDoPattern = Pattern.compile(Constants.TO_DO_REGEX);
    private static final Pattern deadlinePattern = Pattern.compile(Constants.DEADLINE_REGEX);
    private static final Pattern eventPattern = Pattern.compile(Constants.EVENT_REGEX);
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public int getTaskCount() {
        return this.tasks.size();
    }

    public Task getTask(int taskNumber) throws TaskNotFoundException {
        if (taskNumber >= 0 && taskNumber < this.tasks.size()) {
            return this.tasks.get(taskNumber);
        } else if (this.tasks.isEmpty()) {
            throw new TaskNotFoundException(
                    Constants.COULD_NOT_FIND_TASK + (taskNumber + 1) + "\n" +
                            Constants.TRY_ADDING_TASKS);
        } else {
            throw new TaskNotFoundException(
                    Constants.COULD_NOT_FIND_TASK + (taskNumber + 1) + " \n" +
                            Constants.SELECT_TASK_WITHIN_RANGE + "1 to " + this.tasks.size() + ".");
        }
    }

    public Task addTask(String input) throws InvalidTaskException {
        Matcher toDoMatcher = toDoPattern.matcher(input);
        Matcher deadlineMatcher = deadlinePattern.matcher(input);
        Matcher eventMatcher = eventPattern.matcher(input);

        Task task;
        if (toDoMatcher.find()) {
            String taskDescription = toDoMatcher.group(1);
            task = new ToDo(taskDescription);
        } else if (deadlineMatcher.find()) {
            String taskDescription = deadlineMatcher.group(1);
            String deadline = deadlineMatcher.group(2);
            task = new Deadline(taskDescription, deadline);
        } else if (eventMatcher.find()) {
            String taskDescription = eventMatcher.group(1);
            String start = eventMatcher.group(2);
            String end = eventMatcher.group(3);
            task = new Event(taskDescription, start, end);
        } else {
            throw new InvalidTaskException(Constants.UNKNOWN_INPUT);
        }

        this.tasks.add(task);
        return task;
    }

    public Task deleteTask(int taskNumber) throws TaskNotFoundException {
        Task task = this.getTask(taskNumber - 1);
        this.tasks.remove(taskNumber - 1);
        return task;
    }

    public Task markTaskAsDone(int taskNumber) throws TaskNotFoundException {
        Task task = this.getTask(taskNumber - 1);
        task.markAsDone();
        return task;
    }

    public Task markTaskAsNotDone(int taskNumber) throws TaskNotFoundException {
        Task task = this.getTask(taskNumber - 1);
        task.markAsNotDone();
        return task;
    }

    @Override
    public String toString() {
        if (this.tasks.isEmpty()) {
            return Constants.NO_TASKS + " " + Constants.TRY_ADDING_TASKS;
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            String taskDescription = (i + 1) + ". " + this.tasks.get(i).toString();
            if (i < this.tasks.size() - 1) {
                taskDescription += "\n";
            }
            output.append(taskDescription);
        }
        return output.toString();
    }
}
