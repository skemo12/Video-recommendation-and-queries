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
import java.util.*;

public class RatingQuery {

    private static RatingQuery rating = null;
    public static RatingQuery getInstance() {
        if (rating == null) {
            rating = new RatingQuery();
        }
        return rating;
    }

    private List<String> createOutputTitlesMovies(ActionInputData command,
                                            Database data) {
        List<Show> outputVideos = new ArrayList<>();
        Filters filters = new Filters(command);
        if (command.getObjectType().equalsIgnoreCase("movies")){
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie) && movie.getRating() != 0.0) {
                    outputVideos.add(movie);
                }
            }
        }
        else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial) && serial.getRating() != 0.0) {
                    outputVideos.add(serial);
                }
            }
        }

        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                    return 1;
                } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                    return -1;
                } else return o1.getTitle().compareToIgnoreCase(o2.getTitle());

            });
        } else {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                    return -1;
                } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
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
