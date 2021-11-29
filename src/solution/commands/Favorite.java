package solution.commands;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.User;
import solution.utility.Utility;

import java.io.IOException;
import java.util.Map;

/**
 * Class for favorite command
 */
public final class Favorite implements CommandInterface {

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
     * Adds movie to favorite and creates output message
     * does not update favoriteAddCount for movie or serial
     */
    public void doCommand(final ActionInputData action, final Database data)
            throws IOException {

        String username = action.getUsername();
        String title = action.getTitle();
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
                username);
        Map<String, Integer> history = user.getHistory();

        if (history.containsKey(title)) {
            if (!user.getFavoriteMovies().contains(title)) {
                user.getFavoriteMovies().add(title);
                String outputMessage = "success -> " + title
                            + " was added as favourite";
                Utility.getInstance().writeOutputMessage(data, action,
                        outputMessage);
                } else {
                    String outputMessage = "error -> " + title
                            + " is already in favourite list";
                Utility.getInstance().writeOutputMessage(data, action,
                        outputMessage);

                }
            } else {
                String outputMessage = "error -> " + title
                        + " is not seen";
            Utility.getInstance().writeOutputMessage(data, action,
                    outputMessage);
            }


    }
}
