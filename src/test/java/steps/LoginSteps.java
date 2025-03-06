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

public class LoginSteps {

    private static RemoteWebDriver driver;

    @Given("que la aplicación está instalada")
    public void laAplicacionEstaInstalada() throws MalformedURLException {
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

    @When("inicio la app e ingreso credenciales válidas")
    public void inicioLaAppEIngresoCredenciales() {
        WebElement username = driver.findElement(AppiumBy.accessibilityId("test-Username"));
        WebElement password = driver.findElement(AppiumBy.accessibilityId("test-Password"));
        WebElement loginBtn = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginBtn.click();
    }

    @Then("debo ver la pantalla principal")
    public void deboVerLaPantallaPrincipal() {
        WebElement title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='PRODUCTS']"));
        Assertions.assertEquals("PRODUCTS", title.getText());
        driver.quit();
    }
}
