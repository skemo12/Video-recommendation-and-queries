package solution;

import fileio.ShowInput;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class Show extends ShowInput{

    private double rating;
    private int duration;
    private int numberOfViews;
    private int favoriteAddCount;


    public int getFavoriteAddCount() {
        return favoriteAddCount;
    }

    public void setFavoriteAddCount(int favoriteAddCount) {
        this.favoriteAddCount = favoriteAddCount;
    }


    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public Show(String title, int year, ArrayList<String> cast, ArrayList<String> genres, double rating, int duration) {
        super(title, year, cast, genres);
        this.rating = rating;
        this.duration = duration;
        this.numberOfViews = 0;
        this.favoriteAddCount = 0;
    }
}
