package solution.query.videos;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.Movie;
import solution.data.Serial;
import solution.query.Filters;
import solution.data.User;
import solution.query.QueryInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class MostViewedQuery implements QueryInterface {

    /**
     * Make it Singleton
     */
    private static MostViewedQuery mostViewedQuery = null;
    /**
     * Singleton function
     */
    public static MostViewedQuery getInstance() {
        if (mostViewedQuery == null) {
            mostViewedQuery = new MostViewedQuery();
        }
        return mostViewedQuery;
    }
    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {

        List<String> outputVideos = new ArrayList<>();
        Filters filters = new Filters(command);
        Map<String, Integer> totalViews = new HashMap<>();

        for (User user : data.getUsers()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                String title = entry.getKey();
                Integer views = entry.getValue();
                if (totalViews.containsKey(title)) {
                    totalViews.put(title, totalViews.get(title) + views);
                } else {
                    totalViews.put(title, views);
                }

            }
        }
        if (command.getObjectType().equalsIgnoreCase("movies")) {
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie) && totalViews.
                        containsKey(movie.getTitle())) {
                    outputVideos.add(movie.getTitle());
                }
            }
        } else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial) && totalViews.
                        containsKey(serial.getTitle())) {
                    outputVideos.add(serial.getTitle());
                }
            }
        }

        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (totalViews.get(o1) > totalViews.get(o2)) {
                    return 1;
                } else if (totalViews.get(o1) < totalViews.get(o2)) {
                    return -1;
                } else {
                    return o1.compareToIgnoreCase(o2);
                }

            });
        } else {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (totalViews.get(o1) > totalViews.get(o2)) {
                    return -1;
                } else if (totalViews.get(o1) < totalViews.get(o2)) {
                    return 1;
                } else {
                    return o2.compareToIgnoreCase(o1);
                }

            });
        }
        List<String> outputTitles = new ArrayList<>();
        for (String title : outputVideos) {
            if (command.getNumber() <= outputTitles.size()) {
                break;
            }
            outputTitles.add(title);
        }
        return outputTitles;
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
