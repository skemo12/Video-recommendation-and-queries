package solution.query.actors;

import fileio.ActionInputData;
import solution.data.Actor;
import solution.data.Database;
import solution.query.Filters;
import solution.query.QueryInterface;
import solution.utility.Utility;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for description actor query, used to get description actor query
 */
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

        List<String> outputActorsNames = new ArrayList<>();
        Filters filters = new Filters(command);

        for (Actor actor : data.getActors()) {
            if (filters.checkActorFilters(actor)) {
                outputActorsNames.add(actor.getName());
            }

        }

        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputActorsNames);
        } else {
            Collections.sort(outputActorsNames, Collections.reverseOrder());
        }
        return outputActorsNames;
    }

    /**
     * Creates output message and calls method for query
     */
    public void getQuery(final ActionInputData action, final Database data)
            throws IOException {

        List<String> bestActors = createOutputList(action, data);
        String outputMessage = "Query result: " + bestActors;
        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);
    }

}
