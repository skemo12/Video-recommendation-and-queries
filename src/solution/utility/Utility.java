package solution.utility;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.Actor;
import solution.data.Movie;
import solution.data.Serial;
import solution.data.User;
import solution.data.Show;;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Utility class, contains useful methods used all around the solution
 */
public final class Utility {

    /**
     * Make it singleton
     */
    private static Utility utility = null;
    /**
     * Singleton function
     */
    public static Utility getInstance() {
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
     * Returns rating for String title
     */
    public double getRatingByTitle(final String title, final Database data) {
        for (Movie movie : data.getMovies()) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie.getRating();
            }
        }
        for (Serial serial : data.getSerials()) {
            if (serial.getTitle().equalsIgnoreCase(title)) {
                return serial.getRating();
            }
        }
        return 0.0;
    }
    /**
     * Updates FavoriteAddCount for both movies and serials
     */
    public void updateFavoriteCount(final Database data) {
        for (User user : data.getUsers()) {
            for (String favorite : user.getFavoriteMovies()) {
                Movie movie = getMovieByTitle(data.getMovies(), favorite);
                if (movie != null) {
                    movie.setFavoriteCount(movie.getFavoriteCount() + 1);
                }
                Serial serial = getSerialByTitle(data.getSerials(), favorite);
                if (serial != null) {
                    serial.setFavoriteCount(serial.getFavoriteCount()
                            + 1);
                }
            }

        }
    }
    /**
     * Updates numberOfViews for both movies and serials
     */
    public void updateViewNumber(final Database data) {
        for (User user : data.getUsers()) {
            for (Map.Entry<String, Integer> video : user.getHistory().
                    entrySet()) {
                Movie movie = getMovieByTitle(data.getMovies(), video.getKey());
                if (movie != null) {
                    movie.setNumberOfViews(movie.getNumberOfViews()
                            + video.getValue());
                }
                Serial serial = getSerialByTitle(data.getSerials(),
                        video.getKey());
                if (serial != null) {
                    serial.setNumberOfViews(serial.getNumberOfViews()
                            + video.getValue());
                }
            }

        }
    }
    /**
     * Updates grade for all actors
     */
    public void updateGradeActor(final Database data) {
        for (Actor actor : data.getActors()) {
            List<String> videoList = actor.getFilmography();
            double grade = 0.0;
            int count = 0;
            for (String title : videoList) {
                if (getRatingByTitle(title, data) != 0) {
                    grade += getRatingByTitle(title, data);
                    count++;
                }

            }
            if (grade != 0.0 && count != 0) {
                grade = grade / count;
                actor.setGrade(grade);
            }
        }
    }
    /**
     * Updates total numbers of awards
     */
    public void updateAwardsNumberActor(final Database data) {
        for (Actor actor : data.getActors()) {
            List<String> videoList = actor.getFilmography();
            double grade = 0.0;
            int count = 0;
            for (String title : videoList) {
                if (getRatingByTitle(title, data) != 0) {
                    grade += getRatingByTitle(title, data);
                    count++;
                }

            }
            if (grade != 0.0 && count != 0) {
                grade = grade / count;
                actor.setGrade(grade);
            }
        }
    }
    /**
     * Writes message to data output using data file writer
     */
    public void writeOutputMessage(final Database data,
                                   final ActionInputData action,
                                   final String message) throws IOException {
        data.getArrayResult().add(data.getFileWriter()
                .writeFile(action.getActionId(), "no field",
                        message));
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
