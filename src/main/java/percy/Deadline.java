package percy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that must be done by a specific date.
 */
public class Deadline extends Task {
    // Updated new type for by variable
    private LocalDate by;

    /**
     * Creates a deadline.
     *
     * @param description what needs to be done
     * @param by the date the task is due
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "[D]";
    }

    /**
     * {@inheritDoc}
     *
     * <p>Appends the due date in a friendly {@code MMM dd yyyy} format,
     * e.g. {@code "return book (by: Oct 15 2019)"}.
     */
    @Override
    public String getDescription() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return super.getDescription() + " (by: " + by.format(outputFormat) + ")";
    }

    @Override
    public String getTypeLetter() {
        return "D";
    }

    /**
     * {@inheritDoc}
     *
     * <p>Appends the due date in ISO {@code yyyy-mm-dd} form so it can be
     * parsed back when loading.
     */
    @Override
    public String toFileFormat() {
        // Required function toString to concatenate strings for print
        return super.toFileFormat() + " | " + by.toString();
    }
}
