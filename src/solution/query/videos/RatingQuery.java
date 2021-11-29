package solution.query.videos;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.Show;
import solution.query.Filters;
import solution.query.QueryInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class RatingQuery implements QueryInterface {

    /**
     * Make it Singleton
     */
    private static RatingQuery rating = null;
    /**
     * Singleton function
     */
    public static RatingQuery getInstance() {
        if (rating == null) {
            rating = new RatingQuery();
        }
        return rating;
    }
    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {
        List<Show> outputVideos = new ArrayList<>();
        Filters filters = new Filters(command);
        if (command.getObjectType().equalsIgnoreCase("movies")) {
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie)
                        && movie.getRating() != 0.0) {
                    outputVideos.add(movie);
                }
            }
        } else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial)
                        && serial.getRating() != 0.0) {
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
                } else {
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                }

            });
        } else {
            Collections.sort(outputVideos, (o1, o2) -> {
                if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                    return -1;
                } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                    return 1;
                } else {
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                }

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
