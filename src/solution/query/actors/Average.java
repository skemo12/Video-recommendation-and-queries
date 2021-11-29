package solution.query.actors;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.commands.Rating;
import solution.query.QueryInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                         final Database data) {

        List<String> bestActors = new ArrayList<>();
        int number = command.getNumber();
        class IntPairHelper {
            IntPairHelper(final double grade, final String name) {
                this.grade = grade;
                this.name = name;
            }

            private double grade;
            private String name;
        }
        List<IntPairHelper> actorsRatings = new ArrayList<>();
        for (ActorInputData actor : data.getActors()) {
            List<String> videoList = actor.getFilmography();
            double grade = 0.0;
            int count = 0;
            for (String title : videoList) {
                if (Rating.getRating().getRatingByTitle(title, data) != 0) {
                    grade += Rating.getRating().getRatingByTitle(title, data);
                    count++;
                }

            }
            if (grade != 0.0 && count != 0) {
                grade = grade / count;
                actorsRatings.add(new IntPairHelper(grade, actor.getName()));
            }
        }
        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(actorsRatings, (o1, o2) -> {
                if (Double.compare(o1.grade, o2.grade) < 0) {
                    return -1;
                } else {
                    if (Double.compare(o1.grade, o2.grade) > 0) {
                        return 1;
                    } else {
                        return o1.name.compareToIgnoreCase(o2.name);
                    }
                }

            });
        } else {
            Collections.sort(actorsRatings, (o1, o2) -> {
                if (Double.compare(o1.grade, o2.grade) < 0) {
                    return 1;
                } else if (Double.compare(o1.grade, o2.grade) > 0) {
                    return -1;
                } else {
                    return o2.name.compareToIgnoreCase(o1.name);
                }

            });
        }

        for (int i = 0; i < number; i++) {
            if (actorsRatings.size() <= i) {
                break;
            }
            bestActors.add(actorsRatings.get(i).name);
        }
        return bestActors;
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
