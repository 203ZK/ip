import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Travis {
    private final TaskList tasks;
    private static final Pattern markAsDonePattern = Pattern.compile(Constants.MARK_AS_DONE_REGEX);
    private static final Pattern markAsNotDonePattern = Pattern.compile(Constants.MARK_AS_NOT_DONE_REGEX);

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
        Matcher matcher = markAsDonePattern.matcher(input);
        if (matcher.find()) {
            String taskNumberStr = matcher.group(1);
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
    }

    private void markTaskAsNotDone(String input) {
        Matcher matcher = markAsNotDonePattern.matcher(input);
        if (matcher.find()) {
            String taskNumberStr = matcher.group(1);
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
    }

    public static void main(String[] args) {
        Travis travis = new Travis();
        greet();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        while (!input.equals("bye")) {
            String lowercaseInput = input.toLowerCase().trim();

            if (markAsDonePattern.matcher(lowercaseInput).matches()) {
                travis.markTaskAsDone(lowercaseInput);
            } else if (markAsNotDonePattern.matcher(lowercaseInput).matches()) {
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
