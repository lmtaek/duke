import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UiTest {

    private Parser parser;
    private TaskList tasks;
    private Storage storage;

    private String validFilePath = "./duke.txt";

    //Set-up
    public Ui newUi() {
        Ui newUi = new Ui(new TaskList(new ArrayList<>()), new Storage(validFilePath));
        return newUi;
    }

    @Test
    public void testAddTodo() {
        Ui ui = newUi();
        ui.handleInput();
        giveUserInput("todo wash car");

    }

    /**
     * In instances where user input must be used for an outcome, this method
     * takes in a predetermined String and treats it like a user command.
     * @param input: the user command.
     */
    public void giveUserInput(String input) {
        InputStream in = System.in;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Scanner scanner = new Scanner(System.in);
            System.out.println(scanner.nextLine());
        } finally {
            System.setIn(in);
        }
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
