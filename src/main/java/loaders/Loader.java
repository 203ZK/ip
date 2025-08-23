package loaders;

import exceptions.LoadInvalidTaskException;
import tasks.Task;

public interface Loader {
    public Task load(String line) throws LoadInvalidTaskException;
}
