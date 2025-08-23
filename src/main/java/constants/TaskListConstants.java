package constants;

public final class TaskListConstants {
    public static final String FILE_PATH = "tasks.txt";
    public static final String NO_TASKS = "You have no tasks right now.";
    public static final String COULD_NOT_FIND_TASK = "Could not find task with task number: ";
    public static final String TRY_ADDING_TASKS = "Try adding some tasks!";
    public static final String SELECT_TASK_WITHIN_RANGE = "Please select a task number from the range ";
    public static final String UNKNOWN_INPUT = """
            Oops, I had trouble understanding your message :(
            Were you trying to add a task?
            Begin your input with one of the following words to add a task: \
            "todo", "deadline", "event".""";
}
