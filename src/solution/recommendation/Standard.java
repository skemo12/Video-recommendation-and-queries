package solution.recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.utility.Utility;

import java.io.IOException;

public final class Standard implements RecommendationString {

    /**
     * Make it Singleton
     */
    private static Standard standard = null;
    /**
     * Singleton function
     */
    public static Standard getInstance() {
        if (standard == null) {
            standard = new Standard();
        }
        return standard;
    }

    /**
     * Searches and returns String video result
     */
    public String searchVideo(final ActionInputData command,
                               final Database data) {
        String username = command.getUsername();
        User user = Utility.getUtility().getUserByUsername(data.getUsers(),
                username);
        for (Movie movie : data.getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                return movie.getTitle();
            }
        }

        for (Serial serial : data.getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                return serial.getTitle();
            }
        }
        return null;
    }

    /**
     * Creates output message and calls method for recommendation
     */
    public void getRecommendation(final ActionInputData command,
                                  final Database data, final Writer fileWriter,
                                  final JSONArray arrayResult)
            throws IOException {

        String video = searchVideo(command, data);

        if (video == null) {
            String commandType = command.getType();
            commandType = commandType.substring(0, 1).toUpperCase()
                    + commandType.substring(1).toLowerCase();
            String outputMessage =  commandType
                    + "Recommendation cannot be applied!";
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
            return;
        }
        String outputMessage = "StandardRecommendation result: " + video;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
