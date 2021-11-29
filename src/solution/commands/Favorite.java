package solution.commands;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.User;
import solution.utility.Utility;

import java.io.IOException;
import java.util.Map;

public final class Favorite {

    /**
     * Make it Singleton
     */
    private static Favorite favorite = null;
    /**
     * Singleton function
     */
    public static Favorite getInstance() {
        if (favorite == null) {
            favorite = new Favorite();
        }
        return favorite;
    }
    /**
     * Add movie to favorite and creates output message
     */
    public void addFavorite(final ActionInputData command, final Database data,
                            final Writer fileWriter,
                            final JSONArray arrayResult)
            throws IOException {

        String username = command.getUsername();
        String title = command.getTitle();
        User user = Utility.getUtility().getUserByUsername(data.getUsers(),
                username);
        Map<String, Integer> history = user.getHistory();

        if (history.containsKey(title)) {
            if (!user.getFavoriteMovies().contains(title)) {
                user.getFavoriteMovies().add(title);
                String outputMessage = "success -> " + title
                            + " was added as favourite";
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));
                } else {
                    String outputMessage = "error -> " + title
                            + " is already in favourite list";
                    arrayResult.add(fileWriter.writeFile(command.getActionId(),
                            "no field", outputMessage));

                }
            } else {
                String outputMessage = "error -> " + title
                        + " is not seen";
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));
            }


    }
}
