import constants.Enums;
import constants.RegexConstants;
import constants.TaskListConstants;
import exceptions.InvalidTaskException;
import exceptions.LoadInvalidTaskException;
import exceptions.TaskNotFoundException;
import loaders.DeadlineLoader;
import loaders.EventLoader;
import loaders.ToDoLoader;
import tasks.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class TaskList {
    private static final Pattern toDoPattern = Pattern.compile(RegexConstants.TO_DO_REGEX);
    private static final Pattern deadlinePattern = Pattern.compile(RegexConstants.DEADLINE_REGEX);
    private static final Pattern eventPattern = Pattern.compile(RegexConstants.EVENT_REGEX);
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void readTasks() throws IOException {
        Path filePath = Paths.get(TaskListConstants.FILE_PATH);
        BufferedReader reader = Files.newBufferedReader(filePath);

        String line;
        while ((line = reader.readLine()) != null) {
            try {
                Task task = switch (line.charAt(Enums.FileInputArg.TASK_TYPE.ordinal())) {
                    case 'T' -> {
                        ToDoLoader toDoLoader = new ToDoLoader();
                        yield toDoLoader.load(line);
                    }
                    case 'D' -> {
                        DeadlineLoader deadlineLoader = new DeadlineLoader();
                        yield deadlineLoader.load(line);
                    }
                    default -> {
                        EventLoader eventLoader = new EventLoader();
                        yield eventLoader.load(line);
                    }
                };
                this.tasks.add(task);
            } catch (LoadInvalidTaskException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveTasks() throws IOException {
        Path filePath = Paths.get(TaskListConstants.FILE_PATH);
        Files.write(
                filePath, this.toFile(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public int getTaskCount() {
        return this.tasks.size();
    }

    public Task getTask(int taskNumber) throws TaskNotFoundException {
        if (taskNumber >= 0 && taskNumber < this.tasks.size()) {
            return this.tasks.get(taskNumber);
        } else if (this.tasks.isEmpty()) {
            throw new TaskNotFoundException(
                    TaskListConstants.COULD_NOT_FIND_TASK + (taskNumber + 1) + "\n" +
                            TaskListConstants.TRY_ADDING_TASKS);
        } else {
            throw new TaskNotFoundException(
                    TaskListConstants.COULD_NOT_FIND_TASK + (taskNumber + 1) + " \n" +
                            TaskListConstants.SELECT_TASK_WITHIN_RANGE + "1 to " + this.tasks.size() + ".");
        }
    }

    public Task addTask(String input) throws InvalidTaskException {
        Matcher toDoMatcher = toDoPattern.matcher(input);
        Matcher deadlineMatcher = deadlinePattern.matcher(input);
        Matcher eventMatcher = eventPattern.matcher(input);

        Task task;
        if (toDoMatcher.find()) {
            String taskDescription = toDoMatcher.group(Enums.RegexGroup.TASK_NAME.getGroup());
            task = new ToDo(taskDescription);

        } else if (deadlineMatcher.find()) {
            String taskDescription = deadlineMatcher.group(Enums.RegexGroup.TASK_NAME.getGroup());
            String deadline = deadlineMatcher.group(Enums.RegexGroup.DEADLINE.getGroup());
            try {
                LocalDate date = LocalDate.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE);
                task = new Deadline(taskDescription, date);
            } catch (DateTimeParseException e) {
                throw new InvalidTaskException(String.format(TaskListConstants.UNKNOWN_DEADLINE, deadline));
            }

        } else if (eventMatcher.find()) {
            String taskDescription = eventMatcher.group(Enums.RegexGroup.TASK_NAME.getGroup());
            String start = eventMatcher.group(Enums.RegexGroup.START_DATE.getGroup());
            String end = eventMatcher.group(Enums.RegexGroup.END_DATE.getGroup());
            task = new Event(taskDescription, start, end);

        } else {
            throw new InvalidTaskException(TaskListConstants.UNKNOWN_INPUT);
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

    public byte[] toFile() {
        if (this.tasks.isEmpty()) {
            return new byte[0];
        }
        StringBuilder output = new StringBuilder();
        for (Task task : this.tasks) {
            output.append(task.getFileString());
        }
        return output.toString().getBytes();
    }

    @Override
    public String toString() {
        if (this.tasks.isEmpty()) {
            return TaskListConstants.NO_TASKS + " " + TaskListConstants.TRY_ADDING_TASKS;
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
