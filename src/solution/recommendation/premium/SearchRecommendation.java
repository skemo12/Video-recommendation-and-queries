package solution.recommendation.premium;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.Show;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.recommendation.RecommendationList;
import solution.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for search recommendation, used to get search recommendation
 */
public final class SearchRecommendation implements RecommendationList {

    /**
     * Make it Singleton
     */
    private static SearchRecommendation search = null;
    /**
     * Singleton function
     */
    public static SearchRecommendation getInstance() {
        if (search == null) {
            search = new SearchRecommendation();
        }
        return search;
    }

    /**
     * Sorts list of shows by rating, and if rating is equals sorts
     * according name
     */
    private List<Show> sortShowsByRating(final List<Show> videos) {

        Collections.sort(videos, (o1, o2) -> {
            if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                return 1;
            } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                return -1;
            } else {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }

        });
        return videos;
    }
    /**
     * Searches and returns String video result
     */
    public List<String> searchVideoList(final ActionInputData command,
                                        final Database data) {

        List<Show> videos = new ArrayList<>();
        String genre = command.getGenre();
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
                command.getUsername());

        for (Movie movie : data.getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())
                    && movie.getGenres().contains(genre)) {
                videos.add(movie);
            }
        }

        for (Serial serial : data.getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle())
                    && serial.getGenres().contains(genre)) {
                videos.add(serial);
            }
        }

        videos = sortShowsByRating(videos);
        List<String> titles = new ArrayList<>();
        for (Show video : videos) {
            titles.add(video.getTitle());
        }
        return titles;
    }

    /**
     * Creates output message and calls method for recommendation
     */
    public void getRecommendation(final ActionInputData action,
                                  final Database data) throws IOException {

        if (!CheckPremium.getInstance().checkPremium(action, data)) {
            String outputMessage = "SearchRecommendation cannot be applied!";
            Utility.getInstance().writeOutputMessage(data, action,
                    outputMessage);
            return;
        }

        List<String> video = searchVideoList(action, data);
        String outputMessage = "SearchRecommendation result: " + video;

        if (video.isEmpty()) {
            outputMessage = "SearchRecommendation cannot be applied!";
        }

        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);
    }
}
