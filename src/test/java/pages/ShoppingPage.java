package pages;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import utilities.BasePage;
import utilities.Gestures;
import utilities.Logs;

import java.util.List;

public class ShoppingPage extends BasePage {
    private final By title = AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")");
    private final By filterButton = AppiumBy.accessibilityId("test-Modal Selector Button");
    private final By toggleViewButton = AppiumBy.accessibilityId("test-Toggle");
    private final By dropZone = AppiumBy.accessibilityId("test-Cart drop zone");

    //List
    private final By itemList = AppiumBy.accessibilityId("test-PRODUCTS");
    private final By imageList = AppiumBy.androidUIAutomator(
            "new UiSelector().description(\"test-Item\").childSelector(new UiSelector().className(\"android.widget.ImageView\"))");
    private final By handleList = AppiumBy.accessibilityId("test-Drag Handle");


    @Override
    @Step("Esperando que cargue la pantalla Shopping")
    public void waitPageToLoad() {
        waitPage(title, this.getClass().getSimpleName());
    }

    @Override
    @Step("Verificando la pantalla Shopping")
    public void verifyPage() {
        Logs.info("Verificando la pantalla Shopping");
        softAssert().assertTrue(find(title).isDisplayed());
        softAssert().assertTrue(find(filterButton).isDisplayed());
        softAssert().assertTrue(find(toggleViewButton).isDisplayed());
        softAssert().assertTrue(find(itemList).isDisplayed());
        softAssert().assertAll();
    }

    @Step("Haciendo clic en la imagen del ítem según su índice")
    public void clickItemImage(int index) {
        Logs.info("Haciendo clic en la imagen del ítem según su índice");
        final var elements = findAll(imageList);
        Logs.info("Cantidad de imágenes encontradas: %d", elements.size());
        elements.get(index).click();
    }

    @Step("Cambiando a modo lista")
    public void changeViewMode() {
        Logs.info("Cambiando a modo lista");
        find(toggleViewButton).click();

        // En modo lista aparecen los drag handles que usa addToCartDrag.
        // Supuesto pendiente de validar con Appium Inspector.
        Logs.info("Esperando que se ordene en formato lista");
        waitForDisplayed(handleList);
    }

    @Step("Arrastrando ítems hacia la barra para agregar al carrito según cantidad")
    public void addToCartDrag(int cantidad) {
        Logs.info("Arrastrando ítems hacia la barra para agregar al carrito según cantidad");
        for (int i = 0; i < cantidad; i++) {
            dragFirstItemToCart();
        }
    }

    // La lista se vuelve a renderizar después de cada arrastre, por eso se
    // buscan los elementos en cada intento y se reintenta ante un stale.
    private void dragFirstItemToCart() {
        final var maxAttempts = 3;
        for (int attempt = 1; ; attempt++) {
            try {
                final var originElement = findAll(handleList).get(0);
                final var destinyElement = find(dropZone);
                Gestures.dragTo(originElement, destinyElement);
                return;
            } catch (StaleElementReferenceException e) {
                if (attempt == maxAttempts) {
                    throw e;
                }
                Logs.info("Elemento obsoleto, reintentando arrastre (intento %d)", attempt + 1);
            }
        }
    }

    /**
     * Agrega al carrito tantos items como elementos tenga la lista.
     * Solo se usa la cantidad ({@code itemListAdd.size()}): los valores de la lista
     * no seleccionan items concretos; siempre se arrastra el primer item visible.
     */
    @Step("Arrastrando ítems hacia la barra para agregar al carrito según lista")
    public void addToCartDrag(List<Integer> itemListAdd) {
        addToCartDrag(itemListAdd.size());
    }

}
