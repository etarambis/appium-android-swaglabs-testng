package saucedemo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ItemDetailPage;
import pages.ShoppingPage;
import utilities.BaseTest;
import utilities.Groups;

public class ItemDetailTests extends BaseTest {
    private final ShoppingPage shoppingPage = new ShoppingPage();
    private final ItemDetailPage itemDetailPage = new ItemDetailPage();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        commonFlows.goToItemDetailPage(0);
    }

    @Test(groups = {Groups.REGRESSION, Groups.SMOKE})
    public void verifyUITest() {
        itemDetailPage.verifyPage();
    }

    @Test(groups = {Groups.REGRESSION})
    public void backProductsTest() {
        itemDetailPage.clickBackProducts();
        shoppingPage.waitPageToLoad();
        shoppingPage.verifyPage();
    }

    @Test(groups = {Groups.REGRESSION})
    public void pressBackTest() {
        itemDetailPage.pressBack();
        shoppingPage.waitPageToLoad();
        shoppingPage.verifyPage();
    }

}
