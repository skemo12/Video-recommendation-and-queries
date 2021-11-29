package solution.entryPoint;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Actions;
import solution.data.Database;

import java.io.IOException;
import java.util.List;

public final class Solution {

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
    /**
     * Main entry point for Solution
     */
    public void solve(final Input input,
                       final Writer fileWriter, final JSONArray arrayResult)
            throws IOException {

        Database data = new Database(input);
        List<ActionInputData> actions = data.getActions();
        for (ActionInputData action : actions) {
            Actions.getActions().doActions(action, data, fileWriter,
                    arrayResult);
        }
    }
}
