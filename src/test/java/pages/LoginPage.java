package pages;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utilities.BasePage;
import utilities.Gestures;
import utilities.Logs;
import utilities.Timeouts;

public class LoginPage extends BasePage {
    private final By usernameInput = AppiumBy.accessibilityId("test-Username");
    private final By passwordInput = AppiumBy.accessibilityId("test-Password");
    private final By loginButton   = AppiumBy.accessibilityId("test-LOGIN");
    private final By errorMessage  = AppiumBy.androidUIAutomator("new UiSelector().description(\"test-Error message\")" +
            ".childSelector(new UiSelector().className(\"android.widget.TextView\"))");
    private final By standardUserLabel = AppiumBy.accessibilityId("test-standard_user");
    private final By lockedUserLabel = AppiumBy.accessibilityId("test-locked_out_user");
    private final By problemUserLabel = AppiumBy.accessibilityId("test-problem_user");
    private final By passwordLabel = AppiumBy.androidUIAutomator("new UiSelector().text(\"secret_sauce\")");
    private final By mainCanvas = AppiumBy.id("android:id/content");




    @Override
    @Step("Esperando que cargue la pagina de Login")
    public void waitPageToLoad() {
        waitPage(usernameInput, this.getClass().getSimpleName());
    }

    @Override
    @Step("Verificando la pagina de login")
    public void verifyPage() {
        Logs.info("Verificando la pagina de login");
        softAssert.assertTrue(find(usernameInput).isDisplayed());
        softAssert.assertTrue(find(passwordInput).isDisplayed());
        softAssert.assertTrue(find(loginButton).isDisplayed());
        softAssert.assertAll();
    }

    @Step("Rellenando el formulario de login")
    public void fillData(String username, String password) {
        Logs.info("Escribiendo el username");
        find(usernameInput).sendKeys(username);

        Logs.info("Escribiendo el password");
        find(passwordInput).sendKeys(password);

        Logs.info("Haciendo click en el boton de login");
        find(loginButton).click();
    }

    @Step("Rellenando formulario login con tap")
    public void fillDataTap() {
        Logs.info("Haciendo tap en el label de standard_user");
        Gestures.tap(find(standardUserLabel));

        Logs.info("Haciendo click en login");
        find(loginButton).click();
    }

    @Step("Verificando el mensaje de error")
    public void verifyErrorMessage(String errorText) {
        Logs.info("Esperando que el mensaje de error aparezca");
        final var errorMessageElement = waitForDisplayed(errorMessage, Timeouts.ERROR_MESSAGE_WAIT);

        Logs.info("Verificando el mensaje de error");
        softAssert.assertTrue(errorMessageElement.isDisplayed());
        softAssert.assertEquals(errorMessageElement.getText(), errorText);
        softAssert.assertAll();
    }

    @Step("Verificando los labels de credenciales")
    public void verifyLabels() {
        Logs.info("Verificando los labels de credenciales");
        Gestures.swipeVertical(50, 50, 20, find(mainCanvas));

        softAssert.assertTrue(find(standardUserLabel).isDisplayed());
        softAssert.assertTrue(find(lockedUserLabel).isDisplayed());
        softAssert.assertTrue(find(problemUserLabel).isDisplayed());
        softAssert.assertTrue(find(passwordLabel).isDisplayed());
        softAssert.assertAll();
    }

}
