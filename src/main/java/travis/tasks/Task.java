package travis.tasks;

abstract public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean containsString(String searchInput) {
        return this.description.contains(searchInput.toLowerCase());
    }

    public String getStatusIcon() {
        return (isDone ? "X" : "?");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public abstract String getFileString();

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
