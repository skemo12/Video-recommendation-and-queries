package solution.recommendation.standard;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.Show;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.recommendation.RecommendationString;
import solution.utility.Utility;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for best unseen recommendation, used to get best unseen recommendation
 */
public final class BestUnseenRecommendation implements RecommendationString {

    /**
     * Make it Singleton
     */
    private static BestUnseenRecommendation bestUnseen = null;
    /**
     * Singleton function
     */
    public static BestUnseenRecommendation getInstance() {
        if (bestUnseen == null) {
            bestUnseen = new BestUnseenRecommendation();
        }
        return bestUnseen;
    }

    /**
     * Sorts list of shows by rating, and if rating is equals sorts according
     * to order in database
     */
    private List<Show> sortShowsByRating(final List<Show> videos,
                                         final Database data) {

        Collections.sort(videos, (o1, o2) -> {
            if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                        return -1;
            } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                return 1;
            } else {
                return Utility.getInstance().getDatabaseOrder(o1, o2, data);
            }
        });
        return videos;
    }


    /**
     * Searches and returns String video result
     */
    public String searchVideo(final ActionInputData command,
                              final Database data) {

        List<Show> videos = new ArrayList<>();
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
                command.getUsername());

        for (Movie movie : data.getMovies()) {
            if (movie.getRating() != 0.0) {
                videos.add(movie);
            }
        }

        for (Serial serial : data.getSerials()) {
            if (serial.getRating() != 0.0) {
                videos.add(serial);
            }
        }

        videos = sortShowsByRating(videos, data);
        for (Show video : videos) {
            if (!user.getHistory().containsKey(video.getTitle())) {
                return video.getTitle();
            }
        }

        return StandardRecommendation.getInstance().searchVideo(command, data);
    }

    /**
     * Creates output message and calls method for recommendation
     */
    public void getRecommendation(final ActionInputData action,
                                  final Database data) throws IOException {

        String video = searchVideo(action, data);
        String outputMessage = "BestRatedUnseenRecommendation result: " + video;

        if (video == null) {
            outputMessage = "BestRatedUnseenRecommendation cannot be applied!";
        }

        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);

    }

}
