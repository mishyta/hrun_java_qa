package com.corp.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends AbstractBasePage {

    private final String URL = "http://demo.hospitalrun.io/";

    @FindBy(css = "div.signin-logo")
    private WebElement pageLogo;

    @FindBy(css = "#identification")
    private WebElement loginField;

    @FindBy(css = "#password")
    private WebElement passwordField;

    @FindBy(css = "button[type=\"submit\"]")
    private WebElement submitButton;

    @FindBy(css = "div.form-signin-alert")
    private WebElement loginAlert;

    @FindBy(css = "div.signin-contents")
    private WebElement signForm;

    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    //TODO method "set/get" credentials(As valid and no), maybe use for storage JSON

    enum Creeds{
        LOGIN("hr.doctor@hospitalrun.io"),
        PASSWORD("HRt3st12");

        private final String value;

        Creeds(String value){this.value =value;}
    }


    public LoginPage(WebDriver driver) {
        super(driver);
        this.openPage(URL);
    }

    @Step("Open login page: {url}")
    @Override
    public void openPage(String url) {
        super.openPage(url);
        waitingForPageLoaded(5);
    }

    @Step("Input credentials")
    private void inputCreeds(String login, String password){
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
    }

    @Step("Click SignIn")
    private void clickSignIn(){
        submitButton.click();
    }

    @Step("Check login status")
    public void checkLoginAlertExists(){
        wait.until(ExpectedConditions.visibilityOf(loginAlert));
    }


    @Step("Login with valid credentials")
    public void loginWithValidCreeds(){
        inputCreeds(Creeds.LOGIN.value, Creeds.PASSWORD.value);
        clickSignIn();
    }
    @Step("Login with invalid credentials")
    public void loginWithInvalidCreeds(){
        inputCreeds("Some_login", "Some_password");
        clickSignIn();
    }

    @Step("Wait until page loaded. Timeout: {timeOutInSec} sec")
    public void waitingForPageLoaded(int timeOutInSec){
        waitThatPageDisplayedByElement(pageLogo, timeOutInSec);
    }


}
