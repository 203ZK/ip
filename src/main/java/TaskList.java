public class TaskList {
    private final Task[] tasks;
    private int numOfTasks;

    public TaskList() {
        int MAX_SIZE = 100;
        this.tasks = new Task[MAX_SIZE];
        this.numOfTasks = 0;
    }

    public String addTask(String task) {
        this.tasks[this.numOfTasks] = new Task(task);
        this.numOfTasks++;
        return Constants.NEW_TASK_ADDED + task;
    }

    public void markTaskAsDone(int taskNumber) throws TaskNotFoundException {
        if (taskNumber >= 0 && taskNumber < this.numOfTasks) {
            this.tasks[taskNumber].markAsDone();
        } else {
            throw new TaskNotFoundException(Constants.COULD_NOT_FIND_TASK + (taskNumber + 1));
        }
    }

    public void markTaskAsNotDone(int taskNumber) {
        this.tasks[taskNumber].markAsNotDone();
    }

    public String displayTask(int taskNumber) {
        return this.tasks[taskNumber].toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.numOfTasks; i++) {
            String taskDescription = (i + 1) + ". " + this.tasks[i].toString();
            if (i < this.numOfTasks - 1) {
                taskDescription += "\n";
            }
            output.append(taskDescription);
        }
        return output.toString();
    }
}
