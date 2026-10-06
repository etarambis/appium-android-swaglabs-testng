package data;

import models.User;
import org.testng.annotations.DataProvider;

public class CustomDataProviders {
    public static final String DP_CREDENTIALS = "dpCredentials";
    public static final String DP_ERROR_MESSAGE = "dpErrorMessage";

    @DataProvider(name = DP_CREDENTIALS)
    public static Object[][] credentialsDataProvider() {
        final var invalid = DataGiver.getInvalidCredentials();
        final var locked = DataGiver.getLockedCredentials();

        return new Object[][] {
                {invalid.getUsername(), invalid.getPassword(), invalid.getMessage()},
                {locked.getUsername(), locked.getPassword(), locked.getMessage()}
        };
    }

    @DataProvider(name = DP_ERROR_MESSAGE)
    public static Object[][] errorMessageDataProvider() {

        final var user = new User(); //creado con data aleatoria usando faker
        final var errorMessageMap = Parser.getErrorMessageMap(); //leido de excel y convertido a map

        return new Object[][]{
                {"", user.getLastname(), user.getZipcode(), Parser.getErrorMessage(errorMessageMap, "error_name")},
                {user.getName(), "", user.getZipcode(), Parser.getErrorMessage(errorMessageMap, "error_lastname")},
                {user.getName(), user.getLastname(), "", Parser.getErrorMessage(errorMessageMap, "error_zipcode")}
        };
    }
}
