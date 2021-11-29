package solution.query;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;

import java.io.IOException;
import java.util.List;

public interface QueryInterface {
    /**
     * Method to create output String list
     */
    List<String> createOutputList(ActionInputData command,
                                   Database data);
    /**
     * Method to get query
     */
    void getQuery(ActionInputData command,  Database data, Writer fileWriter,
                          JSONArray arrayResult) throws IOException;
}
