package solution;

import fileio.MovieInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Show{

    private List<User> ratingUsers;

    private List<Double> ratingsList;

    public Movie(MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(),
                movie.getGenres(),0.0, movie.getDuration());
        this.ratingUsers = new ArrayList<>();
        this.ratingsList = new ArrayList<>();
    }

    public List<Double> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<Double> ratingsList) {
        this.ratingsList = ratingsList;
    }

    public List<User> getRatingUsers() {
        return ratingUsers;
    }

    public void setRatingUsers(List<User> ratingUsers) {
        this.ratingUsers = ratingUsers;
    }


}
