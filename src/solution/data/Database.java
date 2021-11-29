package solution.data;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public final class Database {

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final List<Movie> movies) {
        this.movies = movies;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public void setSerials(final List<Serial> serials) {
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

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public List<ActorInputData> getActors() {
        return actors;
    }

    public void setActors(final List<ActorInputData> actors) {
        this.actors = actors;
    }

    public List<ActionInputData> getActions() {
        return actions;
    }

    public void setActions(final List<ActionInputData> actions) {
        this.actions = actions;
    }

    public Database(final Input input) {

        this.movies = new ArrayList<>();
        for (MovieInputData movie : input.getMovies()) {
            Movie newMovie = new Movie(movie);
            this.movies.add(newMovie);
        }

        this.serials = new ArrayList<>();
        for (SerialInputData serial : input.getSerials()) {
            Serial newSerial = new Serial(serial);
            this.serials.add(newSerial);
        }
        this.users = new ArrayList<>();
        for (UserInputData user : input.getUsers()) {
            this.users.add(new User(user));
        }
        this.actors = input.getActors();
        this.actions = input.getCommands();
    }

}
