package percy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task that spans a period between two dates.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Creates an event.
     *
     * @param description what the event is
     * @param from the start date
     * @param to the end date
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "[E]";
    }

    /**
     * {@inheritDoc}
     *
     * <p>Appends the date range in a friendly {@code MMM dd yyyy} format,
     * e.g. {@code "project (from: Oct 10 2019 to: Oct 12 2019)"}.
     */
    @Override
    public String getDescription() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return super.getDescription() + " (from: " + from.format(outputFormat) + " to: " + to.format(outputFormat) + ")";
    }

    @Override
    public String getTypeLetter() {
        return "E";
    }

    /**
     * {@inheritDoc}
     *
     * <p>Appends both dates in ISO {@code yyyy-mm-dd} form so they can be
     * parsed back when loading.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + from + " | " + to;
    }
}
