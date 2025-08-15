import java.util.Scanner;

public class Travis {
    private final TaskList tasks;

    public Travis() {
        this.tasks = new TaskList();
    }

    private static void greet() {
        wrap(Constants.GREETING);
    }

    private static void farewell() {
        wrap(Constants.FAREWELL);
    }

    private static void wrap(String content) {
        System.out.println(Constants.HORIZONTAL_LINE + "\n" + content + "\n" + Constants.HORIZONTAL_LINE + "\n");
    }

    private void listTasks() {
        wrap(this.tasks.toString());
    }

    private void addTask(String input) {
        String newTask = this.tasks.addTask(input);
        wrap(newTask);
    }

    private void markTaskAsDone(String input) {
        String taskNumberStr = input.substring(5).trim();
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            this.tasks.markTaskAsDone(taskNumber - 1);
            wrap(Constants.MARKED_AS_DONE + this.tasks.displayTask(taskNumber - 1));
        } catch (NumberFormatException e) { // If unable to match task number, treat as an actual task
            addTask(input);
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        }
    }

    private void markTaskAsNotDone(String input) {
        String taskNumberStr = input.substring(6).trim();
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            this.tasks.markTaskAsNotDone(taskNumber - 1);
            wrap(Constants.MARKED_AS_NOT_DONE + this.tasks.displayTask(taskNumber - 1));
        } catch (NumberFormatException e) { // If unable to match task number, treat as an actual task
            addTask(input);
        } catch (TaskNotFoundException e) {
            wrap(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Travis travis = new Travis();
        greet();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        while (!input.equals("bye")) {
            String lowercaseInput = input.toLowerCase();

            if (lowercaseInput.startsWith("mark ")) {
                travis.markTaskAsDone(lowercaseInput);
            } else if (lowercaseInput.startsWith("unmark ")) {
                travis.markTaskAsNotDone(lowercaseInput);
            } else if (lowercaseInput.equals("list")) {
                travis.listTasks();
            } else {
                travis.addTask(lowercaseInput);
            }

            input = scanner.nextLine().trim();
        }

        farewell();
    }
}
