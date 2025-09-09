package travis.constants;

public final class RegexConstants {
    public static final String REGEX_TO_DO = "^todo (.*)$";
    public static final String REGEX_DEADLINE = "^deadline (.*) /by (.+)$";
    public static final String REGEX_EVENT = "event (.*) /from (.+) /to (.+)";
    public static final String REGEX_MARK_AS_DONE = "^mark (\\d+)$";
    public static final String REGEX_MARK_AS_NOT_DONE = "^unmark (\\d+)$";
    public static final String REGEX_DELETE_TASK = "^delete (\\d+)$";
    public static final String REGEX_FIND_TASK = "^find (.*)$";
    public static final String REGEX_SPLIT_FILE_INPUT = " \\| ";
}
