package percy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Loads tasks from the save file at startup and writes them back whenever the
 * list changes, so tasks persist between runs.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage handler for the given save-file path.
     *
     * @param filePath path to the file tasks are read from and written to
     */
    public Storage(String filePath) {
        assert filePath != null : "storage file path should not be null";
        this.filePath = filePath;
    }

    /**
     * Loads all tasks from the save file.
     *
     * @return the saved tasks, or an empty list if the file does not exist yet.
     *     Lines that are corrupted or unrecognised are skipped rather than
     *     aborting the load.
     */
    public ArrayList<Task> load() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return Files.readAllLines(file.toPath()).stream()
                    .map(this::parseLine)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            System.out.println("OOPS!!! Could not load saved tasks.");
            return new ArrayList<>();
        }
    }

    /**
     * Parses one line of the save file into a {@link Task}.
     *
     * @param line a single line in {@code <type> | <0/1> | <description> ...} form
     * @return the reconstructed task, or {@code null} if the line is corrupted
     *     or its type is unrecognised, so the caller can safely skip it
     */
    private Task parseLine(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];
            Task task;

            if (type.equals("T")) {
                task = new Todo(description);
            } else if (type.equals("D")) {
                task = new Deadline(description, LocalDate.parse(parts[3]));
            } else if (type.equals("E")) {
                task = new Event(description, LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
            } else {
                return null;
            }

            if (isDone) {
                task.markDone();
            }
            return task;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Writes the whole task list to the save file, overwriting its previous
     * contents. The parent folder is created first if it does not exist.
     *
     * @param tasks the task list to persist
     */
    public void save(TaskList tasks) {
        assert tasks != null : "task list to save should not be null";
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        String content = tasks.stream()
                .map(Task::toFileFormat)
                .map(line -> line + System.lineSeparator())
                .collect(Collectors.joining());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("OOPS!!! Could not save tasks.");
        }
    }
}
