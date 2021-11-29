package solution.commands;


import fileio.ActionInputData;
import solution.data.Database;
import solution.data.User;
import solution.data.Movie;
import solution.data.Serial;
import solution.utility.Utility;

import java.io.IOException;
import java.util.Map;

/**
 * Class for rating command
 */
public final class Rating implements CommandInterface {

    /**
     * Make it Singleton
     */
    private static Rating rating = null;

    /**
     * Singleton function
     */
    public static Rating getInstance() {
        if (rating == null) {
            rating = new Rating();
        }
        return rating;
    }

    /**
     * Rates a video with data from action.
     */
    public void doCommand(final ActionInputData action, final Database data)
            throws IOException {
        String username = action.getUsername();
        String title = action.getTitle();
        User user = Utility.getInstance().getUserByUsername(data.getUsers(),
                username);
        Movie movie = Utility.getInstance().getMovieByTitle(data.getMovies(),
                title);
        Serial serial = Utility.getInstance().getSerialByTitle(data.getSerials(),
                title);
        Map<String, Integer> history = user.getHistory();

        if (movie != null) {
            if (history.containsKey(title)) {
                if (movie.getRatingUsers().contains(user)) {
                    String outputMessage = "error -> " + title
                            + " has been already rated";
                    Utility.getInstance().writeOutputMessage(data, action,
                            outputMessage);
                    return;
                }

                movie.getRatingsList().add(action.getGrade());
                movie.getRatingUsers().add(user);
                Double outGrade = movie.getRatingsList().get(movie.
                        getRatingsList().size() - 1);
                String outputMessage = "success -> " + title
                        + " was rated with " + outGrade + " by "
                        + username;
                Utility.getInstance().writeOutputMessage(data, action,
                        outputMessage);
                Double grade = 0.0;
                int count = 0;
                for (Double value : movie.getRatingsList()) {
                    grade += value;
                    count++;
                }
                grade = grade / count;
                movie.setRating(grade);
                user.setNumberOfRatings(user.getNumberOfRatings() + 1);
            } else {
                String outputMessage = "error -> " + title
                        + " is not seen";
                Utility.getInstance().writeOutputMessage(data, action,
                        outputMessage);

            }
        }

        if (serial != null) {
            int seasonNumber = action.getSeasonNumber() - 1;
            if (history.containsKey(title)) {
                if (serial.getRatingUsers().get(seasonNumber).contains(user)) {
                    String outputMessage = "error -> " + title
                                + " has been already rated";
                    Utility.getInstance().writeOutputMessage(data, action,
                            outputMessage);
                    return;

                }

                serial.getRatingUsers().get(seasonNumber).add(user);
                serial.getSeasons().get(seasonNumber).getRatings().add(action.
                        getGrade());
                Double outGrade = serial.getSeasons().get(seasonNumber).
                        getRatings().get(serial.getSeasons().get(seasonNumber).
                                getRatings().size() - 1);
                String outputMessage = "success -> " + title
                        + " was rated with " + outGrade + " by "
                        + username;
                Utility.getInstance().writeOutputMessage(data, action,
                        outputMessage);

                Double grade = 0.0;
                int count = 0;
                for (double value : serial.getSeasons().get(seasonNumber).
                        getRatings()) {
                    grade += value;
                    count++;
                }
                grade = grade / count;
                serial.getGradePerSeason().set(seasonNumber, grade);
                grade = 0.0;
                for (int i = 0; i < serial.getNumberOfSeasons(); i++) {
                        grade += serial.getGradePerSeason().get(i);
                }
                grade = grade / serial.getNumberOfSeasons();
                serial.setRating(grade);
                user.setNumberOfRatings(user.getNumberOfRatings() + 1);
            } else {
                String outputMessage = "error -> " + title
                        + " is not seen";
                Utility.getInstance().writeOutputMessage(data, action,
                        outputMessage);

            }
        }
    }

}


