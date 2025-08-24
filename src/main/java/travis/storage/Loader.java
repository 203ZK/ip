package travis.storage;

import travis.exceptions.LoadInvalidTaskException;
import travis.tasks.Task;

public interface Loader {
    Task load(String line) throws LoadInvalidTaskException;
}
