package duke.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles storage and retrieval of the tasks.
 */
public class Storage {
    private String filePath;

    /**
     * Takes in the filePath for future I/O.
     * @param filePath String representing the path of the file to be written and read from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads in data from an existing file into a created TaskList object.
     * @return TaskList object consisting of the data read from the file.
     * @throws DukeException Thrown when the file does not exist
     */
    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            if (Files.isRegularFile(Paths.get(this.filePath))) {
                List<String> input = Files.readAllLines(Paths.get(this.filePath));

                for (String value : input) {
                    String[] splitInput = value.split(" \\| ");

                    if (value.charAt(0) == 'E') {
                        Event newEvent = new Event(splitInput[2], parseDate(splitInput[3]));
                        if (splitInput[1].equals("1")) {
                            newEvent.markAsDone();
                        }
                        taskList.add(newEvent);
                    } else if (value.charAt(0) == 'T') {
                        ToDo newToDo = new ToDo(splitInput[2]);
                        if (splitInput[1].equals("1")) {
                            newToDo.markAsDone();
                        }
                        taskList.add(newToDo);
                    } else if (value.charAt(0) == 'D') {
                        Deadline newDeadline = new Deadline(splitInput[2], parseDate(splitInput[3]));
                        if (splitInput[1].equals("1")) {
                            newDeadline.markAsDone();
                        }
                        taskList.add(newDeadline);
                    } else if (value.charAt(0) == 'F') {
                        FixedDuration newFixedDuration = new FixedDuration(splitInput[2],
                                Duration.parse(splitInput[3]));
                        if (splitInput[1].equals("1")) {
                            newFixedDuration.markAsDone();
                        }
                        taskList.add(newFixedDuration);
                    }
                }
            } else {
                throw new DukeException("");
            }
        } catch (IOException e) {
            createFileAndDirectory();
        }
        return taskList;
    }

    /**
     * Creates the directory and file as given by the file path initialized in the constructor.
     */
    private void createFileAndDirectory() {
        try {
            File myNewFile = new File(this.filePath);
            Files.createDirectory(Paths.get(myNewFile.getParent()));
            Files.createFile(Paths.get(this.filePath));
        } catch (FileAlreadyExistsException e) {
            return;
        } catch (IOException e) {
            createFileAndDirectory();
        }
    }

    private LocalDateTime parseDate(String dateToParse) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateToParse, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date read from file.");
            return null;
        }
    }

    /**
     * Converts the LocalDateTime object into printable string for writing to file.
     * @param dateTime LocalDateTime object to be converted
     * @return String format of the LocalDateTime object
     */
    private String unparseDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    /**
     * Creates the file as necessary, reads the TaskList and converts each value into a string and writes it to file.
     */
    public void saveToFile() {
        String toSave = "";
        createFileAndDirectory();

        for (int i = 0; i < TaskList.getSize(); ++i) {
            Task value = TaskList.getTask(i);
            String taskType = "";
            String className = value.getClass().getSimpleName();
            int isDone = 0;
            String description = value.description;
            String newTiming = "";

            if (className.equals("ToDo")) {
                taskType = "T";
            } else if (className.equals("Deadline")) {
                taskType = "D";
                newTiming = unparseDate(((Deadline) value).by);
            } else if (className.equals("Event")) {
                taskType = "E";
                newTiming = unparseDate(((Event) value).at);
            } else if (className.equals("FixedDuration")) {
                taskType = "F";
                newTiming = ((FixedDuration) value).duration.toString();
            }

            if (value.isDone) {
                isDone = 1;
            } else {
                isDone = 0;
            }
            if (newTiming != "") {
                toSave += taskType + " | " + Integer.toString(isDone) + " | " + description
                        + " | " + newTiming + "\n";
            } else {
                toSave += taskType + " | " + Integer.toString(isDone) + " | " + description + "\n";
            }
        }
        try {
            Files.writeString(Paths.get(this.filePath), toSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
