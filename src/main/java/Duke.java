import TaskPackage.Task;

import java.io.IOException;
import java.util.ArrayList;

public class Duke {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    private static ArrayList<Task> taskArrayList = new ArrayList<Task>();
    private static int numberOfTasks = 0;

    public Duke() throws IOException {
        this.storage = new Storage(); //At the moment, the data file is hardcoded.
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
        new Duke().run();
    }

    public void run() {
        ui.handleInput();
        System.exit(0);
    }
}
