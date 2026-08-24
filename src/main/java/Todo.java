public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    // Static override
    @Override
    public String getTypeIcon() {
        return "[T]";
    }
}