package solution.recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;

import java.io.IOException;

public class Standard {

    private static Standard standard = null;
    public static Standard getInstance() {
        if (standard == null) {
            standard = new Standard();
        }
        return standard;
    }

    public String searchVideo (ActionInputData command, Database data) {
        String username = command.getUsername();
        User user = Utility.getUtility().getUserByName(data.getUsers(), username);
        for (Movie movie : data.getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                return movie.getTitle();
            }
        }

        for (Serial serial : data.getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                return serial.getTitle();
            }
        }
        return null;
    }
    public void getRecomandation(final ActionInputData command, Database data,
                                final Writer fileWriter,
                                final JSONArray arrayResult) throws IOException {

        String video = searchVideo(command, data);

        if (video == null) {
            String commandType = command.getType();
            commandType = commandType.substring(0,1).toUpperCase() + commandType.substring(1).toLowerCase();
            String outputMessage =  commandType + "Recommendation cannot be applied!";
            arrayResult.add(fileWriter.writeFile(command.getActionId(),
                    "no field", outputMessage));
            return;
        }
        String outputMessage = "StandardRecommendation result: " + video;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
