# AppiumTestNG

Framework de automatización de pruebas móviles (Android) para la app **Swag Labs (Sauce Labs My Demo)**, construido con **Appium + Java + TestNG + Maven**. Usa el patrón *Page Object*, reportes con **Allure** y logging con **Log4j2**.

![Java](https://img.shields.io/badge/Java-17-orange)
![Appium Java Client](https://img.shields.io/badge/Appium%20Java%20Client-10.1.1-purple)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red)
![Allure](https://img.shields.io/badge/Allure-2.34.0-yellow)

---

## Tecnologías y versiones

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje (`maven.compiler.source/target`) |
| Maven (wrapper `mvnw`) | — | Build y ejecución |
| Appium Java Client | 10.1.1 | Control del dispositivo (`AndroidDriver`, `AppiumBy`) |
| Selenium | transitiva de `java-client` | `WebDriverWait`, `By`, `PointerInput`/`Sequence` |
| TestNG | 7.10.2 | Framework de pruebas, grupos y DataProviders |
| Allure TestNG | 2.34.0 | Reportes, `@Step` y `@Attachment` |
| allure-maven | 2.15.2 | Plugin `allure:serve` / `allure:report` |
| AspectJ Weaver | 1.9.24 | Agente Java requerido por `@Step`/`@Attachment` |
| Maven Surefire | 3.2.5 | Ejecución de tests (`testFailureIgnore=true`) |
| Log4j2 (core + api) | 2.26.1 | Logging (logger `AUTOMATION`) |
| Poiji | 5.4.0 | Lectura de Excel a objetos Java |
| Jackson Databind | 2.22.2 | Lectura de JSON |
| Datafaker | 2.7.0 | Datos aleatorios (nombre, apellido, código postal) |
| jsoup | 1.23.2 | Formateo del `pageSource` |

---

## Estructura del proyecto

```
AppiumTestNG/
├── pom.xml                      # Dependencias y plugins
├── mvnw / mvnw.cmd              # Maven Wrapper
├── runSuite.sh                  # Ejecuta el grupo regression
├── openAllure.sh                # Genera y abre el reporte Allure
├── .allure/                     # Allure CLI 2.30.0 local
├── src/
│   ├── main/                    # Vacío (sin código de producción)
│   └── test/
│       ├── java/
│       │   ├── data/            # Lectura de datos y DataProviders
│       │   ├── listeners/       # Listeners de TestNG y de Allure
│       │   ├── models/          # POJOs (Credential, ErrorMessage, User...)
│       │   ├── pages/           # Page Objects de cada pantalla
│       │   ├── saucedemo/       # Clases de test
│       │   └── utilities/       # Base, driver, flujos, gestos, logs, archivos
│       └── resources/
│           ├── apk/             # sauceLabs.apk
│           ├── data/            # credenciales.json y dataExcel.xlsx
│           ├── META-INF/services/  # Registro del listener de Allure
│           ├── screenshots/     # Evidencia local de tests fallidos
│           ├── pageStructure/   # XML del page source de tests fallidos
│           ├── logs/            # Carpeta de logs
│           └── allure.properties
└── target/                      # Salida de build, surefire y allure-results
```

| Paquete | Rol |
|---|---|
| `pages` | Un Page Object por pantalla: `LoginPage`, `ShoppingPage`, `ItemDetailPage`, `YourCartPage`, `YourInformationPage`, `TopBar`, `BurgerMenu`. Cada uno define sus locators y acciones con `@Step`. |
| `saucedemo` | Clases de test. Todas extienden `BaseTest`. |
| `utilities` | `BaseTest` (setup/teardown del driver y listeners), `BasePage` (esperas y helpers), `CommonFlows` (flujos reutilizables de navegación), `DriverManager`/`DriverProvider` (creación y `ThreadLocal` del driver), `Gestures` (tap, long tap, double tap, drag, swipe), `Deeplinks`, `ContextUtilities`, `FileManager` (screenshots y page source), `Logs`. |
| `data` | `DataGiver` (credenciales), `JsonReader`, `ExcelReader`, `Parser` y `CustomDataProviders`. |
| `models` | `Credential`, `CredentialJson`, `ErrorMessage` (Poiji), `User` (Datafaker). |
| `listeners` | `TestListeners` (ITestListener), `SuiteListeners` (ISuiteListener) y `AllureListeners` (adjunta evidencia en Allure). |

---

## Requisitos previos

1. **JDK 17** (`java -version`).
2. **Maven** (opcional: el proyecto incluye `mvnw`).
3. **Node.js** y **Appium 2 o 3** con el driver UiAutomator2:
   ```bash
   npm install -g appium
   appium driver install uiautomator2
   ```
4. **Android SDK** con `adb` y un **emulador (AVD)** o dispositivo físico, con la variable de entorno `ANDROID_HOME` configurada (y `platform-tools` en el `PATH`).
5. **APK** de la app en `src/test/resources/apk/sauceLabs.apk` (ya incluido en el repositorio).
6. Servidor de Appium **en ejecución** en `http://127.0.0.1:4723/` antes de lanzar los tests (el proyecto no lo inicia):
   ```bash
   appium
   ```

---

## Configuración

### Capabilities (ejecución local)
Definidas en `DriverManager.getDesiredLocalCapabilities()`:

| Capability | Valor |
|---|---|
| `appium:platformName` | `Android` |
| `appium:automationName` | `UiAutomator2` |
| `appium:app` | Ruta absoluta de `src/test/resources/apk/sauceLabs.apk` |
| `appium:appWaitActivity` | `com.swaglabsmobileapp.MainActivity` |
| `appium:autoGrantPermissions` | `true` |

No se define `deviceName`/`udid`: Appium usa el dispositivo o emulador conectado.

### Ejecución local vs remota (`JOB_NAME`)
`DriverManager` decide según la variable de entorno `JOB_NAME`:

- **No definida** → driver local contra `http://127.0.0.1:4723/`.
- **Definida** (p. ej. en un job de CI) → se invoca `buildRemoteDriver()`, que **actualmente está vacío** (sin implementar). Con `JOB_NAME` definida no se crea ningún driver.

### Datos de prueba
- `src/test/resources/data/credenciales.json`: credenciales `valid`, `locked` e `invalid` con su mensaje de error esperado. Se leen con `JsonReader` y se exponen con `DataGiver`.
- `src/test/resources/data/dataExcel.xlsx` (hoja `mensajes`, columnas `NOMBRE` y `MENSAJE`): mensajes de error del formulario *Your Information* (`error_name`, `error_lastname`, `error_zipcode`). Se leen con Poiji (`ExcelReader` → `Parser`).
- `models.User` genera nombre, apellido y código postal aleatorios con Datafaker.

### Allure
`src/test/resources/allure.properties` define `allure.results.directory=target/allure-results` y los patrones de enlaces `issue`/`tms` hacia Trello.

---

## Cómo ejecutar

> Con el emulador iniciado y Appium corriendo. Ejecutar desde la carpeta del proyecto (donde está `pom.xml`).

| Objetivo | Comando |
|---|---|
| Toda la suite | `./mvnw clean test` |
| Grupo `regression` | `./mvnw clean test -Dgroups="regression"` |
| Grupo `smoke` | `./mvnw clean test -Dgroups="smoke"` |
| Una clase | `./mvnw clean test -Dtest=LoginTests` |
| Un método | `./mvnw clean test -Dtest=LoginTests#verifyUITest` |
| Varios métodos | `./mvnw clean test -Dtest="LoginTests#verifyUITest+tapStandardUserTest"` |
| Clase + grupo | `./mvnw clean test -Dtest=LoginTests -Dgroups="smoke"` |
| Script del proyecto | `./runSuite.sh` (equivale a `./mvnw clean test -Dgroups="regression"`) |

En Windows usar `mvnw.cmd` en lugar de `./mvnw`, o ejecutar los `.sh` desde Git Bash. No existe un `testng.xml`: Surefire descubre las clases automáticamente.

> `testFailureIgnore=true` está activo: el build termina en `SUCCESS` aunque haya tests fallidos. Revisa siempre el resumen o el reporte.

---

## Reporte de Allure

Tras ejecutar los tests, los resultados quedan en `target/allure-results`.

```bash
./mvnw allure:serve      # genera y abre el reporte en el navegador (igual que ./openAllure.sh)
./mvnw allure:report     # solo genera el reporte en target/site/allure-maven-plugin
```

En los tests fallidos se adjuntan automáticamente un *screenshot* y el *page source* (`AllureListeners`). El proyecto también incluye el Allure CLI 2.30.0 en `.allure/`.

---

## Tests por clase

Los grupos disponibles son `regression` y `smoke`.

| Clase | Test | Cobertura | Grupos |
|---|---|---|---|
| `LoginTests` | `credentialsTest` (DataProvider: invalid y locked) | Mensajes de error con credenciales inválidas y usuario bloqueado | regression, smoke |
| | `verifyUITest` | Elementos de la pantalla de login | regression, smoke |
| | `tapStandardUserTest` | Login tocando el label `standard_user` | regression |
| | `verifyCredentialsLabelsTest` | Labels de usuarios y contraseña visibles | regression |
| `ShoppingTests` | `verifyUITest` | UI de la pantalla de productos | regression, smoke |
| | `dragDropItemCartTest` | Arrastrar 3 productos al carrito y validar contador | regression |
| `ItemDetailTests` | `verifyUITest` | UI del detalle del producto | regression, smoke |
| | `backProductsTest` | Botón *Back to products* | regression |
| | `pressBackTest` | Botón *Back* del dispositivo | regression |
| `YourCartTests` | `deleteSwipeTest` | Eliminar un ítem con swipe y validar contador | regression |
| `YourInformationTests` | `errorMessagesTest` (DataProvider: 3 casos) | Errores por nombre, apellido o código postal vacíos | regression |
| `BurgerMenuTests` | `logoutTest` | Logout desde el menú | regression, smoke |
| | `closeButtonTest` | Cerrar el menú con la X | regression |
| `DeeplinkTests` | `itemDetailDeeplinkTest` | Deeplink al detalle (Sauce Labs Onesie, $7.99) | regression |
| | `shoppingDeeplinkTest` | Deeplink a productos con 2 ítems en el carrito | regression |

Total: 15 métodos, 18 ejecuciones contando los DataProviders.

---

## Problemas conocidos

Última ejecución registrada en `target/surefire-reports`: 18 tests, 13 pasados, 1 fallido y 4 omitidos.

- **`ItemDetailTests`**: inestable. El `@BeforeMethod` (`goToItemDetailPage`) depende de tocar la imagen por índice y de una espera de 20 s por la pantalla de detalle; cuando falla, se omiten los tests de la clase. `DeeplinkTests.itemDetailDeeplinkTest` también ha fallado esperando esa misma pantalla.
- **`YourInformationTests`**: inestable. Su `setUp` recorre login, arrastre de ítems al carrito y checkout; los arrastres (`Gestures.dragTo`) son sensibles al rendimiento del emulador y al estado de la lista.
- `DriverManager.buildRemoteDriver()` no está implementado.
- No hay archivo de configuración de Log4j2 en el proyecto, por lo que se aplica la configuración por defecto de Log4j2.

## Solución de problemas

| Síntoma | Qué hacer |
|---|---|
| Emulador `offline` o no aparece | `adb kill-server && adb start-server && adb devices`. Si persiste, reinicia el AVD con **Cold Boot Now** desde el Device Manager de Android Studio. |
| `adb` no se reconoce | Verifica `ANDROID_HOME` y que `%ANDROID_HOME%\platform-tools` esté en el `PATH`. |
| `Connection refused` a `127.0.0.1:4723` | Appium no está corriendo. Ejecuta `appium`. |
| Puerto 4723 ocupado | Windows: `netstat -ano \| findstr :4723` y luego `taskkill /PID <pid> /F`. O inicia Appium en otro puerto y ajusta `appiumUrl` en `DriverManager`. |
| `Could not find a driver for automationName 'UiAutomator2'` | `appium driver install uiautomator2`. |
| Los `@Step` o adjuntos no aparecen en Allure | Ejecuta con Maven (el `argLine` de Surefire carga el agente AspectJ); evita correr desde el IDE sin ese agente. |
| La app no se instala | Confirma que existe `src/test/resources/apk/sauceLabs.apk` y que ejecutas desde la carpeta que contiene `pom.xml` (la ruta del APK es relativa). |

---

## Buenas prácticas

- Un Page Object por pantalla; los tests **no** usan locators directamente.
- Los locators se declaran como `private final By` al inicio de la página.
- Prefiere `AppiumBy.accessibilityId` (la app expone ids `test-*`) y evita XPath cuando sea posible.
- Usa esperas explícitas (`waitForDisplayed`, `waitPage`) en lugar de `sleep`.
- Cada acción pública de una página lleva `@Step` y `Logs.info(...)`.
- Reutiliza flujos de navegación en `CommonFlows`; no repitas el login en cada test.
- Los datos viven en `resources/data` (JSON/Excel) o se generan con Datafaker; no los dejes escritos en los tests.
- Cada test debe ser independiente: el driver se crea y cierra por método en `BaseTest`.
- Etiqueta cada test con `groups` (`regression`, `smoke`).

## Cómo agregar una nueva página

1. Crea `src/test/java/pages/MiPagina.java` extendiendo `BasePage`.
2. Declara los locators e implementa `waitPageToLoad()` y `verifyPage()`:
   ```java
   public class MiPagina extends BasePage {
       private final By titulo = AppiumBy.accessibilityId("test-Titulo");

       @Override
       @Step("Esperando que la pagina Mi Pagina cargue")
       public void waitPageToLoad() {
           waitPage(titulo, this.getClass().getSimpleName());
       }

       @Override
       @Step("Verificando la pagina Mi Pagina")
       public void verifyPage() {
           softAssert.assertTrue(find(titulo).isDisplayed());
           softAssert.assertAll();
       }
   }
   ```
3. Si se llega a ella desde un flujo común, agrega un método en `CommonFlows` (por ejemplo `goToMiPagina()`).

## Cómo agregar un nuevo test

1. Crea la clase en `src/test/java/saucedemo/` extendiendo `BaseTest`.
2. Usa un `@BeforeMethod` con el flujo de `commonFlows` necesario y escribe los tests con sus grupos:
   ```java
   public class MiPaginaTests extends BaseTest {
       private final MiPagina miPagina = new MiPagina();

       @BeforeMethod(alwaysRun = true)
       public void setUp() {
           commonFlows.goToMiPagina();
       }

       @Test(groups = {regression})
       public void verifyUITest() {
           miPagina.verifyPage();
       }
   }
   ```
3. Si necesita datos parametrizados, agrega un `@DataProvider` en `CustomDataProviders`.
4. Ejecútalo: `./mvnw clean test -Dtest=MiPaginaTests`.
