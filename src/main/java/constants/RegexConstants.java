package constants;

public final class RegexConstants {
    public static final String TO_DO_REGEX = "^todo (.*)$";
    public static final String DEADLINE_REGEX = "^deadline (.*) /by (.+)$";
    public static final String EVENT_REGEX = "event (.*) /from (.+) /to (.+)";
    public static final String MARK_AS_DONE_REGEX = "^mark (\\d+)$";
    public static final String MARK_AS_NOT_DONE_REGEX = "^unmark (\\d+)$";
    public static final String DELETE_TASK_REGEX = "^delete (\\d+)$";
}
