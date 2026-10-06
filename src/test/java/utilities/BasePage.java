package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    private static final ThreadLocal<SoftAssert> SOFT_ASSERT = ThreadLocal.withInitial(SoftAssert::new);
    private final int timeOut;

    public BasePage(int timeOut) {
        this.timeOut = timeOut;
    }

    public BasePage() {
        this(Timeouts.DEFAULT_WAIT); //llamo al constructor de arriba con el default timeout
    }

    /**
     * SoftAssert del test en ejecucion. Se comparte entre las paginas del mismo hilo
     * y se reinicia antes de cada test con {@link #resetSoftAssert()}, porque un
     * assertAll() fallido conserva sus errores y contaminaria al test siguiente.
     */
    protected SoftAssert softAssert() {
        return SOFT_ASSERT.get();
    }

    public static void resetSoftAssert() {
        SOFT_ASSERT.remove();
    }

    protected AndroidDriver getDriver() {
        return new DriverProvider().get();
    }

    protected WebElement waitForDisplayed(By locator, int time) {
        final var wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForDisplayed(By locator) {
        return waitForDisplayed(locator, Timeouts.DEFAULT_WAIT);
    }

    protected void waitPage(By locator, String pageName) {
        Logs.info("Esperando que cargue la pantalla %s", pageName);
        waitForDisplayed(locator, timeOut);
        Logs.info("%s ha cargado satisfactoriamente", pageName);
    }

    protected WebElement find(By locator) {
        return getDriver().findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return getDriver().findElements(locator);
    }
    public void pressBack() {
        Logs.info("Presionando atrás en el dispositivo móvil");
        getDriver().pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public abstract void waitPageToLoad(); //esperar que cargue la pagina

    public abstract void verifyPage(); //verificar la UI de la pagina

}