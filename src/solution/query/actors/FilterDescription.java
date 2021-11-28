package solution.query.actors;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.Database;
import solution.query.Filters;

import java.io.IOException;
import java.util.*;

public class FilterDescription {

    private static FilterDescription filterDescription = null;
    public static FilterDescription getFilterDescription() {
        if (filterDescription == null) {
            filterDescription = new FilterDescription();
        }
        return filterDescription;
    }

    private List<String> createOutputActors(ActionInputData command,
                                            Database data) {

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

    public void filterDescription(final ActionInputData command, Database data,
                            final Writer fileWriter,
                            final JSONArray arrayResult) throws IOException {

        List<String> bestActors = createOutputActors(command, data);
        String outputMessage = "Query result: " + bestActors;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }



}
