import java.util.Scanner;

public class Travis {
    private final TodoList todos;

    public Travis() {
        this.todos = new TodoList();
    }

    public static void main(String[] args) {
        Travis travis = new Travis();
        greet();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println(wrapInHorizontalLines(travis.todos.toString()));
            } else {
                String addedTask = travis.todos.addTask(input);
                System.out.println(wrapInHorizontalLines(addedTask));
            }
            input = scanner.nextLine();
        }

        farewell();
    }

    private static void greet() {
        String GREETING = "Hello! I'm Travis!\nWhat can I do for you?";
        System.out.println(wrapInHorizontalLines(GREETING));
    }

    private static void farewell() {
        String FAREWELL = "Bye. Hope to see you again soon!";
        System.out.println(wrapInHorizontalLines(FAREWELL));
    }

    private static String wrapInHorizontalLines(String content) {
        String HORIZONTAL_LINE = "____________________________________________________________";
        return HORIZONTAL_LINE + "\n" + content + "\n" + HORIZONTAL_LINE + "\n";
    }
}
