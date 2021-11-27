package solution;

import fileio.ShowInput;
import fileio.UserInputData;

import java.util.ArrayList;

public class Show extends ShowInput{

    private double rating;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public Show(String title, int year, ArrayList<String> cast, ArrayList<String> genres) {
        super(title, year, cast, genres);
        this.rating = 0;
    }
}
