import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    private String validToDoInput = "todo make a list";
    private String invalidToDoInput = "todofinish project";

    private String validDeadlineInput = "deadline submit application /by 9:45";
    private String invalidDeadlineInput = "deadline submit applicationby 9:45";

    private String validEventInput = "event Dinner With Family /at 09/30/1998 1800";
    private String invalidEventInput = "event /at 09/30/1998 1800";

    private String validFindInput = "find with";
    private String invalidFindInput = "findwith";

    private String validDeleteInput = "delete 1";
    private String invalidDeleteInput = "delete that";

    private String validDoneInput = "done 1";
    private String invalidDoneInput = "done -1";

    private String badFormat = "badFormat";

    @Test
    public void testParseToDo() {
        Parser validTestParser = new Parser(validToDoInput);
        String validOutput = validTestParser.parseToDo();
        assertEquals("make a list", validOutput);

        Parser invalidTestParser = new Parser(invalidToDoInput);
        String invalidOutput = invalidTestParser.parseToDo();
        assertEquals(badFormat, invalidOutput);

    }

    @Test
    public void testParseDeadline() {
        Parser validTestParser = new Parser(validDeadlineInput);
        String[] validOutput = validTestParser.parseDeadline();
        assertEquals("submit application", validOutput[0]);
        assertEquals("9:45", validOutput[1]);

        Parser invalidTestParser = new Parser(invalidDeadlineInput);
        String[] invalidOutput = invalidTestParser.parseDeadline();
        assertEquals(0, invalidOutput.length);
    }

    @Test
    public void testParseEvent() {
        Parser validTestParser = new Parser(validEventInput);
        String[] validOutput = validTestParser.parseEvent();
        assertEquals("Dinner With Family", validOutput[0]);
        assertEquals("09/30/1998 1800", validOutput[1]);

        Parser invalidTestParser = new Parser(invalidEventInput);
        String[] invalidOutput = invalidTestParser.parseEvent();
        assertEquals(0, invalidOutput.length);
    }

    @Test
    public void testParseFind() {
        Parser validTestParser = new Parser(validFindInput);
        String validOutput = validTestParser.parseFind();
        assertEquals("with", validOutput);

        Parser invalidTestParser = new Parser(invalidFindInput);
        String invalidOutput = invalidTestParser.parseFind();
        assertEquals(badFormat, invalidOutput);
    }

    @Test
    public void testParseDone() {
        Parser validTestParser = new Parser(validDoneInput);
        int validOutput = validTestParser.parseDone();
        assertEquals(1, validOutput);

        Parser invalidTestParser = new Parser(invalidDoneInput);
        int invalidOutput = invalidTestParser.parseDone();
        assertEquals(-1, invalidOutput);
    }

    @Test
    public void testParseDelete() {
        Parser validTestParser = new Parser(validDeleteInput);
        int validOutput = validTestParser.parseDelete();
        assertEquals(1, validOutput);

        Parser invalidTestParser = new Parser(invalidDeleteInput);
        int invalidOutput = invalidTestParser.parseDelete();
        assertEquals(-1, invalidOutput);

    }
}
