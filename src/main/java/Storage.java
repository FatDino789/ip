import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.time.LocalDate;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Load tasks from the save file. Returns an empty list if the file
    // or its parent folder doesn't exist yet, or if it's corrupted.
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("OOPS!!! Could not load saved tasks.");
        }

        return tasks;
    }

    // Parses one line of the save file into a Task. Returns null if the
    // line is corrupted/unrecognized, so it can be safely skipped.
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

    // Saves the full task list to disk, creating the data folder first if needed.
    public void save(TaskList tasks) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).toFileFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("OOPS!!! Could not save tasks.");
        }
    }
}