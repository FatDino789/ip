import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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

        // Initialize storage and load any previously saved tasks
        Storage storage = new Storage("./data/percy.txt");
        ArrayList<Task> tasks = storage.load();

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
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i).getTypeIcon() + tasks.get(i).getStatusIcon() + " " + tasks.get(i).getDescription());
                }
                System.out.println(line);
            } else if (input.startsWith("mark ")) {
                // Introduce the mark function to mark tasks as complete
                int taskNum = Integer.parseInt(input.substring(5));
                int index = taskNum - 1;
                tasks.get(index).markDone();
                storage.save(tasks);
                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks.get(index).getTypeIcon() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription());
                System.out.println(line);
            } else if (input.startsWith("unmark ")) {
                // Introduce the unmark function to unmark tasks as complete
                int taskNum = Integer.parseInt(input.substring(7));
                int index = taskNum - 1;
                tasks.get(index).unmarkDone();
                storage.save(tasks);
                System.out.println(line);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks.get(index).getTypeIcon() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription());
                System.out.println(line);
            } else if (input.equals("delete") || input.startsWith("delete ")) {
                // Handling task deletion
                String numberText = input.length() > 6 ? input.substring(7).trim() : "";
                if (numberText.isEmpty()) {
                    System.out.println(line);
                    System.out.println("OOPS!!! Please specify which task number to delete.");
                    System.out.println(line);
                } else {
                    try {
                        int taskNum = Integer.parseInt(numberText);
                        int index = taskNum - 1;
                        if (index < 0 || index >= tasks.size()) {
                            System.out.println(line);
                            System.out.println("OOPS!!! That task number doesn't exist.");
                            System.out.println(line);
                        } else {
                            Task removed = tasks.remove(index);
                            storage.save(tasks);
                            System.out.println(line);
                            System.out.println("Noted. I've removed this task:");
                            System.out.println("  " + removed.getTypeIcon() + removed.getStatusIcon() + " " + removed.getDescription());
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            System.out.println(line);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(line);
                        System.out.println("OOPS!!! Please enter a valid task number.");
                        System.out.println(line);
                    }
                }
            } else if (input.equals("todo") || input.startsWith("todo ")) {
                // Marking Task as Todo
                String description = input.length() > 4 ? input.substring(5).trim() : "";
                // Error tracking if no input after Todo
                if (description.isEmpty()) {
                    System.out.println(line);
                    System.out.println("OOPS!!! The description of a todo cannot be empty.");
                    System.out.println(line);
                } else {
                    tasks.add(new Todo(description));
                    storage.save(tasks);
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1).getTypeIcon() + tasks.get(tasks.size() - 1).getStatusIcon() + " " + tasks.get(tasks.size() - 1).getDescription());
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
                    String byText = parts[1];
                    try {
                        LocalDate by = LocalDate.parse(byText);
                        tasks.add(new Deadline(description, by));
                        storage.save(tasks);
                        System.out.println(line);
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + tasks.get(tasks.size() - 1).getTypeIcon() + tasks.get(tasks.size() - 1).getStatusIcon() + " " + tasks.get(tasks.size() - 1).getDescription());
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                    } catch (DateTimeParseException e) {
                        System.out.println(line);
                        System.out.println("OOPS!!! Please enter the deadline date as yyyy-mm-dd, e.g. 2019-10-15.");
                        System.out.println(line);
                    }
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
                    tasks.add(new Event(description, from, to));
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1).getTypeIcon() + tasks.get(tasks.size() - 1).getStatusIcon() + " " + tasks.get(tasks.size() - 1).getDescription());
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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