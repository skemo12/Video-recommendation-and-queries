package solution.query.actors;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.query.Filters;
import solution.query.QueryInterface;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public final class FilterDescription implements QueryInterface {

    /**
     * Make it singleton
     */
    private static FilterDescription filterDescription = null;
    /**
     * Singleton function
     */
    public static FilterDescription getInstance() {
        if (filterDescription == null) {
            filterDescription = new FilterDescription();
        }
        return filterDescription;
    }
    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {

        List<String> outputActors = new ArrayList<>();
        Filters filters = new Filters(command);
        for (ActorInputData actor : data.getActors()) {
            if (filters.checkActorFilters(actor)) {
                outputActors.add(actor.getName());
            }

        }
        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputActors);
        } else {
            Collections.sort(outputActors, Collections.reverseOrder());

        }
        return outputActors;
    }
    /**
     * Creates output message and calls method for query
     */
    public void getQuery(final ActionInputData command, final Database data,
                         final Writer fileWriter,
                         final JSONArray arrayResult) throws IOException {

        List<String> bestActors = createOutputList(command, data);
        String outputMessage = "Query result: " + bestActors;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }



}
