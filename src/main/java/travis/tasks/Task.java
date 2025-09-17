package travis.tasks;

abstract public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Checks whether this task's description contains the given search string.
     * <p>
     * Note: The {@code searchInput} is converted to lowercase before matching,
     * but the {@code description} is not. To make the search fully case-insensitive,
     * consider normalizing both strings.
     * </p>
     *
     * @param searchInput the string to search for
     * @return {@code true} if the description contains {@code searchInput},
     *         {@code false} otherwise
     */
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

    /**
     * Returns a string representation of this task
     * suitable for saving to a file.
     *
     * @return the file-safe string representation of this task
     */
    public abstract String getFileString();

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
