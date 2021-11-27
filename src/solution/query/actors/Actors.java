package solution.query.actors;

import fileio.*;
import org.json.simple.JSONArray;
import solution.ParseData;

import java.io.IOException;

public class Actors {

    /**
     * Singleton class
     */
    private static Actors actorsQuery = null;
    public static Actors getActorsQuery() {
        if (actorsQuery == null) {
            actorsQuery = new Actors();
        }
        return actorsQuery;
    }


    public void checkQuery(final ActionInputData command, ParseData data,
                           final Writer fileWriter,
                           final JSONArray arrayResult) throws IOException {

        if (command.getCriteria().equalsIgnoreCase("average")) {
            Average.getAverageQuery().averageQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("awards")) {
            Awards.getAwardsQuery().awardsQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("filter_description")) {
            FilterDescription.getFilterDescription().filterDescription(command, data,
                    fileWriter, arrayResult);
        }
    }
}
