package travis.storage;

import travis.constants.Enums;
import travis.constants.TaskListConstants;
import travis.exceptions.LoadInvalidTaskException;
import travis.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadTasks() throws IOException, LoadInvalidTaskException {
        ArrayList<Task> tasks = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(Paths.get(this.filePath));

        String line;
        while ((line = reader.readLine()) != null) {
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
            tasks.add(task);
        }

        return tasks;
    }

    private byte[] toFile(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return new byte[0];
        }
        StringBuilder output = new StringBuilder();
        for (Task task : tasks) {
            output.append(task.getFileString());
        }
        return output.toString().getBytes();
    }

    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        Path filePath = Paths.get(TaskListConstants.FILE_PATH);
        Files.write(
                filePath, this.toFile(tasks),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    public void updateTaskFile(ArrayList<Task> tasks) {
        try {
            saveTasks(tasks);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
