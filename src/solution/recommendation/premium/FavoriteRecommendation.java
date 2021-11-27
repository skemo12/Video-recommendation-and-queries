package solution.recommendation.premium;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;
import solution.recommendation.Standard;

import java.io.IOException;
import java.util.*;

public class FavoriteRecommendation {

    private static FavoriteRecommendation favoriteRecommendation = null;
    public static FavoriteRecommendation getInstance() {
        if (favoriteRecommendation == null) {
            favoriteRecommendation = new FavoriteRecommendation();
        }
        return favoriteRecommendation;
    }

    public String searchVideo(ActionInputData command, ParseData data) {

        Utility.getUtility().updateFavorite(data);
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

        if (!CheckPremium.getInstance().checkPremium(command, data)) {
            String outputMessage = "FavoriteRecommendation cannot be applied!";
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
            return;
        }
        String video = searchVideo(command, data);
        String outputMessage = "FavoriteRecommendation result: " + video;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
