package solution.commands;

import fileio.UserInputData;

import java.util.List;

public class Utility {

    // make it Singleton
    private static Utility utility = null;

    public static Utility getUtility() {
        if (utility == null) {
            utility = new Utility();
        }
        return utility;
    }

    public UserInputData getUserByName (List<UserInputData> users, String username) {
        for (UserInputData user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
}
