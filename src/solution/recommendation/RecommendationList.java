package solution.recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;

import java.io.IOException;
import java.util.List;

public interface RecommendationList {
    /**
     * Calls search video to get List<String> video recommendation, creates
     * output message
     */
    void getRecommendation(ActionInputData command, Database data,
                           Writer fileWriter, JSONArray arrayResult)
            throws IOException;
    /**
     * Searches and returns List<String> video result for recommendation type
     */
    List<String> searchVideoList(ActionInputData command, Database data);
}
