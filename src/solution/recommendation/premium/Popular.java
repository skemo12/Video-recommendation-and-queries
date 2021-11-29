package solution.recommendation.premium;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.recommendation.RecommendationString;
import solution.utility.Utility;
import solution.recommendation.Standard;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;


public final class Popular implements RecommendationString {

    /**
     * Make it Singleton
     */
    private static Popular popular = null;
    /**
     * Singleton function
     */
    public static Popular getInstance() {
        if (popular == null) {
            popular = new Popular();
        }
        return popular;
    }
    /**
     * Searches and returns String video result
     */
    public String searchVideo(final ActionInputData command,
                              final Database data) {

        for (User user : data.getUsers()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().
                    entrySet()) {
                String title = entry.getKey();
                Integer views = entry.getValue();
                Movie movie = Utility.getUtility().getMovieByTitle(data.
                        getMovies(), title);
                if (movie != null) {
                    movie.setNumberOfViews(movie.getNumberOfViews() + views);
                }

                Serial serial = Utility.getUtility().getSerialByTitle(data.
                        getSerials(), title);
                if (serial != null) {
                    serial.setNumberOfViews(serial.getNumberOfViews() + views);
                }

            }
        }
        User user = Utility.getUtility().getUserByUsername(data.getUsers(),
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
        return Standard.getInstance().searchVideo(command, data);
    }
    /**
     * Creates output message and calls method for recommendation
     */
    public void getRecommendation(final ActionInputData command,
                                  final Database data, final Writer fileWriter,
                                  final JSONArray arrayResult)
            throws IOException {
        if (!CheckPremium.getInstance().checkPremium(command, data)) {
            String outputMessage = "PopularRecommendation cannot be applied!";
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
            return;
        }
        String video = searchVideo(command, data);
        String outputMessage = "PopularRecommendation result: " + video;
        if (video == null) {
            outputMessage = "PopularRecommendation cannot be applied!";
        }
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
