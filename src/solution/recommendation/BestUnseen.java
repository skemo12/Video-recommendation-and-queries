package solution.recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;
import solution.query.videos.MostViewedQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BestUnseen {

    private static BestUnseen bestUnseen = null;
    public static BestUnseen getInstance() {
        if (bestUnseen == null) {
            bestUnseen = new BestUnseen();
        }
        return bestUnseen;
    }

    public String searchVideo(ActionInputData command, ParseData data) {
        List<Show> videos = new ArrayList<>();
        User user = Utility.getUtility().getUserByName(data.getUsers(), command.getUsername());
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

        Collections.sort(videos, (o1, o2) -> {
            if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                return 1;
            } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                return -1;
            } else return o1.getTitle().compareToIgnoreCase(o2.getTitle());

        });

        for (Show video : videos) {
            if (!user.getHistory().containsKey(video.getTitle())){
                return video.getTitle();
            }
        }
        return Standard.getInstance().searchVideo(command, data);
    }

    public void getRecommendation(final ActionInputData command, ParseData data,
                                  final Writer fileWriter,
                                  final JSONArray arrayResult) throws IOException {

        String video = searchVideo(command, data);
        String outputMessage = "BestRatedUnseenRecommendation result: " + video;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
