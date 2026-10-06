package saucedemo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.TopBar;
import pages.YourCartPage;
import utilities.BaseTest;
import utilities.Groups;

import java.util.List;


public class YourCartTests extends BaseTest {

    private final TopBar topBar = new TopBar();
    private final YourCartPage yourCartPage = new YourCartPage();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        commonFlows.goToYourCartPage(List.of(1, 2));
    }

    @Test(groups = {Groups.REGRESSION})
    public void deleteSwipeTest() {
        yourCartPage.deleteItemFromList(0);
        topBar.verifyItemCount(1);
    }

}