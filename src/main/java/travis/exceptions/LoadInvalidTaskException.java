package travis.exceptions;

public class LoadInvalidTaskException extends RuntimeException {
    public LoadInvalidTaskException(String taskInput) {
        super(String.format("Invalid task format: %s", taskInput));
    }
}
