import TaskPackage.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class containing the main method. Establishes current session's Ui, TaskList, and Storage,
 * and cues Ui to begin taking input from the user.
 */
public class Duke {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    /**
     * This method prepares the user's new session with Duke. It creates a Storage, TaskList, and Ui class to use during the session.
     * If the filePath referenced when making the new Storage has been used previously and a file with data is present,
     * the data will be read and the list from the previous session is used.
     * @param filePath The relative path of the saved data file from a previous session.
     * @throws IOException If the Storage class cannot successfully load the data file, this exception is thrown.
     */
    public Duke(String filePath) throws IOException {
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.loadFile());
        } catch (IOException e) {
            System.out.println("File could not be loaded. Starting a new save file and list...");
            this.tasks = new TaskList(new ArrayList<Task>());
        }
        storage.updateTasks(this.tasks);
        this.ui = new Ui(this.tasks, this.storage);

    }

    /**
     * This method prompts the Duke to begin receiving input, and recording input to the file in the referenced filePath.
     * @param args
     * @throws IOException
     */
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
