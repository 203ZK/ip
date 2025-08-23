package loaders;

import exceptions.LoadInvalidTaskException;
import tasks.ToDo;

public class ToDoLoader implements Loader {
    @Override
    public ToDo load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(" \\| ");
        try {
            String status = fields[1];
            String taskDescription = fields[2];
            ToDo todo = new ToDo(taskDescription);
            if (status.equals("X")) {
                todo.markAsDone();
            }
            return todo;
        } catch (Exception e) {
            throw new LoadInvalidTaskException(line);
        }
    }
}
