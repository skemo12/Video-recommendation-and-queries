package solution.recommendation.premium;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.recommendation.RecommendationString;
import solution.utility.Utility;
import solution.recommendation.standard.StandardRecommendation;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Class for popular recommendation, used to get popular recommendation
 */
public final class PopularRecommendation implements RecommendationString {

    /**
     * Make it Singleton
     */
    private static PopularRecommendation popular = null;
    /**
     * Singleton function
     */
    public static PopularRecommendation getInstance() {
        if (popular == null) {
            popular = new PopularRecommendation();
        }
        return popular;
    }

    /**
     * Searches and returns String video result
     */
    public String searchVideo(final ActionInputData command,
                              final Database data) {

        Utility.getInstance().updateViewNumber(data);
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
                command.getUsername());
        Map<String, Integer> popularity = new HashMap<>();

        for (Movie movie : data.getMovies()) {
            for (String genre : movie.getGenres()) {
                if (popularity.containsKey(genre)) {
                    popularity.put(genre, popularity.get(genre)
                            + movie.getNumberOfViews());
                } else {
                    popularity.put(genre, movie.getNumberOfViews());
                }
            }
        }

        while (popularity != null) {
            Map.Entry<String, Integer> max = null;
            for (Map.Entry<String, Integer> entry : popularity.entrySet()) {
                if (max == null || entry.getValue().compareTo(max.
                        getValue()) > 0) {
                    max = entry;
                }
            }

            if (max == null) {
                break;
            }

            for (Movie movie : data.getMovies()) {
                if (movie.getGenres().contains(max.getKey())
                        && !user.getHistory().containsKey(movie.getTitle())) {
                    return movie.getTitle();
                }
            }

            for (Serial serial : data.getSerials()) {
                if (serial.getGenres().contains(max.getKey())
                        && !user.getHistory().containsKey(serial.getTitle())) {
                    return serial.getTitle();
                }
            }

            popularity.remove(max.getKey());
        }

        return StandardRecommendation.getInstance().searchVideo(command, data);
    }

    /**
     * Creates output message and calls method for recommendation
     */
    public void getRecommendation(final ActionInputData action,
                                  final Database data) throws IOException {

        if (!CheckPremium.getInstance().checkPremium(action, data)) {
            String outputMessage = "PopularRecommendation cannot be applied!";
            Utility.getInstance().writeOutputMessage(data, action,
                    outputMessage);
            return;
        }

        String video = searchVideo(action, data);
        String outputMessage = "PopularRecommendation result: " + video;

        if (video == null) {
            outputMessage = "PopularRecommendation cannot be applied!";
        }

        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);

    }
}
