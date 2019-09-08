import java.util.ArrayList;

public class UiTest {

    private Parser parser;
    private TaskList tasks;
    private Storage storage;

    private String validFilePath = "./duke.txt";

    public Ui newUi() {
        Ui newUi = new Ui(new TaskList(new ArrayList<>()), new Storage(validFilePath));
        return newUi;
    }

    /**
     * ToDo: Test adding ToDos and incorrect inputs.
     * ToDo: Test adding Events and incorrect inputs.
     * ToDo: Test adding Deadlines and incorrect inputs.
     * ToDo: Test 'find' feature.
     * ToDo: Test 'delete' feature.
     * ToDo: Test 'bye' feature.
     */
}
