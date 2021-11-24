package com.corp.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractBasePage {

    protected final WebDriver driver;
    protected WebDriverWait wait;


    public AbstractBasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void waitThatPageDisplayedByElement(WebElement element, int timeOutInSec){
        WebDriverWait waitForPageLoaded = new WebDriverWait(driver,Duration.ofSeconds(timeOutInSec));
        waitForPageLoaded.until(ExpectedConditions.visibilityOf(element));
    }

    @Step("Open page: {url}")
    protected void openPage(String url){
        driver.get(url);
    }

    protected Boolean checkElementExists(By by){
        return !driver.findElements(by).isEmpty();
    }

}
