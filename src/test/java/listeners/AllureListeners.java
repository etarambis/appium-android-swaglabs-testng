package listeners;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import utilities.DriverProvider;
import utilities.FileManager;
import utilities.Logs;

public class AllureListeners implements TestLifecycleListener {
    @Override
    public void beforeTestStop(TestResult result) {

        Logs.debug("Tomando evidencias para Allure del test: %s", result.getName());
        final var status = result.getStatus();

        switch (status) {
            case BROKEN, FAILED -> {
                if (new DriverProvider().get() != null) {
                    FileManager.getScreenshot();
                    FileManager.getPageSource();
                }
            }
        }
    }
}
