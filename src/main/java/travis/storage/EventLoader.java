package travis.storage;

import travis.constants.Enums;
import travis.constants.RegexConstants;
import travis.exceptions.LoadInvalidTaskException;
import travis.tasks.Event;

public class EventLoader implements Loader {
    @Override
    public Event load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(RegexConstants.REGEX_SPLIT_FILE_INPUT);
        try {
            String status = fields[Enums.FileInputArg.TASK_STATUS.ordinal()];
            String taskDescription = fields[Enums.FileInputArg.TASK_DESCRIPTION.ordinal()];
            String start = fields[Enums.FileInputArg.TASK_START.ordinal()];
            String end = fields[Enums.FileInputArg.TASK_END.ordinal()];

            Event event = new Event(taskDescription, start, end);
            if (status.equals("X")) {
                event.markAsDone();
            }

            return event;
        } catch (Exception e) {
            throw new LoadInvalidTaskException(line);
        }
    }
}