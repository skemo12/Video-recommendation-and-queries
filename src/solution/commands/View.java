package solution.commands;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.User;
import solution.utility.Utility;

import java.io.IOException;
import java.util.Map;

/**
 * Class for view command
 */
public class View implements CommandInterface {

    /**
     * Make it Singleton
     */
    private static View view = null;
    /**
     * Singleton function
     */
    public static View getInstance() {
        if (view == null) {
            view = new View();
        }
        return view;
    }

    /**
     * Add title to watchlist of user from action
     * does not update the numberOfViews for movie or serials
     */
    public void doCommand(final ActionInputData action, final Database data)
            throws IOException {

        String username = action.getUsername();
        String title = action.getTitle();
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
                username);
        Map<String, Integer> history = user.getHistory();

        if (history.containsKey(title)) {
            history.put(title, history.get(title) + 1);
            String outputMessage = "success -> " + title
                    + " was viewed with total views of " + history.get(title);
            data.getArrayResult().add(data.getFileWriter()
                    .writeFile(action.getActionId(), "no field",
                            outputMessage));
        } else {
            history.put(title, 1);
            String outputMessage = "success -> " + title
                    + " was viewed with total views of " + history.get(title);
            data.getArrayResult().add(data.getFileWriter()
                    .writeFile(action.getActionId(), "no field",
                            outputMessage));
        }

    }
}
