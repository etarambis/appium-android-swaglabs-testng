package saucedemo;

import data.CustomDataProviders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ShoppingPage;
import utilities.BaseTest;
import utilities.Groups;

public class LoginTests extends BaseTest {

    private final LoginPage loginPage = new LoginPage();
    private final ShoppingPage shoppingPage = new ShoppingPage();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        commonFlows.goToLoginPage();

    }

    @Test(
            groups = {Groups.REGRESSION, Groups.SMOKE},
            dataProviderClass = CustomDataProviders.class,
            dataProvider = CustomDataProviders.DP_CREDENTIALS
    )
    public void credentialsTest(String username, String password, String message) {
        loginPage.fillData(username, password);
        loginPage.verifyErrorMessage(message);
    }

    @Test(groups = {Groups.REGRESSION, Groups.SMOKE})
    public void verifyUITest() {
        loginPage.verifyPage();
    }

    @Test(groups = {Groups.REGRESSION})
    public void tapStandardUserTest() {
        loginPage.fillDataTap();
        shoppingPage.waitPageToLoad();
        shoppingPage.verifyPage();
    }

    @Test(groups = {Groups.REGRESSION})
    public void verifyCredentialsLabelsTest() {
        loginPage.verifyLabels();
    }

}
