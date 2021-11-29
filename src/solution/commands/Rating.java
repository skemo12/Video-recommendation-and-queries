package solution.commands;


import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.User;
import solution.data.Movie;
import solution.data.Serial;
import solution.utility.Utility;

import java.io.IOException;
import java.util.Map;

public final class Rating {

    /**
     * Make it Singleton
     */
    private static Rating rating = null;

    /**
     * Singleton function
     */
    public static Rating getRating() {
        if (rating == null) {
            rating = new Rating();
        }
        return rating;
    }


    /**
     * Rates a video with data from command.
     */
    public void rateVideo(final ActionInputData command, final Database data,
                           final Writer fileWriter, final JSONArray arrayResult)
            throws IOException {
        String username = command.getUsername();
        String title = command.getTitle();
        User user = Utility.getUtility().getUserByUsername(data.getUsers(),
                username);
        Movie movie = Utility.getUtility().getMovieByTitle(data.getMovies(),
                title);
        Serial serial = Utility.getUtility().getSerialByTitle(data.getSerials(),
                title);
        Map<String, Integer> history = user.getHistory();

        if (movie != null) {
            if (history.containsKey(title)) {
                if (movie.getRatingUsers().contains(user)) {
                    String outputMessage = "error -> " + title
                            + " has been already rated";
                    arrayResult.add(fileWriter.writeFile(command.getActionId(),
                                "no field", outputMessage));
                    return;

                }
                movie.getRatingsList().add(command.getGrade());
                movie.getRatingUsers().add(user);
                Double outGrade = movie.getRatingsList().get(movie.
                        getRatingsList().size() - 1);
                String outputMessage = "success -> " + title
                        + " was rated with " + outGrade + " by "
                        + username;
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));
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
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));

            }
        }

        if (serial != null) {
            int seasonNumber = command.getSeasonNumber() - 1;
            if (history.containsKey(title)) {
                if (serial.getRatingUsers().get(seasonNumber).contains(user)) {
                    String outputMessage = "error -> " + title
                                + " has been already rated";
                    arrayResult.add(fileWriter.writeFile(command.getActionId(),
                                "no field", outputMessage));
                    return;

                }

                serial.getRatingUsers().get(seasonNumber).add(user);
                serial.getSeasons().get(seasonNumber).getRatings().add(command.
                        getGrade());
                Double outGrade = serial.getSeasons().get(seasonNumber).
                        getRatings().get(serial.getSeasons().get(seasonNumber).
                                getRatings().size() - 1);
                String outputMessage = "success -> " + title
                        + " was rated with " + outGrade + " by "
                        + username;
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));

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
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));

            }
        }
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
}


