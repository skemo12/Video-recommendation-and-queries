package solution;

import fileio.*;
import org.json.simple.JSONArray;
import solution.commands.Rating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseData {

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public void setSerials(List<Serial> serials) {
        this.serials = serials;
    }

    private List<Movie> movies;
    private List<Serial> serials;
    private List<User> users;
    private List<ActorInputData> actors;
    private List<ActionInputData> actions;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<ActorInputData> getActors() {
        return actors;
    }

    public void setActors(List<ActorInputData> actors) {
        this.actors = actors;
    }

    public List<ActionInputData> getActions() {
        return actions;
    }

    public void setActions(List<ActionInputData> actions) {
        this.actions = actions;
    }

    public ParseData(final Input input) {

        List<Movie> movies = new ArrayList<>();
        for (MovieInputData movie : input.getMovies()) {
            Movie newMovie = new Movie(movie);
            movies.add(newMovie);
        }

        List<Serial> serials = new ArrayList<>();
        for (SerialInputData serial : input.getSerials()) {
            Serial newSerial = new Serial(serial);
            serials.add(newSerial);
        }
        this.users = new ArrayList<>();
        for (UserInputData user : input.getUsers()) {
            this.users.add(new User(user));
        }
        this.actors = input.getActors();
        this.actions = input.getCommands();
        this.movies = movies;
        this.serials = serials;
    }

}
