package solution;


import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import solution.commands.Favorite;
import solution.commands.Rating;
import solution.commands.View;
import solution.query.actors.Actors;
import solution.query.users.NumberOfRatingsQuery;
import solution.query.videos.Videos;
import solution.recommendation.BestUnseen;
import solution.recommendation.Standard;
import solution.recommendation.premium.FavoriteRecommendation;
import solution.recommendation.premium.Popular;
import solution.recommendation.premium.Search;

import java.io.IOException;

public final class Actions {

    /**
     * Make it Singleton
     */
    private static Actions actions = null;
    /**
     * Singleton function
     */
    public static Actions getActions() {
        if (actions == null) {
            actions = new Actions();
        }
        return actions;
    }

    /**
     * Does all actions parsing
     */
    public void doActions(final ActionInputData action, final Database data,
                          final Writer fileWriter, final JSONArray arrayResult)
            throws IOException {

        if (action.getActionType().equalsIgnoreCase("command")) {
            if (action.getType().equalsIgnoreCase("favorite")) {
                Favorite.getFavorite().addFavorite(action, data, fileWriter, arrayResult);
                }

                if (action.getType().equalsIgnoreCase("view")) {
                    View.getView().watchTitle(action, data,
                            fileWriter, arrayResult);
                }

                if (action.getType().equalsIgnoreCase("rating")) {
                    Rating.getRating().rateVideo(action, data,
                            fileWriter, arrayResult);
                }
            }
            if (action.getActionType().equalsIgnoreCase("query")) {
                if (action.getObjectType().equalsIgnoreCase("actors")) {
                    Actors.getActorsQuery().checkQuery(action, data,
                            fileWriter, arrayResult);
                }
            }

            if (action.getActionType().equalsIgnoreCase("query")) {

                if (action.getObjectType().equalsIgnoreCase("movies")) {
                    Videos.getVideos().checkQuery(action, data,
                            fileWriter, arrayResult);
                }

                if (action.getObjectType().equalsIgnoreCase("shows")) {
                    Videos.getVideos().checkQuery(action, data,
                            fileWriter, arrayResult);
                }

                if (action.getObjectType().equalsIgnoreCase("users")) {
                    NumberOfRatingsQuery.getInstance().numberOfRatingsQuery(action, data,
                            fileWriter, arrayResult);
                }

            }

            if (action.getActionType().equalsIgnoreCase("recommendation")) {
                if (action.getType().equalsIgnoreCase("standard")) {
                    Standard.getInstance().getRecomandation(action, data,
                            fileWriter, arrayResult);
                }

                if (action.getType().equalsIgnoreCase("best_unseen")) {
                    BestUnseen.getInstance().getRecommendation(action, data,
                            fileWriter, arrayResult);
                }

                if (action.getType().equalsIgnoreCase("popular")) {
                    Popular.getInstance().getRecommendation(action, data,
                            fileWriter, arrayResult);
                }
                if (action.getType().equalsIgnoreCase("favorite")) {
                    FavoriteRecommendation.getInstance().getRecommendation(action, data,
                            fileWriter, arrayResult);
                }
                if (action.getType().equalsIgnoreCase("search")) {
                    Search.getInstance().getRecommendation(action, data,
                            fileWriter, arrayResult);
                }

            }



        }
    }
