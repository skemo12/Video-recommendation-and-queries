package solution;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.commands.Rating;

import java.io.IOException;
import java.util.List;

public class Solution {

    private static Solution solution = null;

    /**
     * Singleton function
     */
    public static Solution getSolution() {
        if (solution == null) {
            solution = new Solution();
        }
        return solution;
    }
    public void solve (final Input input,
                       final Writer fileWriter, final JSONArray arrayResult) throws IOException {

        ParseData data = new ParseData(input);
        List<ActionInputData> actions = data.getActions();
        for (ActionInputData action : actions) {
            Actions.getActions().doActions(action, data, fileWriter, arrayResult);
        }
    }
}
