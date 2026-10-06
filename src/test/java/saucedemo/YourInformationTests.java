package saucedemo;

import data.CustomDataProviders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.YourInformationPage;
import utilities.BaseTest;
import utilities.Groups;

import java.util.List;

public class YourInformationTests extends BaseTest {

    private  final YourInformationPage yourInformationPage = new YourInformationPage();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        commonFlows.goToYourInformationPage(List.of(1, 2));
    }

    @Test(
            groups = {Groups.REGRESSION},
            dataProviderClass = CustomDataProviders.class,
            dataProvider = CustomDataProviders.DP_ERROR_MESSAGE
    )
    public void errorMessagesTest(
            String name,
            String lastname,
            String zipcode,
            String errorMessage
    ) {
        yourInformationPage.fillData(name, lastname, zipcode);
        yourInformationPage.verifyErrorMessage(errorMessage);
    }
}