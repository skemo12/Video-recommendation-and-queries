package solution.recommendation.premium;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search {

    private static Search search = null;
    public static Search getInstance() {
        if (search == null) {
            search = new Search();
        }
        return search;
    }

    public List<String> searchVideo(ActionInputData command, Database data) {

        List<Show> videos = new ArrayList<>();
        String genre = command.getGenre();
        User user = Utility.getUtility().getUserByName(data.getUsers(), command.getUsername());
        for (Movie movie : data.getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle()) && movie.getGenres().contains(genre)) {
                videos.add(movie);
            }
        }
        for (Serial serial : data.getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle()) && serial.getGenres().contains(genre)) {
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
        List<String> titles = new ArrayList<>();
        for (Show video : videos) {
            titles.add(video.getTitle());
        }
        return titles;
    }

    public void getRecommendation(final ActionInputData command, Database data,
                                  final Writer fileWriter,
                                  final JSONArray arrayResult) throws IOException {

        if (!CheckPremium.getInstance().checkPremium(command, data)) {
            String outputMessage = "SearchRecommendation cannot be applied!";
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
            return;
        }
        List<String> video = searchVideo(command, data);
        String outputMessage = "SearchRecommendation result: " + video;
        if(video.isEmpty()) {
            outputMessage = "SearchRecommendation cannot be applied!";
        }
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
