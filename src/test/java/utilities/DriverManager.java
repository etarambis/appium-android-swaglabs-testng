package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {
    private final boolean runServer = System.getenv("JOB_NAME") != null;

    public void buildDriver(){
        if(runServer){
            buildRemoteDriver();
        } else{
            buildLocalDriver();
        }

    }

    public void killDriver(){
        Logs.debug("Matando al driver");
        new DriverProvider().get().quit();
    }

    private void buildLocalDriver(){
        try {

            final var appiumUrl = "http://127.0.0.1:4723/";
            final var desiredCapabilities = getDesiredLocalCapabilities();

            Logs.debug("Inicializando el DRIVER");
            final var driver = new AndroidDriver(new URL(appiumUrl), desiredCapabilities);

            Logs.debug("Asignando el driver al driver provider");
            new DriverProvider().set(driver);

        } catch (MalformedURLException malformedURLException) {

            Logs.error("Error al inicializar el DRIVER: %s", malformedURLException.getMessage());
            throw new RuntimeException(malformedURLException);
        }
    }

    private void buildRemoteDriver(){

    }

    private static DesiredCapabilities getDesiredLocalCapabilities() {

        final var desiredCapabilities = new DesiredCapabilities();

        final var fileAPK = new File("src/test/resources/apk/sauceLabs.apk");

        desiredCapabilities.setCapability("appium:autoGrantPermissions", true);
        desiredCapabilities.setCapability("appium:appWaitActivity", "com.swaglabsmobileapp.MainActivity");
        desiredCapabilities.setCapability("appium:platformName", "Android");
        desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        desiredCapabilities.setCapability("appium:app", fileAPK.getAbsolutePath());

        return desiredCapabilities;
    }
}
