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

        // Initialize the list of tasks
        Task[] tasks = new Task[100];
        int taskCount = 0;

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
            } else if (input.equals("list")) {
                // Output the list when input is list
                System.out.println(line);
                System.out.println("Here are the tasks in your list:");
                // Add the indication for task completion
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i].getStatusIcon() + " " + tasks[i].getDescription());
                }
                System.out.println(line);
            } else if (input.startsWith("mark ")) {
                // Introduce the mark function to mark tasks as complete
                int taskNum = Integer.parseInt(input.substring(5));
                int index = taskNum - 1;
                tasks[index].markDone();
                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [X] " + tasks[index].getDescription());
                System.out.println(line);
            } else if (input.startsWith("unmark ")) {
                // Introduce the unmark function to unmark tasks as complete
                int taskNum = Integer.parseInt(input.substring(7));
                int index = taskNum - 1;
                tasks[index].unmarkDone();
                System.out.println(line);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [ ] " + tasks[index].getDescription());
                System.out.println(line);
            } else {
                // Store the input into a list
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(line);
                System.out.println("added: " + input);
                System.out.println(line);
            }
        }
    }
}
