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
                    System.out.println((i + 1) + "." + tasks[i].getTypeIcon() + tasks[i].getStatusIcon() + " " + tasks[i].getDescription());
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
            } else if (input.equals("todo") || input.startsWith("todo ")) {
                // Marking Task as Todo
                String description = input.length() > 4 ? input.substring(5).trim() : "";
                // Error tracking if no input after Todo
                if (description.isEmpty()) {
                    System.out.println(line);
                    System.out.println("OOPS!!! The description of a todo cannot be empty.");
                    System.out.println(line);
                } else {
                    tasks[taskCount] = new Todo(description);
                    taskCount++;
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1].getTypeIcon() + tasks[taskCount - 1].getStatusIcon() + " " + tasks[taskCount - 1].getDescription());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);
                }
            }  else if (input.equals("deadline") || input.startsWith("deadline ")) {
                // Marking task as deadline
                String details = input.length() > 8 ? input.substring(9).trim() : "";
                // Error tracing if no input after deadline
                if (details.isEmpty()) {
                    System.out.println(line);
                    System.out.println("OOPS!!! The description of a deadline cannot be empty.");
                    System.out.println(line);
                } else {
                    String[] parts = details.split(" /by ", 2);
                    String description = parts[0];
                    String by = parts[1];
                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1].getTypeIcon() + tasks[taskCount - 1].getStatusIcon() + " " + tasks[taskCount - 1].getDescription());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);
                }
            } else if (input.equals("event") || input.startsWith("event ")) {
                // Marking task as event
                String details = input.length() > 5 ? input.substring(6).trim() : "";
                // Error track
                if (details.isEmpty()) {
                    System.out.println(line);
                    System.out.println("OOPS!!! The description of an event cannot be empty.");
                    System.out.println(line);
                } else {
                    String[] fromParts = details.split(" /from ", 2);
                    String description = fromParts[0];
                    String[] toParts = fromParts[1].split(" /to ", 2);
                    String from = toParts[0];
                    String to = toParts[1];
                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1].getTypeIcon() + tasks[taskCount - 1].getStatusIcon() + " " + tasks[taskCount - 1].getDescription());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);
                }
            } else {
                // Other inputs and commands now return error
                System.out.println(line);
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println(line);
            }
        }
    }
}
