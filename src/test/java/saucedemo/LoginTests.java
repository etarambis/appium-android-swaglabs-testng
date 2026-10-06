package saucedemo;

import data.CustomDataProviders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ShoppingPage;
import utilities.BaseTest;

public class LoginTests extends BaseTest {

    private final LoginPage loginPage = new LoginPage();
    private final ShoppingPage shoppingPage = new ShoppingPage();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        commonFlows.goToLoginPage();

    }

    @Test(
            groups = {regression, smoke},
            dataProviderClass = CustomDataProviders.class,
            dataProvider = CustomDataProviders.DP_CREDENTIALS
    )
    public void credentialsTest(String username, String password, String message) {
        loginPage.fillData(username, password);
        loginPage.verifyErrorMessage(message);
    }

    @Test(groups = {regression, smoke})
    public void verifyUITest() {
        loginPage.verifyPage();
    }

    @Test(groups = {regression})
    public void tapStandardUserTest() {
        loginPage.fillDataTap();
        shoppingPage.waitPageToLoad();
        shoppingPage.verifyPage();
    }

    @Test(groups = {regression})
    public void verifyCredentialsLabelsTest() {
        loginPage.verifyLabels();
    }

}
