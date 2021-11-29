package solution.recommendation.standard;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.recommendation.RecommendationString;
import solution.utility.Utility;

import java.io.IOException;

/**
 * Class for standard recommendation, used to get standard recommendation
 */
public final class StandardRecommendation implements RecommendationString {

    /**
     * Make it Singleton
     */
    private static StandardRecommendation standard = null;
    /**
     * Singleton function
     */
    public static StandardRecommendation getInstance() {
        if (standard == null) {
            standard = new StandardRecommendation();
        }
        return standard;
    }

    /**
     * Searches and returns String video result
     */
    public String searchVideo(final ActionInputData command,
                               final Database data) {

        String username = command.getUsername();
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
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
    public void getRecommendation(final ActionInputData action,
                                  final Database data) throws IOException {

        String video = searchVideo(action, data);

        if (video == null) {
            String commandType = action.getType();
            commandType = commandType.substring(0, 1).toUpperCase()
                    + commandType.substring(1).toLowerCase();

            String outputMessage =  commandType
                    + "Recommendation cannot be applied!";
            Utility.getInstance().writeOutputMessage(data, action,
                    outputMessage);
            return;
        }

        String outputMessage = "StandardRecommendation result: " + video;
        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);

    }
}
