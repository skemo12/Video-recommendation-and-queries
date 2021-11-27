package solution.recommendation.premium;

import fileio.ActionInputData;
import solution.ParseData;
import solution.Utility;

public class CheckPremium {
    private static CheckPremium checkPremium = null;
    public static CheckPremium getInstance() {
        if (checkPremium == null) {
            checkPremium = new CheckPremium();
        }
        return checkPremium;
    }

    public boolean checkPremium (ActionInputData command, ParseData data) {
        if (!Utility.getUtility().getUserByName(data.getUsers(), command.getUsername()).
                getSubscriptionType().equalsIgnoreCase("PREMIUM")) {
            return false;
        }
        return true;
    }
}
