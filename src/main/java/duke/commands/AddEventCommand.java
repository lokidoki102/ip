package duke.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.common.Utils;
import duke.storage.Storage;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.ui.Ui;

public class AddEventCommand extends Command {

    @Override
    public void execute(ArrayList<Task> tasks, String[] input)
            throws CommandException, IOException {
        String pattern =
                "([^/]+)\\s+/from\\s+(\\d{1,2}/\\d{1,2}/\\d{4}\\s+\\d{4})\\s+/to\\s+(\\d{1,2}/\\d{1,2}/\\d{4}\\s+\\d{4})";

        Pattern regex = Pattern.compile(pattern);

        // check if it doesnt follow the format of event <some string> /from <some
        // string> /to <some string>
        if (input.length < 2) {
            throw new CommandException(
                    "Please enter the event details! (format: event <your task> /from <dd/MM/yyyy HHmm> /to <dd/MM/yyyy HHmm>)");
        }

        Matcher matcher = regex.matcher(input[1]);

        if (!matcher.matches()) {
            throw new CommandException(
                    "Wrong format! (format: event <your task> /from <dd/MM/yyyy HHmm> /to <dd/MM/yyyy HHmm>)");
        }

        String[] eventDetails = input[1].split("/from|/to");

        if (!Utils.isValidDateTime(eventDetails[1], eventDetails[2])) {
            throw new CommandException(
                    "Datetime is in the wrong format. (format: event <your task> /from <dd/MM/yyyy HHmm> /to <dd/MM/yyyy HHmm>)");
        }

        Event eventTask = new Event(eventDetails[0].trim(),
                Utils.parseDateTime(eventDetails[1].trim()),
                Utils.parseDateTime(eventDetails[2].trim()));
        tasks.add(eventTask);

        Storage.writeToStorage(eventTask);

        Ui.printOutput("Got it. I've added this task:" + eventTask.toString(),
                "Now you have " + tasks.size() + " tasks in the list.");
    }

}
