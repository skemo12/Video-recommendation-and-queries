package solution.recommendation;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import solution.data.Database;

import java.io.IOException;

public interface RecommendationString {
    /**
     * Calls search video to get String video recommendation, creates
     * output message
     */
    void getRecommendation(ActionInputData command, Database data,
                           Writer fileWriter, JSONArray arrayResult)
            throws IOException;
    /**
     * Searches and returns String video result for recommendation type
     */
    String searchVideo(ActionInputData command, Database data);
}
