package utilities;

import io.appium.java_client.android.AndroidDriver;

public class ContextUtilities {

    private static AndroidDriver getDriver() {
        return new DriverProvider().get();
    }

    public static void switchWebContext() {
        final var driver = getDriver();
        final var setContext = driver.getContextHandles();
        Logs.debug("Set context: %s", setContext);

        for (var context : setContext) {
            if (context.contains("WEBVIEW")) {
                Logs.debug("Switching to context: %s", context);
                driver.context(context);
                break;
            }
        }
    }

    public static void switchNativeContext() {
        final var driver = getDriver();
        final var setContext = driver.getContextHandles();
        Logs.debug("Set context: %s", setContext);

        for (var context : setContext) {
            if (context.contains("NATIVE_APP")) {
                Logs.debug("Switching to context: %s", context);
                driver.context(context);
                break;
            }
        }

    }

}
