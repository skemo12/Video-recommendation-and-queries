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
 * Class for longest video query, used to get the longest video query
 */
public final class LongestQuery implements QueryInterface {

    /**
     * Make it Singleton
     */
    private static LongestQuery longestQuery = null;
    /**
     * Singleton function
     */
    public static LongestQuery getInstance() {
        if (longestQuery == null) {
            longestQuery = new LongestQuery();
        }
        return longestQuery;
    }
    private List<Show> sortShowsByLongest(final List<Show> videos,
                                          final String type) {
        if (type.equalsIgnoreCase("asc")) {
            Collections.sort(videos, (o1, o2) -> {
                if (o1.getDuration() > o2.getDuration()) {
                    return 1;
                } else if (o1.getDuration() < o2.getDuration()) {
                    return -1;
                } else {
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                }

            });
        } else {
            Collections.sort(videos, (o1, o2) -> {
                if (o1.getDuration() > o2.getDuration()) {
                    return -1;
                } else if (o1.getDuration() < o2.getDuration()) {
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

        List<Show> longestVideos = new ArrayList<>();
        Filters filters = new Filters(command);

        if (command.getObjectType().equalsIgnoreCase("movies")) {
            List<Movie> movies = data.getMovies();
            for (Movie movie : movies) {
                if (filters.checkShowFilters(movie)) {
                    longestVideos.add(movie);
                }
            }
        } else {
            List<Serial> serials = data.getSerials();
            for (Serial serial : serials) {
                if (filters.checkShowFilters(serial)) {
                    longestVideos.add(serial);
                }
            }
        }

        longestVideos = sortShowsByLongest(longestVideos,
                command.getSortType());
        List<String> outputTitles = new ArrayList<>();
        for (Show video : longestVideos) {
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
