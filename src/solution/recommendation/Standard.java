package solution.recommendation;

import fileio.ActionInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.*;

import java.io.IOException;
import java.util.List;

public class Standard {

    private static Standard standard = null;
    public static Standard getInstance() {
        if (standard == null) {
            standard = new Standard();
        }
        return standard;
    }

    public String searchVideo (ActionInputData command, ParseData data) {
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
        return command.getType() + "Recommendation cannot be applied!";
    }
    public void getRecomandation(final ActionInputData command, ParseData data,
                                final Writer fileWriter,
                                final JSONArray arrayResult) throws IOException {

        String video = searchVideo(command, data);
        String outputMessage = "StandardRecommendation result: " + video;
        arrayResult.add(fileWriter.writeFile(command.getActionId(),
                "no field", outputMessage));

    }
}
