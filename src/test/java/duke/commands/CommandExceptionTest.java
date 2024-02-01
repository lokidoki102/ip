package duke.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandExceptionTest {
    
    @Test
    public void constructor_withMessage_messageIsSet() {
        String errorMessage = "Test error message";

        CommandException commandException = new CommandException(errorMessage);

        assertEquals(errorMessage, commandException.getMessage());
    }

    @Test
    public void constructor_withoutMessage_defaultMessageIsNull() {
        CommandException commandException = new CommandException(null);

        assertNull(commandException.getMessage());
    }
}
