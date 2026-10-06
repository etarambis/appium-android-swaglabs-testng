package saucedemo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ShoppingPage;
import pages.TopBar;
import utilities.BaseTest;
import utilities.Groups;


public class ShoppingTests extends BaseTest {

    private final ShoppingPage shoppingPage = new ShoppingPage();
    private final TopBar topBar = new TopBar();


    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        commonFlows.goToShoppingPage();
    }

    @Test(groups = {Groups.REGRESSION, Groups.SMOKE})
    public void verifyUITest() {
        shoppingPage.verifyPage();
    }

    @Test(groups = {Groups.REGRESSION})
    public void dragDropItemCartTest() {
        shoppingPage.changeViewMode();
        shoppingPage.addToCartDrag(3);
        topBar.verifyItemCount(3);
    }

}