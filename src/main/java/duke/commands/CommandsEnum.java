package duke.commands;

public enum CommandsEnum {
    INSERT_TODO, VIEW_LIST, INSERT_DEADLINE, INSERT_EVENT, SET_MARK, SET_UNMARK, EXIT, DELETE_TASK, FIND, DEFAULT;

    public static CommandsEnum getCommandEnum(String in) {
        in = in.toLowerCase();

        switch (in) {
        case "todo":
            return INSERT_TODO;
        case "list":
            return VIEW_LIST;
        case "deadline":
            return INSERT_DEADLINE;
        case "event":
            return INSERT_EVENT;
        case "mark":
            return SET_MARK;
        case "unmark":
            return SET_UNMARK;
        case "bye":
            return EXIT;
        case "delete":
            return DELETE_TASK;
        case "find":
            return FIND;
        default:
            return DEFAULT;
        }
    }
}
