package com.corp.pages;

import com.corp.pages.elements.PopUp;
import com.corp.pages.elements.SideBar;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractMainPage extends AbstractBasePage {

    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(css = "h1.view-current-title")
    protected WebElement pageTittle;



    public final SideBar sideBar = new SideBar(driver);
    public final PopUp popUp = new PopUp(driver);
    public AbstractMainPage(WebDriver driver) {
        super(driver);

    }

    @Step("Wait until page loaded. Timeout: {timeOutInSec} sec")
    public void waitingForPageLoaded(int timeOutInSec){
        waitThatPageDisplayedByElement(pageTittle, timeOutInSec);
    }




}
