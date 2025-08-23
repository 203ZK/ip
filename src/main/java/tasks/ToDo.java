package tasks;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    public String getFileString() {
        return String.join(" | ", "T", this.getStatusIcon(), this.description) + "\n";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
