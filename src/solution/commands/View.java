package solution.commands;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.User;
import solution.utility.Utility;

import java.io.IOException;
import java.util.Map;

public class View {

    /**
     * Make it Singleton
     */
    private static View view = null;
    /**
     * Singleton function
     */
    public static View getView() {
        if (view == null) {
            view = new View();
        }
        return view;
    }
    /**
     * Add title to watchlist of user from command
     */
    public void watchTitle(final ActionInputData command, final Database data,
                           final Writer fileWriter, final JSONArray arrayResult)
            throws IOException {
        String username = command.getUsername();
        String title = command.getTitle();
        User user = Utility.getUtility().getUserByUsername(data.getUsers(),
                username);
        Map<String, Integer> history = user.getHistory();

        if (history.containsKey(title)) {
            history.put(title, history.get(title) + 1);
            String outputMessage = "success -> " + title
                    + " was viewed with total views of " + history.get(title);
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
        } else {
            history.put(title, 1);
            String outputMessage = "success -> " + title
                    + " was viewed with total views of " + history.get(title);
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
        }

    }
}
