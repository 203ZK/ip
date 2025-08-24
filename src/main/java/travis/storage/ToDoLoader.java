package travis.storage;

import travis.constants.Enums;
import travis.exceptions.LoadInvalidTaskException;
import travis.tasks.ToDo;

public class ToDoLoader implements Loader {
    @Override
    public ToDo load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(" \\| ");
        try {
            String status = fields[Enums.FileInputArg.TASK_STATUS.ordinal()];
            String taskDescription = fields[Enums.FileInputArg.TASK_DESCRIPTION.ordinal()];
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
