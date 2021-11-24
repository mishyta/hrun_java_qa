package com.corp.tests;

import com.corp.pages.LoginPage;
import com.corp.utils.Listener;
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

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.corp.utils.EnvironmentDetector.JNKENV;

public class TestSetUp {

    public WebDriver driver;
    public LoginPage loginPage;

    @BeforeTest
    public  void setProp(){
        System.setProperty("webdriver.chrome.driver", "/home/mknysh/drivers/chromedriver");
    }

    @BeforeMethod()
    public  void setup() throws MalformedURLException {

        setDriverForEnvironment();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);

    }

    @AfterMethod(description = "browser teardown")
    public void tearDown() {
        Allure.addAttachment("Any text", new ByteArrayInputStream(((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES)));
        driver.quit();
    }


    private void setDriverForEnvironment() throws MalformedURLException {
        if(!JNKENV) {

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

            //noinspection deprecation
            driver = new EventFiringWebDriver(new RemoteWebDriver(
                    URI.create(String.format("http://%s:%d/wd/hub", SelenoidIP, SelenoidPort)).toURL(), capabilities))
                    .register(new Listener());
        }
        else{
            driver = new ChromeDriver();
        }
    }
}
