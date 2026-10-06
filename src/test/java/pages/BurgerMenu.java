package pages;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utilities.BasePage;
import utilities.Logs;

public class BurgerMenu extends BasePage {
    private final By logoutButton = AppiumBy.accessibilityId("test-LOGOUT");
    private final By closeButton  = AppiumBy.accessibilityId("test-Close");

    @Override
    @Step("Esperando que cargue la pantalla Burger Menu")
    public void waitPageToLoad() {
        waitPage(logoutButton, this.getClass().getSimpleName());
    }

    @Override
    @Step("Verificando la pantalla Burger Menu")
    public void verifyPage() {
        Logs.info("Verificando la pantalla Burger Menu");
        softAssert().assertTrue(find(logoutButton).isDisplayed());
        softAssert().assertAll();
    }

    @Step("Haciendo clic en el botón Logout")
    public void clickLogout(){
        Logs.info("Haciendo clic en el botón Logout");
        find(logoutButton).click();
    }

    @Step("Haciendo clic en la X de cierre")
    public void clickCloseX(){
        Logs.info("Haciendo clic en la X de cierre");
        find(closeButton).click();
    }

}