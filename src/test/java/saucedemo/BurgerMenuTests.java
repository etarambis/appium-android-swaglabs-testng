package saucedemo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BurgerMenu;
import pages.LoginPage;
import pages.ShoppingPage;
import pages.TopBar;
import utilities.BaseTest;

public class BurgerMenuTests extends BaseTest {
    private final LoginPage loginPage = new LoginPage();
    private final ShoppingPage shoppingPage = new ShoppingPage();
    private final BurgerMenu burgerMenu = new BurgerMenu();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        commonFlows.openBurgerMenu();
    }

    @Test(groups = {regression, smoke})
    public void logoutTest() {
        burgerMenu.clickLogout();
        loginPage.waitPageToLoad();
    }

    @Test(groups = {regression})
    public void closeButtonTest() {
        burgerMenu.clickCloseX();
        shoppingPage.waitPageToLoad();
        shoppingPage.verifyPage();
    }
}
