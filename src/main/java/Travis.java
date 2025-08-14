import java.util.Scanner;

public class Travis {
    public static void main(String[] args) {
        String greeting = "Hello! I'm Travis\nWhat can I do for you?";
        String farewell = "Bye. Hope to see you again soon!";
        System.out.println(wrapInHorizontalLines(greeting));

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(wrapInHorizontalLines(input + "\nOh don't be boring, say something else!"));
            input = scanner.nextLine();
        }
        System.out.println(wrapInHorizontalLines(farewell));
    }

    private static String wrapInHorizontalLines(String content) {
        String HORIZONTAL_LINE = "____________________________________________________________";
        return HORIZONTAL_LINE + "\n" + content + "\n" + HORIZONTAL_LINE + "\n";
    }
}
