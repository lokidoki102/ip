package duke.tasks;

import java.time.LocalDateTime;

import duke.common.Utils;

/**
 * The Event class represents an event task with a start and end time.
 */
public class Event extends Task {

    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Converts an event object into a string representation for storage, including its status,
     * description, and time range.
     * 
     * @return The method is returning a formatted string that represents the object's data in a
     *         storage-friendly format.
     */
    @Override
    public String toStorageString() {
        int statusValue = this.getStatus() ? 1 : 0;

        return String.format("event~%d~%s~%s~%s", statusValue, this.description,
                Utils.formatInput(this.from), Utils.formatInput(this.to));
    }

    /**
     * Returns a string representation of an Event with its status and description.
     * 
     * @return Returns a string representation of a task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + Utils.dateTimeToString(this.from)
                + " to: " + Utils.dateTimeToString(this.to) + ")";
    }
}
