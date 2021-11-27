package solution.commands;


import entertainment.Season;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.Movie;
import solution.ParseData;
import solution.Serial;
import solution.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public void rateVideo(final ActionInputData command, ParseData data,
                           final Writer fileWriter, final JSONArray arrayResult)
            throws IOException {
        String username = command.getUsername();
        String title = command.getTitle();
        UserInputData user = Utility.getUtility().getUserByName(data.getUsers(),
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
                Double outGrade = movie.getRatingsList().get(movie.getRatingsList().size() - 1);
                String outputMessage = "success -> " + title
                        + " was rated with " + outGrade + " by "
                        + username;
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));
                Double grade = 0.0;
                int count = 0;
                for (Double value : movie.getRatingsList()){
                    grade += value;
                    count++;
                }
                grade = grade / count;
                movie.setRating(grade);
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
                serial.getSeasons().get(seasonNumber).getRatings().add(command.getGrade());
                Double outGrade = serial.getSeasons().get(seasonNumber).
                        getRatings().get(serial.getSeasons().get(seasonNumber).getRatings().size() - 1);
                String outputMessage = "success -> " + title
                        + " was rated with " + outGrade + " by "
                        + username;
                arrayResult.add(fileWriter.writeFile(command.getActionId(),
                        "no field", outputMessage));

                Double grade = 0.0;
                int count = 0;
                for (double value : serial.getSeasons().get(seasonNumber).getRatings()) {
                    grade += value;
                    count++;
                }
                grade = grade / count;
                serial.getGradePerSeason().set(seasonNumber, grade);
                grade = 0.0;
                for (int i = 0; i < serial.getNumberOfSeasons(); i++) {
                        grade +=serial.getGradePerSeason().get(i);
                }
                grade = grade / serial.getNumberOfSeasons();
                serial.setRating(grade);

            }
        }
    }

    public double getRatingByTitle(String title, ParseData data) {
        for (Movie movie : data.getMovies()){
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie.getRating();
            }
        }
        for (Serial serial : data.getSerials()){
            if (serial.getTitle().equalsIgnoreCase(title)) {
                return serial.getRating();
            }
        }
        return 0.0;
    }
}


