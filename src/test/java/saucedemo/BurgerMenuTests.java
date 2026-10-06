package saucedemo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BurgerMenu;
import pages.LoginPage;
import pages.ShoppingPage;
import utilities.BaseTest;
import utilities.Groups;

public class BurgerMenuTests extends BaseTest {
    private final LoginPage loginPage = new LoginPage();
    private final ShoppingPage shoppingPage = new ShoppingPage();
    private final BurgerMenu burgerMenu = new BurgerMenu();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        commonFlows.openBurgerMenu();
    }

    @Test(groups = {Groups.REGRESSION, Groups.SMOKE})
    public void logoutTest() {
        burgerMenu.clickLogout();
        loginPage.waitPageToLoad();
    }

    @Test(groups = {Groups.REGRESSION})
    public void closeButtonTest() {
        burgerMenu.clickCloseX();
        shoppingPage.waitPageToLoad();
        shoppingPage.verifyPage();
    }
}
