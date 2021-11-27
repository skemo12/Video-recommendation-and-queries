package solution.query.users;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;
import solution.query.Filters;
import solution.query.videos.LongestQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberOfRatingsQuery {

    private static NumberOfRatingsQuery numberOfRatingsQuery = null;
    public static NumberOfRatingsQuery getInstance() {
        if (numberOfRatingsQuery == null) {
            numberOfRatingsQuery = new NumberOfRatingsQuery();
        }
        return numberOfRatingsQuery;
    }

    private List<String> createOutputTitlesMovies(ActionInputData command,
                                                  ParseData data) {
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
                } else return o1.getUsername().compareToIgnoreCase(o2.getUsername());

            });
        } else {
            Collections.sort(outputUsers, (o1, o2) -> {
                if (o1.getNumberOfRatings() > o2.getNumberOfRatings()) {
                    return -1;
                } else if (o1.getNumberOfRatings() < o2.getNumberOfRatings()) {
                    return 1;
                } else return o1.getUsername().compareToIgnoreCase(o2.getUsername());

            });
        }
        List<String> outputUsernames= new ArrayList<>();
        for (User user : outputUsers) {
            if (command.getNumber() <= outputUsers.size()) {
                break;

            }
            outputUsernames.add(user.getUsername());
        }
        return outputUsernames;
    }

    public void numberOfRatingsQuery(final ActionInputData command, ParseData data,
                            final Writer fileWriter,
                            final JSONArray arrayResult) throws IOException {

        List<String> bestVideos = createOutputTitlesMovies(command, data);
        String outputMessage = "Query result: " + bestVideos;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }

}
