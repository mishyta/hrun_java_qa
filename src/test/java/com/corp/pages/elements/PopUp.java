package com.corp.pages.elements;

import com.corp.pages.AbstractBasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class PopUp extends AbstractBasePage {

    @FindBy(css = "div.modal-content")
    protected WebElement pagePopup;

    private By pagePopupLocator = By.cssSelector("div.modal-content");

    public PopUp(WebDriver driver) {
        super(driver);
    }

    private enum PopupElements {
        HEADER(By.cssSelector("div.modal-header")),
        BODY(By.cssSelector("div.modal-body")),
        FOOTER(By.cssSelector("div.modal-footer")),
        CLOSE_BTN(By.cssSelector("div.modal-header button.close")),
        OK_BTN(By.cssSelector("div.modal-footer button.btn.btn-primary"));

        By by;
        PopupElements(By by) {
            this.by = by;
        }
    }

    @Step("PopUp -> Click OK button")
    public void clickPopupOk(){
        pagePopup.findElement(PopupElements.OK_BTN.by).click();
        wait.until(ExpectedConditions.invisibilityOf(pagePopup));
    }

    @Step("PopUp -> Click Cross button")
    public void closePopup(){
        pagePopup.findElement(PopupElements.CLOSE_BTN.by).click();
        assertPopupIsNotPresent();
    }

    @Step("Assert that popup isn't present")
    public void assertPopupIsNotPresent(){
        Assert.assertFalse(checkElementExists(pagePopupLocator), "popup is present");
    }

    @Step("Wait until popup displayed")
    public void waitUntilPopupDisplayed(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(pagePopupLocator));
    }

    @Step("Assert that popup contains text: {text}")
    public void assertThatPopupContainsText(String text){
        Assert.assertTrue(pagePopup.getText()
                        .toLowerCase()
                        .contains(text.toLowerCase()),
                "Pop up doesn't contains: " + text);
    }

    @Step("Assert that popup contains elements")
    public void assertPopUpOfContainsElements(){
        PopupElements[] popupElements = PopupElements.values();
        for (PopupElements popupElement: popupElements){
            Assert.assertTrue(checkElementExists(popupElement.by),
                    "element doesn't exists" + popupElement.by.toString());
        }
    }

}
