package solution.recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.Show;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.utility.Utility;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BestUnseen implements RecommendationString {

    /**
     * Make it Singleton
     */
    private static BestUnseen bestUnseen = null;
    /**
     * Singleton function
     */
    public static BestUnseen getInstance() {
        if (bestUnseen == null) {
            bestUnseen = new BestUnseen();
        }
        return bestUnseen;
    }

    /**
     * Searches and returns String video result
     */
    public String searchVideo(final ActionInputData command,
                              final Database data) {
        List<Show> videos = new ArrayList<>();
        User user = Utility.getUtility().getUserByUsername(data.getUsers(),
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

        videos.sort((o1, o2) -> {
            if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                return -1;
            } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                return 1;
            } else {
                return Utility.getUtility().getDatabaseOrder(o1, o2, data);
            }

        });

        for (Show video : videos) {
            if (!user.getHistory().containsKey(video.getTitle())) {
                return video.getTitle();
            }
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

        String video = searchVideo(command, data);
        String outputMessage = "BestRatedUnseenRecommendation result: " + video;
        if (video == null) {
            outputMessage = "BestRatedUnseenRecommendation cannot be applied!";
        }
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
