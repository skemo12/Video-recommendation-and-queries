package solution.query.actors;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.ParseData;

import java.io.IOException;
import java.util.*;

public class Awards {

    private static Awards awardsQuery = null;
    public static Awards getAwardsQuery() {
        if (awardsQuery == null) {
            awardsQuery = new Awards();
        }
        return awardsQuery;
    }

    private List<String> createOutputActors(ActionInputData command,
                                            ParseData data) {

        List<String> outputActors = new ArrayList<>();
        Map<String, Integer> totalAwards = new HashMap<>();
        for (ActorInputData actor : data.getActors()) {
            Map<ActorsAwards, Integer> awards = actor.getAwards();
            List<String> filters = command.getFilters().get(3);
            for (String filter : filters) {
                if (awards.containsKey(filter)) {
                    outputActors.add(actor.getName());
                    totalAwards.put(actor.getName(), actor.getAwards().get(filter) + totalAwards.get(actor.getName()));
                }
            }

        }
        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputActors, (o1, o2) -> {
                if (totalAwards.get(o1) > totalAwards.get(o2)) {
                    return 1;
                } else if (totalAwards.get(o2) > totalAwards.get(o1)) {
                    return -1;
                } else return o1.compareToIgnoreCase(o2);

            });
        } else {
            Collections.sort(outputActors, (o1, o2) -> {
                if (totalAwards.get(o1) > totalAwards.get(o2)) {
                    return -1;
                } else if (totalAwards.get(o2) > totalAwards.get(o1)) {
                    return 1;
                } else return o1.compareToIgnoreCase(o2);

            });
        }
        return outputActors;
    }

    public void awardsQuery(final ActionInputData command, ParseData data,
                             final Writer fileWriter,
                             final JSONArray arrayResult) throws IOException {

        List<String> bestActors = createOutputActors(command, data);
        String outputMessage = "Query result: " + bestActors;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }


}
