package solution.recommendation;

import fileio.ActionInputData;
import solution.data.Database;

import java.io.IOException;

/**
 * Interface for recommendations that return a String in recommendation package
 */
public interface RecommendationString {
    /**
     * Calls search video to get String video recommendation, creates
     * output message
     * @param1 is command from main for that specific command
     * @param2 is the custom database
     * @param3 is the output fileWriter
     * @param4 is the output file to be used by fileWriter
     */
    void getRecommendation(ActionInputData command, Database data)
            throws IOException;
    /**
     * Searches and returns String video result for recommendation type
     * @param1 is the command used in getQuery method
     * @param2 is the same database as in getQuery method
     */
    String searchVideo(ActionInputData command, Database data);
}
