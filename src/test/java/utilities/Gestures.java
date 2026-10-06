package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

public class Gestures {

    private static final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    private static AndroidDriver getDriver() {
        return new DriverProvider().get();

    }

    public static void tap(WebElement element) {
        final var puntoCentro = getCenterPoint(element);
        final var sequence = new Sequence(finger, 1);

        Logs.debug("Moviendo el dedo hacia el elemento");
        sequence.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(),
                        puntoCentro
                )
        );

        Logs.debug("Presionando el elemento");
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        Logs.debug("Esperando 1 segundo");
        sequence.addAction(new Pause(finger, Duration.ofMillis(1000)));

        Logs.debug("Dejando de presionar el elemento");
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getDriver().perform(List.of(sequence));
    }

    public static void longTap(WebElement element) {
        final var puntoCentro = getCenterPoint(element);
        final var sequence = new Sequence(finger, 1);

        Logs.debug("Moviendo el dedo hacia el elemento");
        sequence.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(),
                        puntoCentro
                )
        );

        Logs.debug("Presionando el elemento");
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        Logs.debug("Esperando 3.5 segundos");
        sequence.addAction(new Pause(finger, Duration.ofMillis(3500)));

        Logs.debug("Dejando de presionar el elemento");
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getDriver().perform(List.of(sequence));


    }

    public static void doubleTap(WebElement element) {
        final var puntoCentro = getCenterPoint(element);
        final var sequence = new Sequence(finger, 1);

        Logs.debug("Moviendo el dedo hacia el elemento");
        sequence.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(),
                        puntoCentro
                )
        );

        for (var i = 0; i < 2; i++) { //2 veces
            Logs.debug("Presionando el elemento");
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

            Logs.debug("Esperando 1 segundo");
            sequence.addAction(new Pause(finger, Duration.ofMillis(1000)));

            Logs.debug("Dejando de presionar el elemento");
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        }

        Logs.debug("Ejecutando las acciones");
        getDriver().perform(List.of(sequence));
    }

    public static void dragTo(WebElement elementOrigen, WebElement elementDestino) {
        final var centerPointOrigen = getCenterPoint(elementOrigen);
        final var centerPointDestino = getCenterPoint(elementDestino);
        final var sequence = new Sequence(finger, 1);

        //1. Movemos el dedo hacia el elemento origen
        sequence.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(500), //duración de la acción
                        PointerInput.Origin.viewport(), //área donde se hará la acción
                        centerPointOrigen //punto donde se hará la acción con respecto al canvas
                )
        );

        //2. Tocamos la pantalla bajando el dedo
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        //3. Agregamos una pequeña pausa
        sequence.addAction(new Pause(finger, Duration.ofMillis(2000)));

        //4. Arrastramos hacia el elemento destino
        sequence.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(1000), //duración de la acción
                        PointerInput.Origin.viewport(), //área donde se hará la acción
                        centerPointDestino //punto donde se hará la acción con respecto al canvas
                )
        );

        //5. Agregamos una pequeña pausa
        sequence.addAction(new Pause(finger, Duration.ofMillis(1500)));

        //6. Dejamos de tocar la pantalla levantando el dedo
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        //7. Finalmente, ejecutamos las acciones
        getDriver().perform(List.of(sequence));
    }


    public static void swipeGeneral(
            double porcentajeXInicial,
            double porcentajeYInicial,
            double porcentajeXFinal,
            double porcentajeYFinal,
            WebElement element
    ) {
        final var puntoInicial =
                getElementPointUsingPercentages(porcentajeXInicial, porcentajeYInicial, element);
        final var puntoFinal =
                getElementPointUsingPercentages(porcentajeXFinal, porcentajeYFinal, element);
        swipeGeneralPuntos(puntoInicial, puntoFinal);
    }

    public static void swipeHorizontal(
            double porcentajeY,
            double porcentajeXInicial,
            double porcentajeXFinal,
            WebElement element
    ) {
        swipeGeneral(porcentajeXInicial, porcentajeY, porcentajeXFinal, porcentajeY, element);
    }

    public static void swipeVertical(
            double porcentajeX,
            double porcentajeYInicial,
            double porcentajeYFinal,
            WebElement element
    ) {
        swipeGeneral(porcentajeX, porcentajeYInicial, porcentajeX, porcentajeYFinal, element);
    }

    private static void swipeGeneralPuntos(Point origen, Point destino) {
        Logs.debug("Haciendo swipe desde el punto %s hasta el punto %s", origen, destino);
        final var sequence = new Sequence(finger, 1);

        Logs.debug("Movemos el dedo hacia la posicion inicial");
        sequence.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        origen
                )
        );

        Logs.debug("Tocamos la pantalla en el punto de origen");
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        Logs.debug("Agregamos una breve  pausa");
        sequence.addAction(new Pause(finger, Duration.ofMillis(1000)));

        Logs.debug("Movemos el dedo hacia la posicion final");
        sequence.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(),
                        destino
                )
        );

        Logs.debug("Dejamos de tocar la pantalla en el punto de destino");
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Logs.debug("Ejecutando las acciones");
        getDriver().perform(List.of(sequence));


    }


    private static Point getCenterPoint(WebElement element) {
        final var ubicacionElemento = element.getLocation();
        final var tamanoElemento = element.getSize();

        final var centroX = ubicacionElemento.getX() + tamanoElemento.getWidth() / 2;
        final var centroY = ubicacionElemento.getY() + tamanoElemento.getHeight() / 2;

        return new Point(centroX, centroY);
    }

    private static Point getElementPointUsingPercentages(
            double percentageX,
            double percentageY,
            WebElement element
    ) {
        final var ubicacion = element.getLocation();
        final var tamano = element.getSize();

        final var xDelta = (percentageX / 100) * tamano.getWidth();
        final var yDelta = (percentageY / 100) * tamano.getHeight();

        final var x = (int) (ubicacion.getX() + xDelta);
        final var y = (int) (ubicacion.getY() + yDelta);

        return new Point(x, y);
    }

}
