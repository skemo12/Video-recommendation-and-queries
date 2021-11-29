package solution.query.videos;


import fileio.ActionInputData;
import solution.data.Database;

import java.io.IOException;

/**
 * Class for all videos query, used to get videos query result
 */
public final class VideosQuery {

    /**
     * Singleton class
     */
    private static VideosQuery videos = null;
    /**
     * Singleton function
     */
    public static VideosQuery getInstance() {
        if (videos == null) {
            videos = new VideosQuery();
        }
        return videos;
    }

    /**
     * Checks query video type and calls method
     */
    public void checkQuery(final ActionInputData action, final Database data)
            throws IOException {

        if (action.getCriteria().equalsIgnoreCase("ratings")) {
            RatingQuery.getInstance().getQuery(action, data);
        }

        if (action.getCriteria().equalsIgnoreCase("favorite")) {
            FavoriteQuery.getInstance().getQuery(action, data);
        }

        if (action.getCriteria().equalsIgnoreCase("longest")) {
            LongestQuery.getInstance().getQuery(action, data);
        }

        if (action.getCriteria().equalsIgnoreCase("most_viewed")) {
            MostViewedQuery.getInstance().getQuery(action, data);
        }
    }
}

