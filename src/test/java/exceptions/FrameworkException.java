package exceptions;

/**
 * Error no recuperable del framework de pruebas (configuracion, datos o evidencia).
 * Siempre lleva un mensaje con el contexto necesario (ruta, nombre, URL) para diagnosticar el fallo.
 */
public class FrameworkException extends RuntimeException {

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
