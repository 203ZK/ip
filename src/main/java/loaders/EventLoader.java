package loaders;

import exceptions.LoadInvalidTaskException;
import tasks.Event;

public class EventLoader implements Loader {
    @Override
    public Event load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(" \\| ");
        try {
            String status = fields[1];
            String taskDescription = fields[2];
            String start = fields[3];
            String end = fields[4];
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