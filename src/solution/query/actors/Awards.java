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
 * Class for awards actor query, used to get awards actor query
 */
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
     * Sorts list of actors by awards according to type
     */
    private List<Actor> sortActorsByAwards(final List<Actor> actors,
                                          final String type) {

        if (type.equalsIgnoreCase("asc")) {
            Collections.sort(actors, (o1, o2) -> {
                if (o1.getTotalAwards() > o2.getTotalAwards()) {
                    return 1;
                } else if (o1.getTotalAwards() < o2.getTotalAwards()) {
                    return -1;
                } else {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }

            });
        } else {
            Collections.sort(actors, (o1, o2) -> {
                if (o1.getTotalAwards() > o2.getTotalAwards()) {
                    return -1;
                } else if (o1.getTotalAwards() < o2.getTotalAwards()) {
                    return 1;
                } else {
                    return o2.getName().compareToIgnoreCase(o1.getName());
                }

            });
        }
        return actors;
    }

    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {

        List<Actor> actorsAwards = new ArrayList<>();
        Filters filters = new Filters(command);
        Utility.getInstance().updateAwardsNumberActor(data);
        for (Actor actor : data.getActors()) {
            if (actor.getTotalAwards() > 0
                    && filters.checkActorFilters(actor)) {
                actorsAwards.add(actor);
            }
        }

        actorsAwards = sortActorsByAwards(actorsAwards, command.getSortType());
        List<String> outputActorsNames = new ArrayList<>();
        for (Actor actor : actorsAwards) {
            if (command.getNumber() <= outputActorsNames.size()) {
                break;
            }
            outputActorsNames.add(actor.getName());
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
