// Task class that contains the properties to be tracked for each task
public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        isDone = true;
    }

    public void unmarkDone() {
        isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    public String getDescription() {
        return description;
    }

    // Method to be overriden by subclass
    public String getTypeIcon() {
        return "";
    }

    public boolean isDone() {
        return isDone;
    }

    public String getRawDescription() {
        return description;
    }

    public String getTypeLetter() {
        return "";
    }

    public String toFileFormat() {
        return getTypeLetter() + " | " + (isDone() ? "1" : "0") + " | " + getRawDescription();
    }
}