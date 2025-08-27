package travis.tasks;

import travis.constants.TaskListConstants;
import travis.exceptions.TaskNotFoundException;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

    public void setTaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getTaskCount() {
        return this.tasks.size();
    }

    public Task getTask(int taskNumber) throws TaskNotFoundException {
        if (taskNumber >= 0 && taskNumber < this.tasks.size()) {
            return this.tasks.get(taskNumber);
        } else if (this.tasks.isEmpty()) {
            throw new TaskNotFoundException(
                    TaskListConstants.COULD_NOT_FIND_TASK + (taskNumber + 1) + "\n" +
                            TaskListConstants.TRY_ADDING_TASKS);
        } else {
            throw new TaskNotFoundException(
                    TaskListConstants.COULD_NOT_FIND_TASK + (taskNumber + 1) + " \n" +
                            TaskListConstants.SELECT_TASK_WITHIN_RANGE + "1 to " + this.tasks.size() + ".");
        }
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task deleteTask(int taskNumber) throws TaskNotFoundException {
        Task task = this.getTask(taskNumber - 1);
        this.tasks.remove(taskNumber - 1);
        return task;
    }

    public Task markTaskAsDone(int taskNumber) throws TaskNotFoundException {
        Task task = this.getTask(taskNumber - 1);
        task.markAsDone();
        return task;
    }

    public Task markTaskAsNotDone(int taskNumber) throws TaskNotFoundException {
        Task task = this.getTask(taskNumber - 1);
        task.markAsNotDone();
        return task;
    }

    public String filterByTaskName(String searchInput) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            String taskDescription = this.tasks.get(i).toString();
            if (taskDescription.toLowerCase().contains(searchInput.toLowerCase())) {
                String fullDescription = (i + 1) + ". " + taskDescription;
                if (!output.isEmpty()) {
                    fullDescription = "\n" + fullDescription;
                }
                output.append(fullDescription);
            }
        }
        return output.toString();
    }

    @Override
    public String toString() {
        if (this.tasks.isEmpty()) {
            return TaskListConstants.NO_TASKS + " " + TaskListConstants.TRY_ADDING_TASKS;
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            String taskDescription = (i + 1) + ". " + this.tasks.get(i).toString();
            if (i < this.tasks.size() - 1) {
                taskDescription += "\n";
            }
            output.append(taskDescription);
        }
        return output.toString();
    }
}
