package solution.query.videos;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.User;
import solution.data.Movie;
import solution.data.Serial;
import solution.query.Filters;
import solution.query.QueryInterface;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public final class FavoriteQuery implements QueryInterface {

    /**
     * Make it Singleton
     */
    private static FavoriteQuery favoriteQuery = null;
    /**
     * Singleton function
     */
    public static FavoriteQuery getInstance() {
        if (favoriteQuery == null) {
            favoriteQuery = new FavoriteQuery();
        }
        return favoriteQuery;
    }
    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {
        List<String> outputVideos = new ArrayList<>();
        Filters filters = new Filters(command);
        Map<String, Integer> favoriteCount = new HashMap<>();

        for (User user : data.getUsers()) {
            for (String show : user.getFavoriteMovies()) {
                if (favoriteCount.containsKey(show)) {
                    favoriteCount.put(show, favoriteCount.get(show) + 1);
                } else {
                    favoriteCount.put(show, 1);
                }
            }
        }

        if (command.getObjectType().equalsIgnoreCase("movies")) {
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie) && favoriteCount.
                        containsKey(movie.getTitle())) {
                    outputVideos.add(movie.getTitle());
                }
            }
        } else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial) && favoriteCount.
                        containsKey(serial.getTitle())) {
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
                } else {
                    return o1.compareToIgnoreCase(o2);
                }

            });
        } else {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (favoriteCount.get(o1) > favoriteCount.get(o2)) {
                    return -1;
                } else if (favoriteCount.get(o1) < favoriteCount.get(o2)) {
                    return 1;
                } else {
                    return o2.compareToIgnoreCase(o1);
                }

            });
        }

        return outputVideos;
    }
    /**
     * Creates output message and calls method for query
     */
    public void getQuery(final ActionInputData command, final Database data,
                         final Writer fileWriter,
                         final JSONArray arrayResult) throws IOException {

        List<String> bestVideos = createOutputList(command, data);
        String outputMessage = "Query result: " + bestVideos;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }

}
