package tasks;

import constants.LoaderConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate deadline;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LoaderConstants.DEADLINE_FORMAT);

    public Deadline(String description, LocalDate deadline) {
        super(description);
        this.deadline = deadline;
    }

    public String getFileString() {
        return String.join(
                " | ", "D", this.getStatusIcon(),
                this.description, this.deadline.format(formatter)) + "\n";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline.format(formatter) + ")";
    }
}
