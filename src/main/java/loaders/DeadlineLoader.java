package loaders;

import exceptions.LoadInvalidTaskException;
import tasks.Deadline;

public class DeadlineLoader implements Loader {
    @Override
    public Deadline load(String line) throws LoadInvalidTaskException {
        String[] fields = line.split(" \\| ");
        try {
            String status = fields[1];
            String taskDescription = fields[2];
            String time = fields[3];
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