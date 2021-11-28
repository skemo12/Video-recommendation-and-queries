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

    public String searchVideo(ActionInputData command, Database data) {

        Utility.getUtility().updateFavorite(data);
        List<Show> videos = new ArrayList<>();
        User user = Utility.getUtility().getUserByName(data.getUsers(), command.getUsername());
        for (Movie movie : data.getMovies()) {
            if (movie.getFavoriteAddCount() != 0.0) {
                videos.add(movie);
            }
        }
        for (Serial serial : data.getSerials()) {
            if (serial.getFavoriteAddCount() != 0.0) {
                videos.add(serial);
            }
        }

        Collections.sort(videos, (o1, o2) -> {
            if (o1.getFavoriteAddCount() > o2.getFavoriteAddCount()) {
                return -1;
            } else if (o1.getFavoriteAddCount() < o2.getFavoriteAddCount()) {
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

        if (!CheckPremium.getInstance().checkPremium(command, data)) {
            String outputMessage = "FavoriteRecommendation cannot be applied!";
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
            return;
        }
        String video = searchVideo(command, data);
        if (video == null) {
            String commandType = command.getType();
            commandType = commandType.substring(0,1).toUpperCase() + commandType.substring(1).toLowerCase();
            String outputMessage =  commandType + "Recommendation cannot be applied!";
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
            return;
        }
        String outputMessage = "FavoriteRecommendation result: " + video;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
