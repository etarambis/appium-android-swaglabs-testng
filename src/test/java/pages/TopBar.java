package pages;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import utilities.BasePage;
import utilities.Logs;

public class TopBar extends BasePage {
    private final By burgerButton = AppiumBy.accessibilityId("test-Menu");
    private final By checkoutButton = AppiumBy.accessibilityId("test-Cart");
    private final By itemCartCountLabel = AppiumBy.androidUIAutomator(
            "new UiSelector().description(\"test-Cart\").childSelector(new UiSelector().className(\"android.widget.TextView\"))");

    @Override
    @Step("Esperando que la barra superior cargue")
    public void waitPageToLoad() {
        waitPage(burgerButton, this.getClass().getSimpleName());
    }

    @Override
    @Step("Verificando la barra superior")
    public void verifyPage() {
        Logs.info("Verificando la barra superior");
        softAssert.assertTrue(find(burgerButton).isDisplayed());
        softAssert.assertTrue(find(checkoutButton).isDisplayed());
        softAssert.assertAll();
    }

    @Step("Abriendo el burger menu")
    public void openBurgerMenu() {
        Logs.info("Abriendo el burger menu");
        find(burgerButton).click();
    }

    @Step("Verificando la cantidad de items en el carrito")
    public void verifyItemCount(int expected) {
        Logs.info("Verificando la cantidad de items en el carrito: %d", expected);
        Assert.assertEquals(Integer.parseInt(find(itemCartCountLabel).getText()), expected);
    }

    @Step("Haciendo click en checkout")
    public void clickCheckout() {
        Logs.info("Haciendo click en checkout");
        find(checkoutButton).click();
    }

}