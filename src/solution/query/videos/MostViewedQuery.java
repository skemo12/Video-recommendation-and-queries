package solution.query.videos;

import fileio.ActionInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;
import solution.query.Filters;

import java.io.IOException;
import java.util.*;

public class MostViewedQuery {

    private static MostViewedQuery mostViewedQuery = null;
    public static MostViewedQuery getInstance() {
        if (mostViewedQuery == null) {
            mostViewedQuery = new MostViewedQuery();
        }
        return mostViewedQuery;
    }

    private List<String> createOutputTitlesMovies(ActionInputData command,
                                                  ParseData data) {

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
        if (command.getObjectType().equalsIgnoreCase("movies")){
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie) && totalViews.containsKey(movie.getTitle())) {
                    outputVideos.add(movie.getTitle());
                }
            }
        }
        else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial) && totalViews.containsKey(serial.getTitle())) {
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
                } else return o1.compareToIgnoreCase(o2);

            });
        } else {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (totalViews.get(o1) > totalViews.get(o2)) {
                    return -1;
                } else if (totalViews.get(o1) < totalViews.get(o2)) {
                    return 1;
                } else return o1.compareToIgnoreCase(o2);

            });
        }

        return outputVideos;
    }

    public void mostViewedQuery(final ActionInputData command, ParseData data,
                            final Writer fileWriter,
                            final JSONArray arrayResult) throws IOException {

        List<String> bestVideos = createOutputTitlesMovies(command, data);
        String outputMessage = "Query result: " + bestVideos;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }

}
