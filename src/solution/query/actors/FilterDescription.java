package solution.query.actors;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.ParseData;

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
                                            ParseData data) {

        List<String> outputActors = new ArrayList<>();
        for (ActorInputData actor : data.getActors()) {
            boolean ok = true;
            String description = actor.getCareerDescription();
            List<String> filters = command.getFilters().get(2);
            if (filters != null) {
                for (String filter : filters) {
                    if (!description.contains(filter)) {
                        ok = false;
                    }
                }
            }

            if (ok) {
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

    public void filterDescription(final ActionInputData command, ParseData data,
                            final Writer fileWriter,
                            final JSONArray arrayResult) throws IOException {

        List<String> bestActors = createOutputActors(command, data);
        String outputMessage = "Query result: " + bestActors;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }



}
