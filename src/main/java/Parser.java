import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Makes sense of raw user input.
 *
 * <p>{@link #parse(String)} splits a line into a {@link CommandType} plus its
 * argument string. The {@code parseXxx} helpers then turn the arguments of a
 * specific command into the object it needs (a task, or a task index),
 * throwing {@link PercyException} with a friendly message when the input is
 * malformed. All parsing lives here so {@link Percy} only has to decide what
 * to do, not how to read the text.
 */
public class Parser {

    /** The kinds of command Percy understands. */
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, UNKNOWN
    }

    /**
     * A parsed command: its {@link CommandType} together with everything the
     * user typed after the command word (trimmed; may be an empty string).
     */
    public static class Command {
        private final CommandType type;
        private final String arguments;

        private Command(CommandType type, String arguments) {
            this.type = type;
            this.arguments = arguments;
        }

        public CommandType getType() {
            return type;
        }

        public String getArguments() {
            return arguments;
        }
    }

    /** Splits raw input into a command word and its argument string. */
    public static Command parse(String input) {
        String trimmed = input.trim();
        int firstSpace = trimmed.indexOf(' ');

        String commandWord;
        String arguments;
        if (firstSpace == -1) {
            commandWord = trimmed;
            arguments = "";
        } else {
            commandWord = trimmed.substring(0, firstSpace);
            arguments = trimmed.substring(firstSpace + 1).trim();
        }

        return new Command(toType(commandWord), arguments);
    }

    /** Maps a command word to its {@link CommandType}, or UNKNOWN if unrecognised. */
    private static CommandType toType(String commandWord) {
        switch (commandWord) {
        case "bye":
            return CommandType.BYE;
        case "list":
            return CommandType.LIST;
        case "mark":
            return CommandType.MARK;
        case "unmark":
            return CommandType.UNMARK;
        case "delete":
            return CommandType.DELETE;
        case "todo":
            return CommandType.TODO;
        case "deadline":
            return CommandType.DEADLINE;
        case "event":
            return CommandType.EVENT;
        default:
            return CommandType.UNKNOWN;
        }
    }

    /**
     * Parses a 1-based task number from {@code arguments} and converts it to a
     * 0-based index. Range checking against the actual list is left to the caller.
     *
     * @throws PercyException if the number is missing or not an integer.
     */
    public static int parseIndex(String arguments) throws PercyException {
        if (arguments.isEmpty()) {
            throw new PercyException("OOPS!!! Please specify which task number.");
        }
        try {
            return Integer.parseInt(arguments.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new PercyException("OOPS!!! Please enter a valid task number.");
        }
    }

    /**
     * Builds a {@link Todo} from a {@code todo} command's arguments.
     *
     * @throws PercyException if the description is empty.
     */
    public static Todo parseTodo(String arguments) throws PercyException {
        if (arguments.isEmpty()) {
            throw new PercyException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(arguments);
    }

    /**
     * Builds a {@link Deadline} from a {@code deadline} command's arguments,
     * which must have the form {@code <description> /by <yyyy-mm-dd>}.
     *
     * @throws PercyException if the description or date is missing or invalid.
     */
    public static Deadline parseDeadline(String arguments) throws PercyException {
        if (arguments.isEmpty()) {
            throw new PercyException("OOPS!!! The description of a deadline cannot be empty.");
        }
        String[] parts = arguments.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new PercyException(
                    "OOPS!!! A deadline needs a description and a '/by' date.");
        }
        try {
            LocalDate by = LocalDate.parse(parts[1].trim());
            return new Deadline(parts[0].trim(), by);
        } catch (DateTimeParseException e) {
            throw new PercyException(
                    "OOPS!!! Please enter the deadline date as yyyy-mm-dd, e.g. 2019-10-15.");
        }
    }

    /**
     * Builds an {@link Event} from an {@code event} command's arguments, which
     * must have the form {@code <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>}.
     *
     * @throws PercyException if any part is missing or a date is invalid.
     */
    public static Event parseEvent(String arguments) throws PercyException {
        if (arguments.isEmpty()) {
            throw new PercyException("OOPS!!! The description of an event cannot be empty.");
        }
        String[] fromParts = arguments.split(" /from ", 2);
        if (fromParts.length < 2 || fromParts[0].trim().isEmpty()) {
            throw new PercyException(
                    "OOPS!!! An event needs a description and '/from' and '/to' dates.");
        }
        String[] toParts = fromParts[1].split(" /to ", 2);
        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
            throw new PercyException(
                    "OOPS!!! An event needs a description and '/from' and '/to' dates.");
        }
        try {
            LocalDate from = LocalDate.parse(toParts[0].trim());
            LocalDate to = LocalDate.parse(toParts[1].trim());
            return new Event(fromParts[0].trim(), from, to);
        } catch (DateTimeParseException e) {
            throw new PercyException(
                    "OOPS!!! Please enter event dates as yyyy-mm-dd, e.g. 2019-10-15.");
        }
    }
}
