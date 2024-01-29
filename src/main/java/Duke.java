import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

public class Duke {
    private final static String indentation = " ".repeat(3);
    private final static String subIndentation = indentation + " ";
    private final static String divider = "_".repeat(60);
    private final static String logo =
            " _               _          \n" + "    | |   _   _  ___| | ___   _ \n"
                    + "    | |  | | | |/ __| |/ / | | |      |\\__/,|   (`\\\n"
                    + "    | |__| |_| | (__|   <| |_| |    _.|o o  |_   ) )\n"
                    + "    |_____\\__,_|\\___|_|\\_\\\\__, |  -(((---(((--------\n"
                    + "                          |___/ ";

    public static void main(String[] args) {
        printOutput(logo, "Hello! I'm Lucky the cat", "What can I do for you?");
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        boolean isChatting = true;
        Command command;

        while (isChatting) {
            String[] input = sc.nextLine().split(" ", 2);

            command = Command.parseCommand(input[0]);

            switch (command) {
            case VIEW_LIST:
                printList(tasks);
                break;
            case EXIT:
                exit();
                break;
            case SET_MARK:
                updateMarkStatus(true, tasks, input);
                break;
            case SET_UNMARK:
                updateMarkStatus(false, tasks, input);
                break;
            case INSERT_TODO:
                insertToDo(input, tasks);
                break;
            case INSERT_DEADLINE:
                insertDeadline(input, tasks);
                break;
            case INSERT_EVENT:
                insertEvent(input, tasks);
                break;
            case DELETE_TASK:
                deleteTask(tasks, input);
                break;
            default:
                printOutput("I'm sorry, but I have zero idea what you're asking from me...");
                break;
            }
        }
        sc.close();
    }

    public static void printOutput(String... msg) {
        System.out.println(indentation + divider);

        for (String string : msg) {
            System.out.println(subIndentation + string);
        }
        System.out.println(indentation + divider + "\n");
    }

    public static void printList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Task task : tasks) {
            sb.append(i + "." + task.toString() + "\n" + subIndentation);
            i++;
        }
        printOutput("Here are the tasks in your list:", sb.toString());
    }

    public static void exit() {
        printOutput("Goodbye my friend. See you soon!");
        System.exit(0);
    }

    public static void updateMarkStatus(boolean isMark, ArrayList<Task> tasks, String[] input) {

        if (input.length < 2) {
            printOutput("Please specify which task. (format: mark/unmark <task no.>)");
            return;
        }

        if (!isInteger(input[1])) {
            printOutput("Task number not found! (format: mark/unmark <task no.>)");
            return;
        }

        int index = Integer.parseInt(input[1]) - 1;

        // check if index is within bounds
        if (index >= tasks.size()) {
            printOutput("Task not found!");
            return;
        }

        if (isMark) {
            // check if there's no change in status
            if (tasks.get(index).getStatus()) {
                printOutput("The task was already marked as done. I'm not changing anything.");
            } else {
                tasks.get(index).setStatus(true);
                printOutput("Nice! I've marked this task as done:", tasks.get(index).toString());
            }
        } else {
            // check if there's no change in status
            if (!tasks.get(index).getStatus()) {
                printOutput(
                        "The task you're unmarking was not marked to begin with... I'm not changing anything.");
            } else {
                tasks.get(index).setStatus(false);
                printOutput("OK, I've marked this task as not done yet: ",
                        tasks.get(index).toString());
            }
        }
    }

    public static void insertToDo(String[] input, ArrayList<Task> tasks) {

        if (input.length < 2) {
            printOutput("Please add the task description. (format: todo <task description>)");
            return;
        }

        ToDo todoTask = new ToDo(input[1]);
        tasks.add(todoTask);
        printOutput("Got it. I've added this task:", indentation + todoTask.toString(),
                "Now you have " + tasks.size() + 1 + " tasks in the list.");
    }

    public static void insertDeadline(String[] input, ArrayList<Task> tasks) {

        String pattern = "([^/]+)\\s+/by\\s+([^/]+)";
        Pattern regex = Pattern.compile(pattern);

        if (input.length < 2) {
            printOutput(
                    "Please enter the deadline details! (format: deadline <your task> /by <date>)");
            return;
        }

        Matcher matcher = regex.matcher(input[1]);

        if (!matcher.matches()) {
            printOutput("Wrong format! (format: deadline <your task> /by <date>)");
            return;
        }

        String[] deadlineDetails = input[1].split("/by");
        Deadline deadlineTask = new Deadline(deadlineDetails[0].trim(), deadlineDetails[1].trim());
        tasks.add(deadlineTask);
        printOutput("Got it. I've added this task:", indentation + deadlineTask.toString(),
                "Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void insertEvent(String[] input, ArrayList<Task> tasks) {

        String pattern = "([^/]+)\\s+/from\\s+([^/]+)\\s+/to\\s+([^/]+)";
        Pattern regex = Pattern.compile(pattern);

        // check if it doesnt follow the format of event <some string> /from <some
        // string> /to <some string>
        if (input.length < 2) {
            printOutput(
                    "Please enter the event details! (format: event <your task> /from <date> /to)");
            return;
        }

        Matcher matcher = regex.matcher(input[1]);

        if (!matcher.matches()) {
            printOutput("Wrong format! (format: event <your task> /from <date> /to)");
            return;
        }

        String[] eventDetails = input[1].split("/from|/to");
        Event eventTask =
                new Event(eventDetails[0].trim(), eventDetails[1].trim(), eventDetails[2]);
        tasks.add(eventTask);
        printOutput("Got it. I've added this task:", indentation + eventTask.toString(),
                "Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void deleteTask(ArrayList<Task> tasks, String[] input) {
        if (input.length < 2) {
            printOutput("Please specify which task to delete. (format: delete <task no.>)");
            return;
        }

        if (!isInteger(input[1])) {
            printOutput("Task number not found! (format: delete <task no.>)");
            return;
        }

        tasks.remove(Integer.parseInt(input[1]) - 1);
        printOutput("Noted. I've removed this task: ",
                tasks.get(Integer.parseInt(input[1]) - 1).toString(),
                "Now you have " + tasks.size() + " tasks in the list.");
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
