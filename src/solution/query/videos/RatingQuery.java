package solution.query.videos;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.Show;
import solution.query.Filters;
import solution.query.QueryInterface;
import solution.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for rating video query, used to get rating video query
 */
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
     * Sorts list of shows by average rating according to type
     */
    private List<Show> sortShowsByRating(final List<Show> videos,
                                             final String type) {

        if (type.equalsIgnoreCase("asc")) {
            Collections.sort(videos, (o1, o2) -> {
                if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                    return 1;
                } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                    return -1;
                } else {
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                }

            });
        } else {
            Collections.sort(videos, (o1, o2) -> {
                if (Double.compare(o1.getRating(), o2.getRating()) > 0) {
                    return -1;
                } else if (Double.compare(o1.getRating(), o2.getRating()) < 0) {
                    return 1;
                } else {
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                }

            });
        }
        return videos;
    }

    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {

        List<Show> mostViewedVideos = new ArrayList<>();
        Filters filters = new Filters(command);
        if (command.getObjectType().equalsIgnoreCase("movies")) {
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie)
                        && movie.getRating() != 0.0) {
                    mostViewedVideos.add(movie);
                }
            }
        } else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial)
                        && serial.getRating() != 0.0) {
                    mostViewedVideos.add(serial);
                }
            }
        }


        List<String> outputTitles = new ArrayList<>();
        for (Show video : mostViewedVideos) {
            if (command.getNumber() <= outputTitles.size()) {
                break;
            }
            outputTitles.add(video.getTitle());
        }
        return outputTitles;
    }

    /**
     * Creates output message and calls method for query
     */
    public void getQuery(final ActionInputData action, final Database data)
            throws IOException {

        List<String> bestVideos = createOutputList(action, data);
        String outputMessage = "Query result: " + bestVideos;
        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);

    }

}
