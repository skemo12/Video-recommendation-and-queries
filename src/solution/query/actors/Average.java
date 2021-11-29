package solution.query.actors;

import fileio.ActionInputData;
import solution.data.Actor;
import solution.data.Database;
import solution.query.QueryInterface;
import solution.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for average actor query, used to get average actor query
 */
public final class Average implements QueryInterface {

    /**
     * Make it singleton
     */
    private static Average averageQuery = null;
    /**
     * Singleton function
     */
    public static Average getInstance() {
        if (averageQuery == null) {
            averageQuery = new Average();
        }
        return averageQuery;
    }

    /**
     * Sorts list of actors by average according to type
     */
    private List<Actor> sortActorListByAverage(final List<Actor> actors,
                                              final String type) {

        if (type.equalsIgnoreCase("asc")) {
            Collections.sort(actors, (o1, o2) -> {
                if (Double.compare(o1.getGrade(), o2.getGrade()) < 0) {
                    return -1;
                } else {
                    if (Double.compare(o1.getGrade(), o2.getGrade()) > 0) {
                        return 1;
                    } else {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                }

            });
        } else {
            Collections.sort(actors, (o1, o2) -> {
                if (Double.compare(o1.getGrade(), o2.getGrade()) < 0) {
                    return 1;
                } else if (Double.compare(o1.getGrade(), o2.getGrade()) > 0) {
                    return -1;
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

        List<Actor> bestActors = new ArrayList<>();
        Utility.getInstance().updateGradeActor(data);
        for (Actor actor : data.getActors()) {
            if (actor.getGrade() != 0.0) {
                bestActors.add(actor);
            }
        }

        bestActors = sortActorListByAverage(bestActors, command.getSortType());
        List<String> outputActors = new ArrayList<>();
        for (Actor actor : bestActors) {
            if (command.getNumber() <= outputActors.size()) {
                break;
            }
            outputActors.add(actor.getName());
        }
        return outputActors;
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
