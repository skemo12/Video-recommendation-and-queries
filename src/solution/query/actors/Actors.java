package solution.query.actors;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;

import java.io.IOException;

public final class Actors {

    /**
     * Make it singleton
     */
    private static Actors actorsQuery = null;
    /**
     * Singleton function
     */
    public static Actors getActorsQuery() {
        if (actorsQuery == null) {
            actorsQuery = new Actors();
        }
        return actorsQuery;
    }

    /**
     * Checks query type for actor and calls method
     */
    public void checkQuery(final ActionInputData command, final Database data,
                           final Writer fileWriter,
                           final JSONArray arrayResult) throws IOException {

        if (command.getCriteria().equalsIgnoreCase("average")) {
            Average.getInstance().getQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("awards")) {
            Awards.getInstance().getQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().
                equalsIgnoreCase("filter_description")) {
            FilterDescription.getInstance().getQuery(command, data,
                    fileWriter, arrayResult);
        }
    }
}
