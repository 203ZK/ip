package travis.constants;

public final class Enums {
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

    public enum FileInputArg {
        TASK_TYPE,
        TASK_STATUS,
        TASK_DESCRIPTION,
        TASK_START,
        TASK_END
    }
}
