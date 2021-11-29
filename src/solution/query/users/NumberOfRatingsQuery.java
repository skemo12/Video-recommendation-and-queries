package solution.query.users;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;
import solution.data.User;
import solution.query.QueryInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        if (command.getSortType().equalsIgnoreCase("asc")) {
            Collections.sort(outputUsers, (o1, o2) -> {
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
            Collections.sort(outputUsers, (o1, o2) -> {
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
    public void getQuery(final ActionInputData command,
                         final Database data,
                         final Writer fileWriter,
                         final JSONArray arrayResult) throws IOException {

        List<String> bestVideos = createOutputList(command, data);
        String outputMessage = "Query result: " + bestVideos;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }

}
