package solution.recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BestUnseen {

    private static BestUnseen bestUnseen = null;
    public static BestUnseen getInstance() {
        if (bestUnseen == null) {
            bestUnseen = new BestUnseen();
        }
        return bestUnseen;
    }

    public String searchVideo(ActionInputData command, Database data) {
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
                return -1;
            } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                return 1;
            } else return Utility.getUtility().getDatabaseOrder(o1, o2, data);

        });

        for (Show video : videos) {
            if (!user.getHistory().containsKey(video.getTitle())){
                return video.getTitle();
            }
        }
        return Standard.getInstance().searchVideo(command, data);
    }

    public void getRecommendation(final ActionInputData command, Database data,
                                  final Writer fileWriter,
                                  final JSONArray arrayResult) throws IOException {

        String video = searchVideo(command, data);
        String outputMessage = "BestRatedUnseenRecommendation result: " + video;
        if (video == null) {
            outputMessage = "BestRatedUnseenRecommendation cannot be applied!";
        }
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
