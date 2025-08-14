import java.util.ArrayList;

public class TodoList {
    private final String[] todos;
    private int numOfTasks;

    public TodoList() {
        int MAX_SIZE = 100;
        this.todos = new String[MAX_SIZE];
        this.numOfTasks = 0;
    }

    public String addTask(String task) {
        this.todos[this.numOfTasks] = task;
        this.numOfTasks++;
        return "A new task has been added: " + task;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.numOfTasks; i++) {
            String taskDescription = (i + 1) + ". " + this.todos[i];
            if (i < this.numOfTasks - 1) {
                taskDescription += "\n";
            }
            output.append(taskDescription);
        }
        return output.toString();
    }
}
