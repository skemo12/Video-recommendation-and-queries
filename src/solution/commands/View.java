package solution.commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.ParseData;
import solution.User;
import solution.Utility;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class View {

    // Make it Singleton
    private static View view = null;
    public static View getView() {
        if (view == null) {
            view = new View();
        }
        return view;
    }

    public void watchTitle(ActionInputData command, ParseData data,
                           Writer fileWriter, JSONArray arrayResult)
            throws IOException {
        String username = command.getUsername();
        String title = command.getTitle();
        User user = Utility.getUtility().getUserByName(data.getUsers(),
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
