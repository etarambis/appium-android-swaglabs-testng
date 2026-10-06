package utilities;

import io.appium.java_client.android.AndroidDriver;

public class DriverProvider {

    private static final ThreadLocal<AndroidDriver> ThreadLocal = new ThreadLocal<>();

    public void set(AndroidDriver driver) {
        ThreadLocal.set(driver);
    }


    public AndroidDriver get() {
        return ThreadLocal.get();
    }

    public void remove() {
        ThreadLocal.remove();
    }

}
