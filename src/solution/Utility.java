package solution;

import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

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

    public UserInputData getUserByName(final List<UserInputData> users,
                                       final String username) {
        for (UserInputData user : users) {
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
}
