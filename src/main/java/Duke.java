import TaskPackage.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class containing the main method. Establishes current session's Ui, TaskList, and Storage,
 * and cues Ui to begin taking in input from the user.
 */
public class Duke {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    public Duke(String filePath) throws IOException {
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadFile());
        } catch (IOException e) {
            //Could be redundant. Error handled in Storage class as well, but keeping for extra security.
            System.out.println("File could not be loaded. Starting a new save file and list...");
            this.tasks = new TaskList(new ArrayList<Task>());
        }
        storage.updateTasks(this.tasks);
        this.ui = new Ui(this.tasks, this.storage);

    }

    public static void main(String[] args) throws IOException {
        new Duke("./duke.txt").run();
    }

    /**
     * When Ui and new Duke object has been created, this method
     * will support the ongoing session and quit once the Ui stops receiving input.
     */
    private void run() {
        ui.handleInput();
        System.exit(0);
    }
}
