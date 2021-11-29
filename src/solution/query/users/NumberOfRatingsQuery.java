package solution.query.users;

import fileio.ActionInputData;
import solution.data.Database;
import solution.data.User;
import solution.query.QueryInterface;
import solution.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for user query, used to get number of ratings query
 */
public final class NumberOfRatingsQuery implements QueryInterface {

    /**
     * Make it Singleton
     */
    private static NumberOfRatingsQuery numberOfRatingsQuery = null;
    /**
     * Singleton function
     */
    public static NumberOfRatingsQuery getInstance() {
        if (numberOfRatingsQuery == null) {
            numberOfRatingsQuery = new NumberOfRatingsQuery();
        }
        return numberOfRatingsQuery;
    }

    /**
     * Sorts list of users by number of ratings given according to type
     */
    private List<User> sortUsersByRatings(final List<User> users,
                                          final String type) {

        if (type.equalsIgnoreCase("asc")) {
            Collections.sort(users, (o1, o2) -> {
                if (o1.getNumberOfRatings() > o2.getNumberOfRatings()) {
                    return 1;
                } else if (o1.getNumberOfRatings() < o2.getNumberOfRatings()) {
                    return -1;
                } else {
                    return o1.getUsername().
                            compareToIgnoreCase(o2.getUsername());
                }

            });
        } else {
            Collections.sort(users, (o1, o2) -> {
                if (o1.getNumberOfRatings() > o2.getNumberOfRatings()) {
                    return -1;
                } else if (o1.getNumberOfRatings() < o2.getNumberOfRatings()) {
                    return 1;
                } else {
                    return o2.getUsername().
                            compareToIgnoreCase(o1.getUsername());
                }

            });
        }
        return users;
    }

    /**
     * Method to create output String list
     */
    public List<String> createOutputList(final ActionInputData command,
                                          final Database data) {
        List<User> outputUsers = new ArrayList<>();
        for (User user : data.getUsers()) {
            if (user.getNumberOfRatings() > 0) {
                outputUsers.add(user);
            }
        }

        outputUsers = sortUsersByRatings(outputUsers, command.getSortType());
        List<String> outputUsernames = new ArrayList<>();
        for (User user : outputUsers) {
            if (command.getNumber() <= outputUsernames.size()) {
                break;
            }
            outputUsernames.add(user.getUsername());
        }
        return outputUsernames;
    }

    /**
     * Creates output message and calls method for query
     */
    public void getQuery(final ActionInputData action, final Database data)
            throws IOException {

        List<String> bestVideos = createOutputList(action, data);
        String outputMessage = "Query result: " + bestVideos;
        Utility.getInstance().writeOutputMessage(data, action,
                outputMessage);

    }

}
