package solution.commands;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.Database;
import solution.User;
import solution.Utility;

import java.io.IOException;
import java.util.Map;

public final class Favorite {

    // make it Singleton
    private static Favorite favorite = null;
    public static Favorite getFavorite() {
        if (favorite == null) {
            favorite = new Favorite();
        }
        return favorite;
    }

    public void addFavorite(final ActionInputData command, Database data,
                            final Writer fileWriter,
                            final JSONArray arrayResult)
            throws IOException {

        String username = command.getUsername();
        String title = command.getTitle();
        User user = Utility.getUtility().getUserByName(data.getUsers(),
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
