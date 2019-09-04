import TaskPackage.Task;

import java.io.IOException;
import java.util.ArrayList;

public class Duke {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    public Duke(String filePath) throws IOException {
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadFile());
        } catch (IOException e) {
            System.out.println("File could not be loaded. Starting a new list...");
            this.tasks = new TaskList(new ArrayList<Task>());
        }
        storage.updateTasks(this.tasks);
        this.ui = new Ui(this.tasks, this.storage);

    }

    public static void main(String[] args) throws IOException {
        new Duke("../data/duke.txt").run();
    }

    private void run() {
        ui.handleInput();
        System.exit(0);
    }
}
