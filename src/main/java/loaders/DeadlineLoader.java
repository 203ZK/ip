package loaders;

import constants.Enums;
import constants.LoaderConstants;
import exceptions.LoadInvalidTaskException;
import tasks.Deadline;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineLoader implements Loader {
    @Override
    public Deadline load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(" \\| ");
        try {
            String status = fields[Enums.FileInputArg.TASK_STATUS.ordinal()];
            String taskDescription = fields[Enums.FileInputArg.TASK_DESCRIPTION.ordinal()];
            String timeStr = fields[Enums.FileInputArg.TASK_START.ordinal()];

            LocalDate time = LocalDate.parse(timeStr, DateTimeFormatter.ofPattern(LoaderConstants.DEADLINE_FORMAT));
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