package solution.query.videos;


import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.Database;

import java.io.IOException;

public class Videos {
    /**
     * Singleton class
     */
    private static Videos videos = null;
    public static Videos getVideos() {
        if (videos == null) {
            videos = new Videos();
        }
        return videos;
    }

    public void checkQuery(final ActionInputData command, Database data,
                           final Writer fileWriter,
                           final JSONArray arrayResult) throws IOException {

        if (command.getCriteria().equalsIgnoreCase("ratings")) {
            RatingQuery.getInstance().ratingQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("favorite")) {
            FavoriteQuery.getInstance().favoriteQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("longest")) {
            LongestQuery.getInstance().ratingQuery(command, data,
                    fileWriter, arrayResult);
        }

        if (command.getCriteria().equalsIgnoreCase("most_viewed")) {
            MostViewedQuery.getInstance().mostViewedQuery(command, data,
                    fileWriter, arrayResult);
        }
    }
}

