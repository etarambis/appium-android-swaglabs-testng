package pages;

import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utilities.BasePage;
import utilities.Logs;

public class YourInformationPage extends BasePage {

    private final By firstNameInput = AppiumBy.accessibilityId("test-First Name");
    private final By lastNameInput = AppiumBy.accessibilityId("test-Last Name");
    private final By zipcodeInput = AppiumBy.accessibilityId("test-Zip/Postal Code");
    private final By continueButton = AppiumBy.accessibilityId("test-CONTINUE");
    private final By errorLabel = AppiumBy.androidUIAutomator(
            "description(\"test-Error message\").childSelector(className(\"android.widget.TextView\"))");

    @Override
    @Step("Esperando que la pagina de your information page cargue")
    public void waitPageToLoad() {
        waitPage(firstNameInput, this.getClass().getSimpleName());
    }

    @Override
    @Step("Verificando la pagina de your information page")
    public void verifyPage() {
        Logs.info("Verificando la pagina de your information page");

        softAssert.assertTrue(find(firstNameInput).isDisplayed());
        softAssert.assertTrue(find(lastNameInput).isDisplayed());
        softAssert.assertTrue(find(zipcodeInput).isDisplayed());
        softAssert.assertTrue(find(continueButton).isDisplayed());
        softAssert.assertAll();
    }

    @Step("Rellenando el formulario")
    public void fillData(String name, String lastname, String zipcode) {
        if (!name.isEmpty()) { //si el name es vacio no se escribe
            Logs.info("Escribiendo el name");
            find(firstNameInput).sendKeys(name);
        }
        if (!lastname.isEmpty()) { //si el lastname es vacio no se escribe
            Logs.info("Escribiendo el lastname");
            find(lastNameInput).sendKeys(lastname);
        }
        if (!zipcode.isEmpty()) { //si el zipcode es vacio no se escribe
            Logs.info("Escribiendo el zipcode");
            find(zipcodeInput).sendKeys(zipcode);
        }

        Logs.info("Haciendo click en el boton de continue");
        find(continueButton).click();
    }

    @Step("Verificando el mensaje de error")
    public void verifyErrorMessage(String errorMessage) {
        Logs.info("Verificando el mensaje de error");
        final var errorLabelElement = find(errorLabel);

        softAssert.assertTrue(errorLabelElement.isDisplayed());
        softAssert.assertEquals(errorLabelElement.getText(), errorMessage);
        softAssert.assertAll();
    }
}