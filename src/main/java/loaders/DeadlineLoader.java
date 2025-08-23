package loaders;

import constants.Enums;
import exceptions.LoadInvalidTaskException;
import tasks.Deadline;

public class DeadlineLoader implements Loader {
    @Override
    public Deadline load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(" \\| ");
        try {
            String status = fields[Enums.FileInputArg.TASK_STATUS.ordinal()];
            String taskDescription = fields[Enums.FileInputArg.TASK_DESCRIPTION.ordinal()];
            String time = fields[Enums.FileInputArg.TASK_START.ordinal()];
            Deadline deadline = new Deadline(taskDescription, time);
            if (status.equals("X")) {
                deadline.markAsDone();
            }
            return deadline;
        } catch (Exception e) {
            throw new LoadInvalidTaskException(line);
        }
    }
}