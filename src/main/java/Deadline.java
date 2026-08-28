public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "[D]";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (by: " + by + ")";
    }

    @Override
    public String getTypeLetter() {
        return "D";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + by;
    }
}