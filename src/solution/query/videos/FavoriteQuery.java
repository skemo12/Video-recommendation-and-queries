package solution.query.videos;

import fileio.ActionInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;
import solution.query.Filters;

import java.io.IOException;
import java.util.*;

public class FavoriteQuery {

    private static FavoriteQuery favoriteQuery = null;
    public static FavoriteQuery getInstance() {
        if (favoriteQuery == null) {
            favoriteQuery = new FavoriteQuery();
        }
        return favoriteQuery;
    }

    private List<String> createOutputTitlesMovies(ActionInputData command,
                                                  ParseData data) {
        List<String> outputVideos = new ArrayList<>();
        Filters filters = new Filters(command);
        Map<String, Integer> favoriteCount = new HashMap<>();

        for (User user : data.getUsers()) {
            for (String show : user.getFavoriteMovies()) {
                if (favoriteCount.containsKey(show)) {
                    favoriteCount.put(show, favoriteCount.get(show) + 1);
                }
                else {
                    favoriteCount.put(show, 1);
                }
            }
        }

        if (command.getObjectType().equalsIgnoreCase("movies")){
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie) && favoriteCount.containsKey(movie.getTitle())) {
                    outputVideos.add(movie.getTitle());
                }
            }
        }
        else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial) && favoriteCount.containsKey(serial.getTitle())) {
                    outputVideos.add(serial.getTitle());
                }
            }
        }

        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (favoriteCount.get(o1) > favoriteCount.get(o2)) {
                    return 1;
                } else if (favoriteCount.get(o1) < favoriteCount.get(o2)) {
                    return -1;
                } else return o1.compareToIgnoreCase(o2);

            });
        } else {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (favoriteCount.get(o1) > favoriteCount.get(o2)) {
                    return -1;
                } else if (favoriteCount.get(o1) < favoriteCount.get(o2)) {
                    return 1;
                } else return o1.compareToIgnoreCase(o2);

            });
        }

        return outputVideos;
    }

    public void favoriteQuery(final ActionInputData command, ParseData data,
                            final Writer fileWriter,
                            final JSONArray arrayResult) throws IOException {

        List<String> bestVideos = createOutputTitlesMovies(command, data);
        String outputMessage = "Query result: " + bestVideos;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }

}
