package duke.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.common.Utils;
import duke.storage.Storage;
import duke.tasks.Deadline;
import duke.tasks.Task;
import duke.ui.Ui;

public class AddDeadlineCommand extends Command {

    @Override
    public void execute(ArrayList<Task> tasks, String[] input)
            throws CommandException, IOException {
        String pattern = "([^/]+)\\s+/by\\s+(\\d{1,2}/\\d{1,2}/\\d{4}\\s+\\d{4})";
        Pattern regex = Pattern.compile(pattern);

        if (input.length < 2) {
            throw new CommandException(
                    "Please enter the deadline details! (format: deadline <your task> /by <dd/MM/yyyy HHmm>)");
        }

        Matcher matcher = regex.matcher(input[1]);

        if (!matcher.matches()) {
            throw new CommandException(
                    "Wrong format! (format: deadline <your task> /by <dd/MM/yyyy HHmm>)");
        }

        String[] deadlineDetails = input[1].split("/by");

        if (!Utils.isValidDateTime(deadlineDetails[1])) {
            throw new CommandException(
                    "Datetime is in the wrong format. (format: deadline <your task> /by <dd/MM/yyyy HHmm>)");
        }

        Deadline deadlineTask = new Deadline(deadlineDetails[0].trim(),
                Utils.parseDateTime(deadlineDetails[1].trim()));

        tasks.add(deadlineTask);

        Storage.writeToStorage(deadlineTask);

        Ui.printOutput("Got it. I've added this task:" + deadlineTask.toString(),
                "Now you have " + tasks.size() + " tasks in the list.");
    }

}
