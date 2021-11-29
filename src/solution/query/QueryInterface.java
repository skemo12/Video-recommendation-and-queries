package solution.query;

import fileio.ActionInputData;
import solution.data.Database;

import java.io.IOException;
import java.util.List;

/**
 * Interface for all queries in query package
 */
public interface QueryInterface {
    /**
     * Method to create output String list
     * @param1 is the command used in getQuery method
     * @param2 is the same database as in getQuery method
     */
    List<String> createOutputList(ActionInputData command,
                                   Database data);
    /**
     * Method to get query
     * @param1 is command from main for that specific command
     * @param2 is the custom database
     * @param3 is the output fileWriter
     * @param4 is the output file to be used by fileWriter
     */
    void getQuery(ActionInputData command, Database data) throws IOException;
}
