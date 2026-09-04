package percy;

import java.util.Scanner;

/**
 * Handles all interaction with the user: reading commands from standard input
 * and printing Percy's responses (including the banner, dividers and errors)
 * to standard output.
 *
 * <p>Keeping every {@code System.out} / {@code Scanner} call in this one class
 * means the rest of the program never talks to the console directly, so the
 * wording of messages can be changed in a single place.
 */
public class Ui {
    /** Horizontal divider used to frame each response. */
    private static final String LINE =
            "____________________________________________________________";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Prints the startup banner and greeting. */
    public void showWelcome() {
        String banner = " ____                        \n"
                + "|  _ \\ ___ _ __ ___ _   _ \n"
                + "| |_) / _ \\ '__/ __| | | |\n"
                + "|  __/  __/ | | (__| |_| |\n"
                + "|_|   \\___|_|  \\___|\\__, |\n"
                + "                    |___/ \n";
        System.out.println(banner);
        System.out.println("Hello! I'm Percy.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /** Reads the next command line typed by the user. */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Prints the horizontal divider. */
    public void showLine() {
        System.out.println(LINE);
    }

    /** Prints the farewell message shown when the user types {@code bye}. */
    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /** Prints an error message framed by dividers. */
    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    /** Shown when the save file exists but could not be read on startup. */
    public void showLoadingError() {
        showError("OOPS!!! Could not load saved tasks. Starting with an empty list.");
    }

    /** Prints every task in the list, numbered from 1. */
    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + "." + task.getTypeIcon()
                    + task.getStatusIcon() + " " + task.getDescription());
        }
        showLine();
    }

    /** Prints the tasks that matched a {@code find} search, numbered from 1. */
    public void showMatchingTasks(TaskList matches) {
        showLine();
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            Task task = matches.get(i);
            System.out.println((i + 1) + "." + task.getTypeIcon()
                    + task.getStatusIcon() + " " + task.getDescription());
        }
        showLine();
    }

    /** Confirms that a task was added and reports the new total. */
    public void showAdded(Task task, int total) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + formatTask(task));
        System.out.println("Now you have " + total + " tasks in the list.");
        showLine();
    }

    /** Confirms that a task was removed and reports the new total. */
    public void showRemoved(Task task, int total) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + formatTask(task));
        System.out.println("Now you have " + total + " tasks in the list.");
        showLine();
    }

    /** Confirms that a task was marked as done. */
    public void showMarked(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + formatTask(task));
        showLine();
    }

    /** Confirms that a task was marked as not done. */
    public void showUnmarked(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + formatTask(task));
        showLine();
    }

    /** Shared one-line rendering of a task: type icon, status icon, description. */
    private String formatTask(Task task) {
        return task.getTypeIcon() + task.getStatusIcon() + " " + task.getDescription();
    }
}
