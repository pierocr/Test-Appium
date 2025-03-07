package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.qameta.allure.Allure; // <-- Se importa Allure para agregar capturas al reporte
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoginSteps {

    private static RemoteWebDriver driver;

    @Given("que la aplicación está instalada")
    public void laAplicacionEstaInstalada() throws MalformedURLException {

        limpiarDirectorioScreenshots(); //Limpia la carpeta screenshots antes de iniciar

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                // Ajusta la ruta local de tu APK
                .setApp(System.getProperty("user.dir")
                        + "/src/test/resources/app/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk")
                // Ajusta valores para que Appium no falle al ver la MainActivity
                .setAppPackage("com.swaglabsmobileapp")
                .setAppActivity("com.swaglabsmobileapp.MainActivity")
                .setAppWaitActivity("com.swaglabsmobileapp.SplashActivity,com.swaglabsmobileapp.MainActivity,*");

        // Si usas Appium 2.x, la URL es sin /wd/hub
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    @When("inicio la app e ingreso usuario {string} y contraseña {string}")
    public void inicioLaAppEIngresoUsuarioYPass(String usuario, String pass) {
        tomarScreenshot("antes_de_ingresar_credenciales", 2);

        if (!usuario.isEmpty()) {
            driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys(usuario);
        }
        if (!pass.isEmpty()) {
            driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys(pass);
        }

        tomarScreenshot("credenciales_ingresadas", 1);
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
    }

    @Then("el resultado del login debe ser {string}")
    public void elResultadoDelLoginDebeSer(String resultadoEsperado) {
        try {
            WebElement title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='PRODUCTS']"));
            if ("éxito".equals(resultadoEsperado)) {
                Assertions.assertEquals("PRODUCTS", title.getText());
            } else {
                Assertions.fail("El usuario debería haber fallado en el login");
            }
        } catch (Exception e) {
            if ("usuario bloqueado".equals(resultadoEsperado)) {
                WebElement mensajeError = driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'locked out')]"));
                Assertions.assertTrue(mensajeError.isDisplayed(), "Mensaje de usuario bloqueado no encontrado");
            } else if ("credenciales inválidas".equals(resultadoEsperado)) {
                WebElement mensajeError = driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Username and password do not match')]"));
                Assertions.assertTrue(mensajeError.isDisplayed(), "Mensaje de credenciales inválidas no encontrado");
            } else if ("campo usuario vacío".equals(resultadoEsperado)) {
                WebElement mensajeError = driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Username is required')]"));
                Assertions.assertTrue(mensajeError.isDisplayed(), "Mensaje de usuario vacío no encontrado");
            } else if ("campo contraseña vacío".equals(resultadoEsperado)) {
                WebElement mensajeError = driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Password is required')]"));
                Assertions.assertTrue(mensajeError.isDisplayed(), "Mensaje de contraseña vacía no encontrado");
            } else {
                Assertions.fail("El resultado esperado no se encuentra manejado: " + resultadoEsperado);
            }
        }

        tomarScreenshot("resultado_login", 1);
        driver.quit();
    }

    // Método actualizado para tomar screenshot con espera previa
    public static void tomarScreenshot(String nombreArchivo, int segundosEspera) {
        // Si se define un tiempo de espera antes de la captura, se ejecuta un delay
        if (segundosEspera > 0) {
            try {
                Thread.sleep(segundosEspera * 1000);
            } catch (InterruptedException e) {
                System.err.println("Error durante la espera: " + e.getMessage());
            }
        }

        // Tomar la captura de pantalla con Appium
        File srcFile = driver.getScreenshotAs(OutputType.FILE);
        String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String directorioEvidencias = "screenshots/"; // Directorio donde se guardarán los archivos
        String rutaArchivo = directorioEvidencias + nombreArchivo + "_" + fechaHora + ".png";

        try {
            FileUtils.copyFile(srcFile, new File(rutaArchivo)); // Guarda la captura en la carpeta 'screenshots'
            System.out.println("✅ Screenshot guardado en: " + rutaArchivo);

            // ⬇️ **Nuevo cambio: Agregar la captura al reporte Allure**
            try {
                Allure.addAttachment(nombreArchivo, new FileInputStream(rutaArchivo));
                System.out.println("📸 Screenshot agregado a Allure Report: " + nombreArchivo);
            } catch (FileNotFoundException e) {
                System.err.println("❌ Error agregando screenshot a Allure: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("❌ Error guardando el screenshot: " + e.getMessage());
        }
    }

    // Método para limpiar carpeta screenshots antes de ejecutar las pruebas
    public static void limpiarDirectorioScreenshots() {
        String directorioEvidencias = "screenshots/";
        try {
            FileUtils.cleanDirectory(new File(directorioEvidencias));
            System.out.println("✅ Carpeta screenshots limpiada correctamente.");
        } catch (Exception e) {
            System.err.println("Error limpiando el directorio screenshots: " + e.getMessage());
        }
    }

}
