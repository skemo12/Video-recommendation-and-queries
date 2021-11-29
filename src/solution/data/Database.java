package solution.data;

import fileio.ActorInputData;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom database class that contains custom movies, serials, actors and users
 * Actions remain the same as in fileio package
 */
public final class Database {

    /**
     * Movies list
     */
    private List<Movie> movies;
    /**
     * Serials list
     */
    private List<Serial> serials;
    /**
     * Users list
     */
    private List<User> users;
    /**
     * Actors list
     */
    private List<Actor> actors;
    /**
     * Actions list
     */
    private List<ActionInputData> actions;
    /**
     * fileWriter given
     */
    private Writer fileWriter;
    /**
     * file for the output to be written in
     */
    private JSONArray arrayResult;

    public Database(final Input input, final  Writer fileWriter,
                    final JSONArray arrayResult) {

        this.actions = input.getCommands();
        this.fileWriter = fileWriter;
        this.arrayResult = arrayResult;

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

        this.actors = new ArrayList<>();
        for (ActorInputData actor : input.getActors()) {
            this.actors.add(new Actor(actor));
        }

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(final List<Actor> actors) {
        this.actors = actors;
    }

    public List<ActionInputData> getActions() {
        return actions;
    }

    public void setActions(final List<ActionInputData> actions) {
        this.actions = actions;
    }

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

    public Writer getFileWriter() {
        return fileWriter;
    }

    public void setFileWriter(final Writer fileWriter) {
        this.fileWriter = fileWriter;
    }

    public JSONArray getArrayResult() {
        return arrayResult;
    }

    public void setArrayResult(final JSONArray arrayResult) {
        this.arrayResult = arrayResult;
    }
}
