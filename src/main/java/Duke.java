import java.util.Scanner;

public class Duke {

    private static String dukeGreeting = "Hello, I'm Duke.\nWhat can I do to help you?";
    private static String[] taskList = new String[100];
    private static int listLength = 0;


    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        System.out.println(dukeGreeting + "\r\n");

        // The user will enter commands from this point onwards.
        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()) {
            String userInput = input.nextLine();

            if (userInput.toLowerCase().equals("bye")) {
                closeApplication();
            }
            else if (userInput.toLowerCase().equals("list")) {
                System.out.println(readTaskList());
            } else {
                System.out.println(addToList(userInput));
            }
            continue;
        }

    }

    static void closeApplication() {
        System.out.println("\tBye. Hope to see you again soon!");
        System.exit(0);
    }

    static String readTaskList() {
        String response = "";
        for (int i=0; i<listLength; i++) {
            response = response + "\t" + (i+1) + ". " + taskList[i] + "\n";
        }
        return response;
    }

    static String addToList(String input) {
        if (listLength >= 100) {
            return "\tYou can't add any more tasks to your list!";
        }
        else {
            taskList[listLength] = input;
            listLength++;
            return "\tadded: " + input;
        }
    }


}
