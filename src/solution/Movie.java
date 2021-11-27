package solution;

import fileio.MovieInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Show{

    private int duration;
    private List<UserInputData> ratingUsers;

    private List<Double> ratingsList;

    public Movie(String title, int year, ArrayList<String> cast, ArrayList<String> genres, int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratingUsers = new ArrayList<>();
        this.ratingsList = new ArrayList<>();
    }

    public Movie(MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(),
                movie.getGenres());
        this.duration = movie.getDuration();
        this.ratingUsers = new ArrayList<>();
        this.ratingsList = new ArrayList<>();
    }

    public List<Double> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<Double> ratingsList) {
        this.ratingsList = ratingsList;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<UserInputData> getRatingUsers() {
        return ratingUsers;
    }

    public void setRatingUsers(List<UserInputData> ratingUsers) {
        this.ratingUsers = ratingUsers;
    }


}
