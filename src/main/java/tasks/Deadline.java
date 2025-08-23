package tasks;

public class Deadline extends Task {
    private String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public String getFileString() {
        return String.join(
                " | ", "D", this.getStatusIcon(), this.description, this.deadline) + "\n";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
