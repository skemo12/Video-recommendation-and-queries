package solution.query.videos;


import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;

import java.io.IOException;

public final class Videos {
    /**
     * Singleton class
     */
    private static Videos videos = null;
    /**
     * Singleton function
     */
    public static Videos getVideos() {
        if (videos == null) {
            videos = new Videos();
        }
        return videos;
    }
    /**
     * Checks query video type and calls method
     */
    public void checkQuery(final ActionInputData command, final Database data,
                           final Writer fileWriter,
                           final JSONArray arrayResult) throws IOException {

        if (command.getCriteria().equalsIgnoreCase("ratings")) {
            RatingQuery.getInstance().getQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("favorite")) {
            FavoriteQuery.getInstance().getQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("longest")) {
            LongestQuery.getInstance().getQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("most_viewed")) {
            MostViewedQuery.getInstance().getQuery(command, data,
                    fileWriter, arrayResult);
        }
    }
}

