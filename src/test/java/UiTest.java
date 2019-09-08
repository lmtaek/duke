import TaskPackage.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class UiTest {

    private Parser parser;
    private TaskList tasks;
    private Storage storage;

    private String validFilePath = "./duke.txt";

    //Set-up
    public void newUi() {
        this.tasks = new TaskList(new ArrayList<Task>());
        this.storage = new Storage(validFilePath);
    }

    @Test
    public void testGreeting() throws IOException {

    }
}
