package solution.actions;


import fileio.ActionInputData;

import solution.commands.Favorite;
import solution.commands.Rating;
import solution.commands.View;
import solution.data.Database;
import solution.query.actors.ActorsQuery;
import solution.query.users.NumberOfRatingsQuery;
import solution.query.videos.VideosQuery;
import solution.recommendation.standard.BestUnseenRecommendation;
import solution.recommendation.standard.StandardRecommendation;
import solution.recommendation.premium.FavoriteRecommendation;
import solution.recommendation.premium.PopularRecommendation;
import solution.recommendation.premium.SearchRecommendation;

import java.io.IOException;

/**
 * Class for doing all actions, called by solution to check action type
 */
public final class Actions {

    /**
     * Make it Singleton
     */
    private static Actions actions = null;

    /**
     * Singleton function
     */
    public static Actions getInstance() {
        if (actions == null) {
            actions = new Actions();
        }
        return actions;
    }

    /**
     * Verifies action type and call corresponding method
     */
    public void doActions(final ActionInputData action, final Database data)
            throws IOException {

        if (action.getActionType().equalsIgnoreCase("command")) {

            if (action.getType().equalsIgnoreCase("favorite")) {
                Favorite.getInstance().doCommand(action, data);
            }

            if (action.getType().equalsIgnoreCase("view")) {
                View.getInstance().doCommand(action, data);
            }

            if (action.getType().equalsIgnoreCase("rating")) {
                Rating.getInstance().doCommand(action, data);
            }
        }

        if (action.getActionType().equalsIgnoreCase("query")) {

            if (action.getObjectType().equalsIgnoreCase("actors")) {
                ActorsQuery.getInstance().checkQuery(action, data);
            }
            if (action.getObjectType().equalsIgnoreCase("movies")) {
                VideosQuery.getInstance().checkQuery(action, data);
            }
            if (action.getObjectType().equalsIgnoreCase("shows")) {
                VideosQuery.getInstance().checkQuery(action, data);
            }
            if (action.getObjectType().equalsIgnoreCase("users")) {
                NumberOfRatingsQuery.getInstance().getQuery(action, data);
            }

        }

        if (action.getActionType().
                equalsIgnoreCase("recommendation")) {
            if (action.getType().equalsIgnoreCase("standard")) {
                StandardRecommendation.getInstance().
                        getRecommendation(action, data);
            }

            if (action.getType().
                    equalsIgnoreCase("best_unseen")) {
                BestUnseenRecommendation.getInstance().
                        getRecommendation(action, data);
            }

            if (action.getType().equalsIgnoreCase("popular")) {
                    PopularRecommendation.getInstance().
                            getRecommendation(action, data);
            }

            if (action.getType().equalsIgnoreCase("favorite")) {
                FavoriteRecommendation.getInstance().
                        getRecommendation(action, data);
            }

            if (action.getType().equalsIgnoreCase("search")) {
                SearchRecommendation.getInstance().
                        getRecommendation(action, data);
            }

        }
    }
}
