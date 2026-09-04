package percy;

/**
 * Entry point for the Percy task-tracking chatbot.
 *
 * <p>Percy's work is split across four helper classes; this class just wires
 * them together and runs the main command loop:
 * <ul>
 *   <li>{@link Ui} &ndash; reads input and prints responses</li>
 *   <li>{@link Storage} &ndash; loads and saves tasks to disk</li>
 *   <li>{@link TaskList} &ndash; holds the tasks in memory</li>
 *   <li>{@link Parser} &ndash; turns raw input into structured commands</li>
 * </ul>
 */
public class Percy {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /**
     * Creates a Percy instance that persists tasks to {@code filePath}.
     * If the existing save file cannot be read, Percy reports the problem and
     * starts with an empty task list rather than crashing.
     */
    public Percy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /** Runs the read&ndash;evaluate&ndash;respond loop until the user types {@code bye}. */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();
            Parser.Command command = Parser.parse(input);
            try {
                isExit = execute(command);
            } catch (PercyException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Carries out a single parsed command: updates the task list, saves to
     * disk when the list changed, and asks the {@link Ui} to report the result.
     *
     * @return true if Percy should exit after this command.
     * @throws PercyException if the command's arguments are invalid.
     */
    private boolean execute(Parser.Command command) throws PercyException {
        switch (command.getType()) {
        case BYE:
            ui.showGoodbye();
            return true;
        case LIST:
            ui.showTaskList(tasks);
            return false;
        case FIND:
            ui.showMatchingTasks(tasks.find(requireKeyword(command.getArguments())));
            return false;
        case MARK: {
            Task task = tasks.get(resolveIndex(command.getArguments()));
            task.markDone();
            storage.save(tasks);
            ui.showMarked(task);
            return false;
        }
        case UNMARK: {
            Task task = tasks.get(resolveIndex(command.getArguments()));
            task.unmarkDone();
            storage.save(tasks);
            ui.showUnmarked(task);
            return false;
        }
        case DELETE: {
            Task removed = tasks.remove(resolveIndex(command.getArguments()));
            storage.save(tasks);
            ui.showRemoved(removed, tasks.size());
            return false;
        }
        case TODO:
            return addTask(Parser.parseTodo(command.getArguments()));
        case DEADLINE:
            return addTask(Parser.parseDeadline(command.getArguments()));
        case EVENT:
            return addTask(Parser.parseEvent(command.getArguments()));
        default:
            throw new PercyException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /** Adds a task, persists the list, and reports it. Always keeps Percy running. */
    private boolean addTask(Task task) {
        tasks.add(task);
        storage.save(tasks);
        ui.showAdded(task, tasks.size());
        return false;
    }

    /**
     * Converts a 1-based task number typed by the user into a valid 0-based
     * index into the current list.
     *
     * @throws PercyException if the number is missing, non-numeric, or out of range.
     */
    private int resolveIndex(String arguments) throws PercyException {
        int index = Parser.parseIndex(arguments);
        if (!tasks.isValidIndex(index)) {
            throw new PercyException("OOPS!!! That task number doesn't exist.");
        }
        return index;
    }

    /**
     * Returns the search keyword typed after {@code find}.
     *
     * @throws PercyException if no keyword was given.
     */
    private String requireKeyword(String arguments) throws PercyException {
        if (arguments.isEmpty()) {
            throw new PercyException("OOPS!!! Please tell me what keyword to search for.");
        }
        return arguments;
    }

    public static void main(String[] args) {
        new Percy("./data/percy.txt").run();
    }
}
