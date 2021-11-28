package solution.query.videos;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.Database;
import solution.Movie;
import solution.Serial;
import solution.Show;
import solution.query.Filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LongestQuery {

    private static LongestQuery longestQuery = null;
    public static LongestQuery getInstance() {
        if (longestQuery == null) {
            longestQuery = new LongestQuery();
        }
        return longestQuery;
    }

    private List<String> createOutputTitlesMovies(ActionInputData command,
                                                  Database data) {
        List<Show> outputVideos = new ArrayList<>();
        Filters filters = new Filters(command);

        if (command.getObjectType().equalsIgnoreCase("movies")){
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie)) {
                    outputVideos.add(movie);
                }
            }
        }
        else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial)) {
                    outputVideos.add(serial);
                }
            }
        }

        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (o1.getDuration() > o2.getDuration()) {
                    return 1;
                } else if (o1.getDuration() < o2.getDuration()) {
                    return -1;
                } else return o1.getTitle().compareToIgnoreCase(o2.getTitle());

            });
        } else {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (o1.getDuration() > o2.getDuration()) {
                    return -1;
                } else if (o1.getDuration() < o2.getDuration()) {
                    return 1;
                } else return o1.getTitle().compareToIgnoreCase(o2.getTitle());

            });
        }
        List<String> outputTitles = new ArrayList<>();
        for (Show show : outputVideos) {
            if (command.getNumber() <= outputTitles.size()) {
                break;

            }
            outputTitles.add(show.getTitle());
        }
        return outputTitles;
    }

    public void ratingQuery(final ActionInputData command, Database data,
                            final Writer fileWriter,
                            final JSONArray arrayResult) throws IOException {

        List<String> bestVideos = createOutputTitlesMovies(command, data);
        String outputMessage = "Query result: " + bestVideos;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }

}
