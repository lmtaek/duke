import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DukeTest {

    private String validFilePath = "./duke.txt";

    @Test
    public void dummyTest() {
        assertEquals(2, 2);
    }

    @Test
    public void testNewDuke() throws IOException {
        try {
            Duke duke = new Duke(validFilePath);
        } catch (IOException e) {
            fail();
        }
    }

    //ToDo: test Duke without access to valid filePath.
}
