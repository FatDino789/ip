import java.util.Scanner;

public class Percy {
    public static void main(String[] args) {
        // Chat Bot Banner
        String banner = " ____                        \n"
                + "|  _ \\ ___ _ __ ___ _   _ \n"
                + "| |_) / _ \\ '__/ __| | | |\n"
                + "|  __/  __/ | | (__| |_| |\n"
                + "|_|   \\___|_|  \\___|\\__, |\n"
                + "                    |___/ \n";

        // Horizontal Strings for Design
        String line = "____________________________________________________________";

        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println(banner);
        System.out.println("Hello! I'm Percy.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        // Continuously prompt until the user inputs bye
        while (true) {
            // Take in the user's input
            input = scanner.nextLine();

            // Check condition that the user inputted bye
            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            System.out.println(line);
            System.out.println(input);
            System.out.println(line);
        }
    }
}