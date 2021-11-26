package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class Favorite {

    // TODO: make it Singleton
    private static Favorite favorite = null;

    public static Favorite getFavorite() {
        if (favorite == null) {
            favorite = new Favorite();
        }
        return favorite;
    }

    public void addFavorite(ActionInputData command, Input input){
        String username = command.getUsername();
        List<UserInputData> users = input.getUsers();
        String title = command.getTitle();
        UserInputData user = users.getUsername("username");


    }
}
