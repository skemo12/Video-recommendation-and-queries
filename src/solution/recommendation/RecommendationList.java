package solution.recommendation;

import fileio.ActionInputData;
import solution.data.Database;

import java.io.IOException;
import java.util.List;

/**
 * Interface for recommendations that return a list of Strings in recommendation
 * package
 */
public interface RecommendationList {
    /**
     * Calls search video to get List<String> video recommendation, creates
     * output message
     * @param1 is command from main for that specific command
     * @param2 is the custom database
     * @param3 is the output fileWriter
     * @param4 is the output file to be used by fileWriter
     */
    void getRecommendation(ActionInputData command, Database data)
            throws IOException;
    /**
     * Searches and returns List<String> video result for recommendation type
     * @param1 is the command used in getQuery method
     * @param2 is the same database as in getQuery method
     */
    List<String> searchVideoList(ActionInputData command, Database data);
}
