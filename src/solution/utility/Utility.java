package solution.utility;

import actor.ActorsAwards;
import fileio.ActorInputData;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.data.Show;
import solution.data.Database;

import java.util.List;
import java.util.Map;

public final class Utility {

    /**
     * Make it singleton
     */
    private static Utility utility = null;
    /**
     * Singleton function
     */
    public static Utility getUtility() {
        if (utility == null) {
            utility = new Utility();
        }
        return utility;
    }

    /**
     * Returns User according to username
     */
    public User getUserByUsername(final List<User> users,
                                  final String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns Movie according to title
     */
    public Movie getMovieByTitle(final List<Movie> movies,
                                 final String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Returns Serial according to title
     */
    public Serial getSerialByTitle(final List<Serial> serials,
                                   final String title) {
        for (Serial serial : serials) {
            if (serial.getTitle().equalsIgnoreCase(title)) {
                return serial;
            }
        }
        return null;
    }
    /**
     * Gets total awards an actor has
     */
    public Integer getTotalAwardsNumber(final ActorInputData actor) {
        Map<ActorsAwards, Integer> awards = actor.getAwards();
        Integer total = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    /**
     * Updates FavoriteAddCount for both movies and serials
     */
    public void updateFavorite(final Database data) {
        for (User user : data.getUsers()) {
            for (String favorite : user.getFavoriteMovies()) {
                Movie movie = getMovieByTitle(data.getMovies(), favorite);
                if (movie != null) {
                    movie.setFavoriteAddCount(movie.getFavoriteAddCount() + 1);
                }
                Serial serial = getSerialByTitle(data.getSerials(), favorite);
                if (serial != null) {
                    serial.setFavoriteAddCount(serial.getFavoriteAddCount()
                            + 1);
                }
            }

        }
    }
    /**
     * Comparator for Database
     */
    public int getDatabaseOrder(final Show o1, final Show o2,
                                final Database data) {
        if (data.getMovies().contains(o1)) {
            if (data.getMovies().contains(o2)) {
                return data.getMovies().indexOf(o1)
                        - data.getMovies().indexOf(o2);
            } else {
                return data.getMovies().indexOf(o1)
                        - data.getSerials().indexOf(o2);
            }
        } else {
            if (data.getMovies().contains(o2)) {
                return data.getSerials().indexOf(o1)
                        - data.getMovies().indexOf(o2);
            } else {
                return data.getSerials().indexOf(o1)
                        - data.getSerials().indexOf(o2);
            }
        }
    }
}
