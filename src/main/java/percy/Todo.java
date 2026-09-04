package percy;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTypeIcon() {
        return "[T]";
    }

    @Override
    public String getTypeLetter() {
        return "T";
    }
}