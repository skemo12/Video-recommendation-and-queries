package solution.query.actors;

import fileio.*;
import org.json.simple.JSONArray;
import solution.ParseData;

import java.io.IOException;

public class ActorsQuery {

    /**
     * Singleton class
     */
    private static ActorsQuery actorsQuery = null;
    public static ActorsQuery getActorsQuery() {
        if (actorsQuery == null) {
            actorsQuery = new ActorsQuery();
        }
        return actorsQuery;
    }


    public void checkQuery(final ActionInputData command, ParseData data,
                           final Writer fileWriter,
                           final JSONArray arrayResult) throws IOException {

        if (command.getCriteria().equalsIgnoreCase("average")) {
            AverageQuery.getAverageQuery().averageQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("awards")) {
            AwardsQuery.getAwardsQuery().awardsQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("filter_description")) {
            FilterDescription.getFilterDescription().filterDescription(command, data,
                    fileWriter, arrayResult);
        }
    }
}
