/**
 * Represents an error that Percy itself raises, such as an unknown command
 * or a badly formed argument.
 *
 * <p>Having a dedicated exception type lets the main loop catch Percy's own
 * expected errors (and show a friendly message) separately from unexpected
 * bugs, which are allowed to surface normally.
 */
public class PercyException extends Exception {
    public PercyException(String message) {
        super(message);
    }
}
