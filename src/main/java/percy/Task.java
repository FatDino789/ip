package percy;

/**
 * A single task tracked by Percy.
 *
 * <p>Holds the description and the done/not-done state common to every task
 * type. Subclasses ({@link Todo}, {@link Deadline}, {@link Event}) add their
 * own extra fields and override the rendering methods.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a task that starts out not done.
     *
     * @param description the text describing what needs to be done
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void unmarkDone() {
        isDone = false;
    }

    /**
     * Returns the checkbox shown in the task list.
     *
     * @return {@code "[X]"} if done, {@code "[ ]"} otherwise
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the text shown to the user for this task. Subclasses append
     * extra detail (such as dates) to the base description.
     *
     * @return the display description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type tag shown in the task list, e.g. {@code "[T]"}.
     * The base task has no type, so this returns an empty string.
     *
     * @return the type icon, or an empty string if there is none
     */
    public String getTypeIcon() {
        return "";
    }

    /**
     * Returns whether this task is done.
     *
     * @return true if the task has been marked done
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the plain description without any type-specific detail. Used when
     * saving the task to disk.
     *
     * @return the raw description text
     */
    public String getRawDescription() {
        return description;
    }

    /**
     * Returns the single letter used to identify this task's type in the save
     * file, e.g. {@code "T"}. The base task has no type letter.
     *
     * @return the type letter, or an empty string if there is none
     */
    public String getTypeLetter() {
        return "";
    }

    /**
     * Encodes this task as one line of the save file, in the form
     * {@code <type> | <0 or 1> | <description>}. Subclasses append their extra
     * fields after the description.
     *
     * @return the save-file representation of this task
     */
    public String toFileFormat() {
        return getTypeLetter() + " | " + (isDone() ? "1" : "0") + " | " + getRawDescription();
    }
}
