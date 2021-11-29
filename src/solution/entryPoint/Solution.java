package solution.entryPoint;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.actions.Actions;
import solution.data.Database;

import java.io.IOException;
import java.util.List;

/**
 * Class for main entry point for provided solution
 * Instance called in main
 */
public final class Solution {

    /**
     * Make it singleton
     */
    private static Solution solution = null;
    /**
     * Singleton function
     */
    public static Solution getInstance() {
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

        Database data = new Database(input, fileWriter, arrayResult);
        List<ActionInputData> actions = data.getActions();
        for (ActionInputData action : actions) {
            Actions.getInstance().doActions(action, data);
        }
    }
}
