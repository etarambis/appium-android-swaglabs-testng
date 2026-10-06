package pages;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utilities.BasePage;
import utilities.Gestures;
import utilities.Logs;
import utilities.Timeouts;

public class ItemDetailPage extends BasePage {
    private final By backProductsButton = AppiumBy.accessibilityId("test-BACK TO PRODUCTS");
    private final By itemDescription = AppiumBy
            .xpath("//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView");
    private final By itemImage = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"test-Image Container\")" +
                    ".childSelector(new UiSelector().className(\"android.widget.ImageView\"))");
    private final By itemPrice = AppiumBy.accessibilityId("test-Price");
    private final By addCartButton = AppiumBy.accessibilityId("test-ADD TO CART");
    private final By canvas = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.ScrollView\")" +
                    ".description(\"test-Inventory item page\")");

    private WebElement getTitleElement() {
        return findAll(itemDescription).get(0);
    }

    private WebElement getDescriptionElement() {
        return findAll(itemDescription).get(1);
    }

    @Override
    @Step("Esperando que cargue la pantalla Item Detail")
    public void waitPageToLoad() {
        waitForDisplayed(canvas, Timeouts.SLOW_PAGE_WAIT);
        Logs.info("La pantalla Item Detail ha cargado satisfactoriamente");
    }

    @Override
    @Step("Verificando la pantalla Item Detail")
    public void verifyPage() {
        Logs.info("Verificando la pantalla Item Detail");

        softAssert().assertTrue(find(backProductsButton).isDisplayed());
        softAssert().assertTrue(find(itemImage).isDisplayed());
        softAssert().assertTrue(find(itemPrice).isDisplayed());
        softAssert().assertTrue(getTitleElement().isDisplayed());
        softAssert().assertTrue(getDescriptionElement().isDisplayed());
        softAssert().assertAll();

        Gestures.swipeVertical(50,70,30, find(canvas));
        Assert.assertTrue(find(addCartButton).isDisplayed(), "El botón de agregar al carrito no está visible");

    }

    @Step("Haciendo clic en Back to Products")
    public void clickBackProducts() {
        Logs.info("Haciendo clic en Back to Products");
        find(backProductsButton).click();
    }

    @Step("Verificando la información del ítem")
    public void verifyItemInfo(String itemName, double expectedPrice) {
        Logs.info("Verificando la información del ítem");

        final var priceDollar = find(itemPrice).getText();
        final var priceNoDollar = Double.parseDouble(priceDollar.replace("$", ""));

        softAssert().assertEquals(getTitleElement().getText(), itemName);
        softAssert().assertEquals(priceNoDollar, expectedPrice);
        softAssert().assertAll();
    }

}
