package utilities;

/**
 * Timeouts de espera, en segundos, centralizados.
 * Cada valor puede sobrescribirse por linea de comandos, por ejemplo
 * {@code -Dtimeout.default=10}.
 */
public final class Timeouts {
    /** Espera por defecto para elementos y pantallas. */
    public static final int DEFAULT_WAIT = read("timeout.default", 5);
    /** Espera de mensajes de error que aparecen tras una acción. */
    public static final int ERROR_MESSAGE_WAIT = read("timeout.errorMessage", 3);
    /** Espera de pantallas que tardan mas en cargar (detalle de item). */
    public static final int SLOW_PAGE_WAIT = read("timeout.slowPage", 20);

    private Timeouts() {
    }

    private static int read(String property, int defaultValue) {
        return Integer.getInteger(property, defaultValue);
    }
}
