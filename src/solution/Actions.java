package solution.commands;

import fileio.*;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.List;

public class Actions {

    // make it Singleton
    private static Commands commands = null;

    public static Commands getCommands() {
        if (commands == null) {
            commands = new Commands();
        }
        return commands;
    }

    public void doCommands(Input input, Writer fileWriter,
                           JSONArray arrayResult) throws IOException {
        List<ActionInputData> commands = input.getCommands();
        for (ActionInputData command : commands) {
            if (command.getActionType().equalsIgnoreCase("command")) {
                if (command.getType().equalsIgnoreCase("favorite")) {
                    Favorite.getFavorite().addFavorite(command, input,
                            fileWriter, arrayResult);
                }

                if (command.getType().equalsIgnoreCase("view")) {
                    View.getView().watchTitle(command, input,
                            fileWriter, arrayResult);
                }

                if (command.getType().equalsIgnoreCase("rating")) {
                    Rating.getRating().rateVideo(command, input,
                            fileWriter, arrayResult);
                }
            }
        }
    }
}
