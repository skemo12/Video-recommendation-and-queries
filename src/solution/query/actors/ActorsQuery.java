package solution.query.actors;

import fileio.ActionInputData;
import solution.data.Database;

import java.io.IOException;

/**
 * Class for all actors query, used to get actors query result
 */
public final class ActorsQuery {
    /**
     * Make it singleton
     */
    private static ActorsQuery actorsQuery = null;
    /**
     * Singleton function
     */
    public static ActorsQuery getInstance() {
        if (actorsQuery == null) {
            actorsQuery = new ActorsQuery();
        }
        return actorsQuery;
    }

    /**
     * Checks query type for actor and calls method
     */
    public void checkQuery(final ActionInputData command, final Database data)
            throws IOException {

        if (command.getCriteria().equalsIgnoreCase("average")) {
            Average.getInstance().getQuery(command, data);
        }

        if (command.getCriteria().equalsIgnoreCase("awards")) {
            Awards.getInstance().getQuery(command, data);
        }

        if (command.getCriteria().
                equalsIgnoreCase("filter_description")) {
            FilterDescription.getInstance().getQuery(command, data);
        }
    }
}
