package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {
    private static final int DRIVER_CREATION_ATTEMPTS = 2;

    private final boolean runServer = System.getenv("JOB_NAME") != null;

    public void buildDriver(){
        if(runServer){
            buildRemoteDriver();
        } else{
            buildLocalDriver();
        }

    }

    public void killDriver(){
        final var driverProvider = new DriverProvider();
        final var driver = driverProvider.get();

        if (driver == null) {
            Logs.debug("No hay driver que cerrar");
            return;
        }

        try {
            Logs.debug("Cerrando el driver");
            driver.quit();
        } catch (WebDriverException webDriverException) {
            Logs.error("Error al cerrar el driver: %s", webDriverException.getMessage());
        } finally {
            driverProvider.remove(); //evita que el driver quede asociado al hilo para el siguiente test
        }
    }

    private void buildLocalDriver(){
        try {

            final var appiumUrl = "http://127.0.0.1:4723/";
            final var desiredCapabilities = getDesiredLocalCapabilities();

            Logs.debug("Inicializando el driver");
            final var driver = createDriver(new URL(appiumUrl), desiredCapabilities);

            Logs.debug("Asignando el driver al driver provider");
            new DriverProvider().set(driver);

        } catch (MalformedURLException malformedURLException) {

            Logs.error("Error al inicializar el driver: %s", malformedURLException.getMessage());
            throw new RuntimeException(malformedURLException);
        }
    }

    /**
     * Reintenta solo la creacion de la sesion ante fallos de infraestructura
     * (sesion no creada o servidor Appium inalcanzable).
     */
    private AndroidDriver createDriver(URL appiumUrl, DesiredCapabilities capabilities) {
        for (var attempt = 1; ; attempt++) {
            try {
                return new AndroidDriver(appiumUrl, capabilities);
            } catch (SessionNotCreatedException | UnreachableBrowserException exception) {
                if (attempt >= DRIVER_CREATION_ATTEMPTS) {
                    throw exception;
                }
                Logs.warning("No se pudo crear la sesión (intento %d de %d): %s",
                        attempt, DRIVER_CREATION_ATTEMPTS, exception.getMessage());
            }
        }
    }

    private void buildRemoteDriver(){

    }

    private static DesiredCapabilities getDesiredLocalCapabilities() {

        final var desiredCapabilities = new DesiredCapabilities();

        final var fileAPK = new File("src/test/resources/apk/sauceLabs.apk");

        desiredCapabilities.setCapability("appium:autoGrantPermissions", true);
        // Evita que las animaciones de ventana del emulador hagan inestables esperas y gestos
        desiredCapabilities.setCapability("appium:disableWindowAnimation", true);
        desiredCapabilities.setCapability("appium:appWaitActivity", "com.swaglabsmobileapp.MainActivity");
        desiredCapabilities.setCapability("appium:platformName", "Android");
        desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        desiredCapabilities.setCapability("appium:app", fileAPK.getAbsolutePath());

        return desiredCapabilities;
    }
}
