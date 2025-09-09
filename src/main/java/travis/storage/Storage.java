package travis.storage;

import travis.constants.Enums;
import travis.constants.TaskListConstants;
import travis.exceptions.InvalidTaskException;
import travis.exceptions.LoadInvalidTaskException;
import travis.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Responsible for reading and saving the task list from and to the hard disk.
 * Currently, tasks are saved to a <code>.txt</code> file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads tasks from the <code>.txt</code> file.
     * @return <code>ArrayList</code> of tasks.
     * @throws IOException Exception if the file is not found.
     * @throws LoadInvalidTaskException Exception if a task has an incorrect format.
     */
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
                case 'E' -> {
                    EventLoader eventLoader = new EventLoader();
                    yield eventLoader.load(line);
                }
                default -> throw new LoadInvalidTaskException(line);
            };
            tasks.add(task);
        }

        return tasks;
    }

    /**
     * Converts a list of tasks into a <code>byte</code> array to write to the <code>.txt</code> file.
     * @param tasks List of tasks.
     * @return <code>byte</code> array of the tasks.
     */
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

    /**
     * Saves the current state of the task list to the <code>.txt</code> file.
     * If a <code>.txt</code> file does not exist yet, a new file is created and written to.
     * Otherwise, the existing file contents are replaced with the new state of the task list.
     * @param tasks List of tasks.
     * @throws IOException Exception if the file is not found.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        Path filePath = Paths.get(TaskListConstants.FILE_PATH);
        Files.write(
                filePath, this.toFile(tasks),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    /**
     * Updates the existing <code>.txt</code> file.
     * @param tasks List of tasks.
     */
    public void updateTaskFile(ArrayList<Task> tasks) {
        try {
            saveTasks(tasks);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
