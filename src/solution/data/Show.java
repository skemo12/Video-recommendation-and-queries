package solution.data;

import fileio.ShowInput;

import java.util.ArrayList;

/**
 * Custom show class with extra features
 * Extends class ShowInput from fileio
 */
public class Show extends ShowInput {

    /**
     * Show's overall rating
     */
    private double rating;
    /**
     * Show's overall duration
     */
    private int duration;
    /**
     * Show's overall number of views
     */
    private int numberOfViews;
    /**
     * Show's number of users who added it to favorite list
     */
    private int favoriteCount;


    public final int getFavoriteCount() {
        return favoriteCount;
    }

    public final void setFavoriteCount(final int favoriteCount) {
        this.favoriteCount = favoriteCount;
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
        this.favoriteCount = 0;
    }
}
