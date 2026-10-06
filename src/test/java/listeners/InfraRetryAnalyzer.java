package listeners;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utilities.Logs;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Reintenta un test una sola vez (configurable con {@code -Dretry.max}) y unicamente
 * si fallo por infraestructura: sesion perdida o servidor Appium inalcanzable.
 * Los fallos de aserciones, locators o esperas nunca se reintentan para no ocultar defectos.
 */
public class InfraRetryAnalyzer implements IRetryAnalyzer {
    private static final int MAX_RETRIES = Integer.getInteger("retry.max", 1);
    private static final Map<String, Integer> ATTEMPTS = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        if (!isInfrastructureFailure(result.getThrowable())) {
            return false;
        }

        final var key = result.getMethod().getQualifiedName() + Arrays.toString(result.getParameters());
        final var attempt = ATTEMPTS.merge(key, 1, Integer::sum);
        if (attempt > MAX_RETRIES) {
            return false;
        }

        Logs.warning("Reintentando %s por fallo de infraestructura (%d de %d)",
                result.getName(), attempt, MAX_RETRIES);
        return true;
    }

    static boolean isInfrastructureFailure(Throwable throwable) {
        for (var current = throwable; current != null; current = current.getCause()) {
            if (current instanceof SessionNotCreatedException
                    || current instanceof NoSuchSessionException
                    || current instanceof UnreachableBrowserException
                    || current instanceof SocketException
                    || current instanceof SocketTimeoutException) {
                return true;
            }
            if (current.getCause() == current) {
                break;
            }
        }
        return false;
    }
}
