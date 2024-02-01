package duke.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import duke.tasks.Task;

public class MarkCommandTest {
    @Test
    public void execute_validInput_success() throws IOException, CommandException {
        MarkCommand markCommand = new MarkCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        Task task = new Task("Sample task");
        tasks.add(task);

        String[] input = { "mark", "1" };

        assertDoesNotThrow(() -> markCommand.execute(tasks, input));
        assertTrue(task.getStatus());
    }

    @Test
    public void execute_invalidInput_throwsCommandException() {
        MarkCommand markCommand = new MarkCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String[] input = { "mark" };

        assertThrows(CommandException.class, () -> markCommand.execute(tasks, input));
    }

    @Test
    public void execute_nonExistentTaskIndex_throwsCommandException() {
        MarkCommand markCommand = new MarkCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        String[] input = { "mark", "1" };

        assertThrows(CommandException.class, () -> markCommand.execute(tasks, input));
    }

    @Test
    public void execute_alreadyMarkedTask_throwsCommandException() {
        MarkCommand markCommand = new MarkCommand();
        ArrayList<Task> tasks = new ArrayList<>();
        Task task = new Task("Sample task");
        task.setStatus(true);
        tasks.add(task);

        String[] input = { "mark", "1" };

        assertThrows(CommandException.class, () -> markCommand.execute(tasks, input));
    }
}
