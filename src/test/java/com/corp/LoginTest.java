package com.corp;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.corp.EnvironmentDetector.JNKENV;

public class LoginTest {

    public WebDriver driver;
    public  LoginPage page;

    @BeforeTest
    public  void setProp(){
        System.setProperty("webdriver.chrome.driver", "/home/mknysh/drivers/chromedriver");
    }

    @BeforeMethod()
    public  void setup() throws MalformedURLException {


        if(JNKENV) {

            // if env = jenkins run test in remote browser, use selenoid

            // Selenoid options
            String SelenoidIP = "10.8.0.46";
            int SelenoidPort = 4444;
            Map<String, Object> SelenoidOptions = new HashMap<>();
            SelenoidOptions.put("enableVNC", true);
            SelenoidOptions.put("enableVideo", true);

            // Desired capabilities
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");
            capabilities.setCapability("browserVersion", "95.0");
            capabilities.setCapability("selenoid:options", SelenoidOptions);

            driver = new EventFiringWebDriver(new RemoteWebDriver(
                    URI.create(String.format("http://%s:%d/wd/hub", SelenoidIP, SelenoidPort)).toURL(), capabilities))
                    .register(new Listener());
        }
        else{
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        page = new LoginPage(driver);
        page.openPage(page.URL);

    }

    @Test(description = "Login with valid creeds")
    void loginWithValidKeys(){

        page.loginWithValidCreeds();

    }

    @Test(description = "Login with invalid creeds")
    void loginWithInvalidKeys(){

        page.loginWithInvalidCreeds();

        Allure.addAttachment("Any text", new ByteArrayInputStream(((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES)));


    }

    @AfterMethod(description = "browser teardown")
    public void tearDown() {

        Allure.addAttachment("Any text", new ByteArrayInputStream(((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES)));
        driver.quit();
    }
}
