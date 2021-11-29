package solution.query.actors;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.utility.Utility;
import solution.query.Filters;
import solution.query.QueryInterface;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public final class Awards implements QueryInterface {

    /**
     * Make it singleton
     */
    private static Awards awardsQuery = null;
    /**
     * Singleton function
     */
    public static Awards getInstance() {
        if (awardsQuery == null) {
            awardsQuery = new Awards();
        }
        return awardsQuery;
    }

    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {

        List<String> outputActors = new ArrayList<>();
        Map<String, Integer> totalAwards = new HashMap<>();
        for (ActorInputData actor : data.getActors()) {
            Map<ActorsAwards, Integer> awards = actor.getAwards();
            Filters filters = new Filters(command);
            if (filters.checkActorFilters(actor)) {
                outputActors.add(actor.getName());

                if (totalAwards.containsKey(actor.getName())) {
                    totalAwards.put(actor.getName(),
                            totalAwards.get(actor.getName())
                                    + Utility.getUtility().
                                            getTotalAwardsNumber(actor));
                } else {
                    totalAwards.put(actor.getName(), Utility.getUtility().
                            getTotalAwardsNumber(actor));
                }
            }


        }
        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputActors, (o1, o2) -> {
                if (totalAwards.get(o1) > totalAwards.get(o2)) {
                    return 1;
                } else if (totalAwards.get(o2) > totalAwards.get(o1)) {
                    return -1;
                } else {
                    return o1.compareToIgnoreCase(o2);
                }

            });
        } else {
            Collections.sort(outputActors, (o1, o2) -> {
                if (totalAwards.get(o1) > totalAwards.get(o2)) {
                    return -1;
                } else if (totalAwards.get(o2) > totalAwards.get(o1)) {
                    return 1;
                } else {
                    return o2.compareToIgnoreCase(o1);
                }

            });
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
