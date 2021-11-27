package solution;

import fileio.*;

import java.util.List;

public final class Utility {

    // make it Singleton
    private static Utility utility = null;

    public static Utility getUtility() {
        if (utility == null) {
            utility = new Utility();
        }
        return utility;
    }

    public User getUserByName(final List<User> users,
                                       final String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public Movie getMovieByTitle(final List<Movie> movies,
                                           final String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }

    public Serial getSerialByTitle(final List<Serial> serials,
                                            final String title) {
        for (Serial serial : serials) {
            if (serial.getTitle().equalsIgnoreCase(title)) {
                return serial;
            }
        }
        return null;
    }

    public ActorInputData getActorByName(final List<ActorInputData> actors,
                                            final String title) {
        for (ActorInputData actor : actors) {
            if (actor.getName().equalsIgnoreCase(title)) {
                return actor;
            }
        }
        return null;
    }

    public void updateFavorite(ParseData data) {
        for (User user : data.getUsers()) {
            for (String favorite : user.getFavoriteMovies()) {
                Movie movie = getMovieByTitle(data.getMovies(), favorite);
                if (movie != null) {
                    movie.setFavoriteAddCount(movie.getFavoriteAddCount() + 1);
                }
                Serial serial = getSerialByTitle(data.getSerials(), favorite);
                if (serial != null) {
                    serial.setFavoriteAddCount(serial.getFavoriteAddCount() + 1);
                }
            }

        }
    }
}
