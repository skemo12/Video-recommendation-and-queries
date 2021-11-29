package solution.commands;

import fileio.ActionInputData;
import solution.data.Database;

import java.io.IOException;

/**
 * Interface for all commands in commands package
 */
public interface CommandInterface {
    /**
     * Does command according to class name
     * @param1 is command from main for that specific command
     * @param2 is the custom database
     * @param3 is the output fileWriter
     * @param4 is the output file to be used by fileWriter
     */
    void doCommand(ActionInputData command, Database data) throws IOException;
}
