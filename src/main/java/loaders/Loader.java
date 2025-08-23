package loaders;

import exceptions.LoadInvalidTaskException;
import tasks.Task;

public interface Loader {
    Task load(String line) throws LoadInvalidTaskException;
}
