package solution.data;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Show {

    private List<User> ratingUsers;

    private List<Double> ratingsList;

    public Movie(final MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(),
                movie.getGenres(), 0.0, movie.getDuration());
        this.ratingUsers = new ArrayList<>();
        this.ratingsList = new ArrayList<>();
    }

    public List<Double> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(final List<Double> ratingsList) {
        this.ratingsList = ratingsList;
    }

    public List<User> getRatingUsers() {
        return ratingUsers;
    }

    public void setRatingUsers(final List<User> ratingUsers) {
        this.ratingUsers = ratingUsers;
    }


}
