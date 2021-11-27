package solution;


import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import solution.commands.Favorite;
import solution.commands.Rating;
import solution.commands.View;
import solution.query.actors.ActorsQuery;

import java.io.IOException;

public final class Actions {

    // make it Singleton
    private static Actions actions = null;

    public static Actions getActions() {
        if (actions == null) {
            actions = new Actions();
        }
        return actions;
    }

    public void doActions(ActionInputData action, ParseData data, final Writer fileWriter,
                           final JSONArray arrayResult) throws IOException {

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
                    ActorsQuery.getActorsQuery().checkQuery(action, data,
                            fileWriter, arrayResult);
                }
            }


    }
}
