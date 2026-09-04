package percy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "[E]";
    }

    @Override
    public String getDescription() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return super.getDescription() + " (from: " + from.format(outputFormat) + " to: " + to.format(outputFormat) + ")";
    }

    @Override
    public String getTypeLetter() {
        return "E";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + from + " | " + to;
    }
}