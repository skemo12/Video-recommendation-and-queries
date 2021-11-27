package solution.query.videos;


import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.Movie;
import solution.ParseData;
import solution.Show;
import solution.query.actors.Average;
import solution.query.actors.Awards;
import solution.query.actors.FilterDescription;

import java.io.IOException;
import java.util.List;

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

    public void checkQuery(final ActionInputData command, ParseData data,
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

