package travis.storage;

import travis.constants.Enums;
import travis.constants.RegexConstants;
import travis.exceptions.LoadInvalidTaskException;
import travis.tasks.Deadline;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineLoader implements Loader {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");

    @Override
    public Deadline load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(RegexConstants.REGEX_SPLIT_FILE_INPUT);
        try {
            String status = fields[Enums.FileInputArg.TASK_STATUS.ordinal()];
            String taskDescription = fields[Enums.FileInputArg.TASK_DESCRIPTION.ordinal()];
            String timeStr = fields[Enums.FileInputArg.TASK_START.ordinal()];
            LocalDate time = LocalDate.parse(timeStr, formatter);

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