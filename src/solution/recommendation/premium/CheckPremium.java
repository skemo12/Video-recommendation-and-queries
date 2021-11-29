package solution.recommendation.premium;

import fileio.ActionInputData;
import solution.data.Database;
import solution.utility.Utility;

/**
 * Class for checking whether a video is premium or not
 */
public final class CheckPremium {

    /**
     * Make it Singleton
     */
    private static CheckPremium checkPremium = null;
    /**
     * Singleton function
     */
    public static CheckPremium getInstance() {
        if (checkPremium == null) {
            checkPremium = new CheckPremium();
        }
        return checkPremium;
    }

    /**
     * Searches and returns String video result
     */
    public boolean checkPremium(final ActionInputData command,
                                 final Database data) {

        if (!Utility.getInstance().getUserByUsername(data.getUsers(),
                        command.getUsername()).
                getSubscriptionType().equalsIgnoreCase("PREMIUM")) {
            return false;
        }
        return true;
    }
}
