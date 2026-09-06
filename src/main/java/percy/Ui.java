package percy;

import java.util.Scanner;

/**
 * Handles interaction with the user.
 *
 * <p>Responses are collected into a buffer rather than printed directly, so the
 * same methods serve both the command-line loop (which prints {@link #flush()})
 * and the GUI (which shows it in a dialog box). Keeping every response string in
 * this one class means their wording can be changed in a single place.
 */
public class Ui {
    /** Horizontal divider used to frame each response. */
    private static final String LINE =
            "____________________________________________________________";

    private final Scanner scanner;
    private final StringBuilder buffer = new StringBuilder();

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Appends one line to the pending response. */
    private void print(String text) {
        buffer.append(text).append(System.lineSeparator());
    }

    /**
     * Returns everything shown since the last call and clears the buffer.
     *
     * @return the accumulated response text
     */
    public String flush() {
        String out = buffer.toString();
        buffer.setLength(0);
        return out;
    }

    /** Reads the next command line typed by the user (command-line mode only). */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Adds the startup banner and greeting. */
    public void showWelcome() {
        String banner = " ____                        \n"
                + "|  _ \\ ___ _ __ ___ _   _ \n"
                + "| |_) / _ \\ '__/ __| | | |\n"
                + "|  __/  __/ | | (__| |_| |\n"
                + "|_|   \\___|_|  \\___|\\__, |\n"
                + "                    |___/ \n";
        print(banner);
        print("Hello! I'm Percy.");
        print("What can I do for you?");
        showLine();
    }

    /** Adds the horizontal divider. */
    public void showLine() {
        print(LINE);
    }

    /** Adds the farewell message shown when the user types {@code bye}. */
    public void showGoodbye() {
        showLine();
        print("Bye. Hope to see you again soon!");
        showLine();
    }

    /** Adds an error message framed by dividers. */
    public void showError(String message) {
        showLine();
        print(message);
        showLine();
    }

    /** Shown when the save file exists but could not be read on startup. */
    public void showLoadingError() {
        showError("OOPS!!! Could not load saved tasks. Starting with an empty list.");
    }

    /** Adds every task in the list, numbered from 1. */
    public void showTaskList(TaskList tasks) {
        showLine();
        print("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            print((i + 1) + "." + task.getTypeIcon()
                    + task.getStatusIcon() + " " + task.getDescription());
        }
        showLine();
    }

    /** Adds the tasks that matched a {@code find} search, numbered from 1. */
    public void showMatchingTasks(TaskList matches) {
        showLine();
        print("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            Task task = matches.get(i);
            print((i + 1) + "." + task.getTypeIcon()
                    + task.getStatusIcon() + " " + task.getDescription());
        }
        showLine();
    }

    /** Confirms that a task was added and reports the new total. */
    public void showAdded(Task task, int total) {
        showLine();
        print("Got it. I've added this task:");
        print("  " + formatTask(task));
        print("Now you have " + total + " tasks in the list.");
        showLine();
    }

    /** Confirms that a task was removed and reports the new total. */
    public void showRemoved(Task task, int total) {
        showLine();
        print("Noted. I've removed this task:");
        print("  " + formatTask(task));
        print("Now you have " + total + " tasks in the list.");
        showLine();
    }

    /** Confirms that a task was marked as done. */
    public void showMarked(Task task) {
        showLine();
        print("Nice! I've marked this task as done:");
        print("  " + formatTask(task));
        showLine();
    }

    /** Confirms that a task was marked as not done. */
    public void showUnmarked(Task task) {
        showLine();
        print("OK, I've marked this task as not done yet:");
        print("  " + formatTask(task));
        showLine();
    }

    /** Shared one-line rendering of a task: type icon, status icon, description. */
    private String formatTask(Task task) {
        return task.getTypeIcon() + task.getStatusIcon() + " " + task.getDescription();
    }
}
