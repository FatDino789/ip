package percy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    // Updated new type for by variable
    private LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "[D]";
    }

    @Override
    public String getDescription() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return super.getDescription() + " (by: " + by.format(outputFormat) + ")";
    }

    @Override
    public String getTypeLetter() {
        return "D";
    }

    @Override
    public String toFileFormat() {
        // Required function toString to concatenate strings for print
        return super.toFileFormat() + " | " + by.toString();
    }
}