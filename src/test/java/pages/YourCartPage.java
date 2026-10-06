package pages;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utilities.BasePage;
import utilities.Gestures;
import utilities.Logs;

public class YourCartPage extends BasePage {
    private final By itemList = AppiumBy.accessibilityId("test-Item");
    private final By deleteButton = AppiumBy.accessibilityId("test-Delete");
    // "test-Cart Content" es el ScrollView del carrito (validado con el page source real).
    // El botón CHECKOUT queda fuera de pantalla, por lo que se hace scroll hasta encontrarlo.
    private final By checkoutButton = AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().description(\"test-Cart Content\"))" +
                    ".scrollIntoView(new UiSelector().description(\"test-CHECKOUT\"))");

    @Override
    @Step("Esperando que cargue la pantalla Your Cart")
    public void waitPageToLoad() {
        waitPage(itemList, this.getClass().getSimpleName());
    }

    @Override
    @Step("Verificando la pantalla Your Cart")
    public void verifyPage() {
        Logs.info("Verificando la pantalla Your Cart");
        softAssert().assertTrue(find(itemList).isDisplayed());
        softAssert().assertAll();
    }

    @Step("Eliminando un ítem de la lista por su índice")
    public void deleteItemFromList(int index) {
        Logs.info("Eliminando un ítem de la lista por su índice: %d", index);
        final var canvas = findAll(itemList).get(index);
        Gestures.swipeHorizontal(50,60,20, canvas);

        Logs.info("Haciendo clic en el botón Delete");
        Gestures.tap(find(deleteButton));

    }

    @Step("Haciendo clic en el botón Checkout")
    public void clickCheckout() {
        Logs.info("Haciendo clic en el botón Checkout");
        find(checkoutButton).click();
    }


}