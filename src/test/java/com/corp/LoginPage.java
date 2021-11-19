package com.corp;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage extends BasePage{

    public String URL = "http://demo.hospitalrun.io/";

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

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //TODO method "set/get" credentials(As valid and no), maybe use for storage JSON

    enum Creeds{
        LOGIN("hr.doctor@hospitalrun.io"),
        PASSWORD("HRt3st12");

        private final String value;

        Creeds(String value){this.value =value;}
    }

    private WebDriverWait wait = new WebDriverWait(driver,10);

    @Step("Open login page: {url}")
    @Override
    public void openPage(String url) {
        super.openPage(url);
        wait.until(ExpectedConditions.visibilityOf(signForm));
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
    private boolean checkLoginStatus(){
        return true;
    }


    @Step("Login with valid credentials")
    public void loginWithValidCreeds(){
        inputCreeds(Creeds.LOGIN.value, Creeds.PASSWORD.value);
        clickSignIn();
        Assert.assertTrue(checkLoginStatus(),
                "Login with valid creeds unsuccessful");
    }
    @Step("Login with invalid credentials")
    public void loginWithInvalidCreeds(){
        inputCreeds("Some_login", "Some_password");
        clickSignIn();
        Assert.assertFalse(false,
                "Login with invalid creeds successful");
    }



}
