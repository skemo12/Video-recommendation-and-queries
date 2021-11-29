package solution.data;

import fileio.ShowInput;

import java.util.ArrayList;

public class Show extends ShowInput {

    private double rating;
    private int duration;
    private int numberOfViews;
    private int favoriteAddCount;


    public final int getFavoriteAddCount() {
        return favoriteAddCount;
    }

    public final void setFavoriteAddCount(final int favoriteAddCount) {
        this.favoriteAddCount = favoriteAddCount;
    }


    public final int getNumberOfViews() {
        return numberOfViews;
    }

    public final void setNumberOfViews(final int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public final int getDuration() {
        return duration;
    }

    public final void setDuration(final int duration) {
        this.duration = duration;
    }

    public final double getRating() {
        return rating;
    }

    public final void setRating(final double rating) {
        this.rating = rating;
    }


    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres,
                final double rating, final int duration) {
        super(title, year, cast, genres);
        this.rating = rating;
        this.duration = duration;
        this.numberOfViews = 0;
        this.favoriteAddCount = 0;
    }
}
