public final class Constants {
    private Constants() {}

    // TaskList.java constants
    public static final String NO_TASKS = "You have no tasks right now.";
    public static final String COULD_NOT_FIND_TASK = "Could not find task with task number: ";
    public static final String TRY_ADDING_TASKS = "Try adding some tasks!";
    public static final String SELECT_TASK_WITHIN_RANGE = "Please select a task number from the range ";
    public static final String UNKNOWN_INPUT = """
            Oops, I had trouble understanding your message :(
            Were you trying to add a task?
            Begin your input with one of the following words to add a task: \
            "todo", "deadline", "event".""";

    // Regular expressions
    public static final String TO_DO_REGEX = "^todo (.*)$";
    public static final String DEADLINE_REGEX = "^deadline (.*) /by (.+)$";
    public static final String EVENT_REGEX = "event (.*) /from (.+) /to (.+)";
    public static final String MARK_AS_DONE_REGEX = "^mark (\\d+)$";
    public static final String MARK_AS_NOT_DONE_REGEX = "^unmark (\\d+)$";
    public static final String DELETE_TASK_REGEX = "^delete (\\d+)$";

    // Travis.java constants
    public static final String GREETING = "Hello! I'm Travis!\nWhat can I do for you?";
    public static final String FAREWELL = "Bye. Hope to see you again soon!";
    public static final String HORIZONTAL_LINE = "____________________________________________________________";
    public static final String TOTAL_TASKS = "Now you have %d task(s) in your list.";
    public static final String NEW_TASK = "A new task has been added: %s\n";
    public static final String DELETED_TASK = "Noted. I have deleted the following task: %s\n";
    public static final String MARKED_AS_DONE = "Nice! I've marked this task as done: %s";
    public static final String MARKED_AS_NOT_DONE = "Ok, I've marked this task as not done yet: %s";

    // Enums
    public enum RegexGroup {
        TASK_INDEX(1),
        TASK_NAME(1),
        DEADLINE(2),
        START_DATE(2),
        END_DATE(3);

        private final int group;

        RegexGroup(int group) {
            this.group = group;
        }

        public int getGroup() {
            return this.group;
        }
    }
}
