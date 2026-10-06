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
    // El boton CHECKOUT queda fuera de pantalla, por lo que se hace scroll hasta encontrarlo.
    private final By checkoutButton = AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().description(\"test-Cart Content\"))" +
                    ".scrollIntoView(new UiSelector().description(\"test-CHECKOUT\"))");

    @Override
    @Step("Esperando que la pagina de your cart cargue")
    public void waitPageToLoad() {
        waitPage(itemList, this.getClass().getSimpleName());
    }

    @Override
    @Step("Verificando la pagina de your cart")
    public void verifyPage() {
        Logs.info("Verificando la pagina de your cart");
        softAssert().assertTrue(find(itemList).isDisplayed());
        softAssert().assertAll();
    }

    @Step("Eliminando un item de la lista por su index")
    public void deleteItemFromList(int index) {
        Logs.info("Eliminando un item de la lista por su index: %d", index);
        final var canvas = findAll(itemList).get(index);
        Gestures.swipeHorizontal(50,60,20, canvas);

        Logs.info("Haciendo click en el boton de eliminar");
        Gestures.tap(find(deleteButton));

    }

    @Step("Haciendo click en el boton de checkout")
    public void clickCheckout() {
        Logs.info("Haciendo click en el boton de checkout");
        find(checkoutButton).click();
    }


}