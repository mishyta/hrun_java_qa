package com.corp.pages.elements;

import com.corp.pages.AbstractBasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.*;

public class SideBar extends AbstractBasePage {

    public SideBar(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "span.octicon-gear")
    private WebElement settingsGear;

    @FindBy(css = "a.logout")
    private WebElement logOut;

    @FindBy(css = "a.category-sub-item")
    private List<WebElement> subOpts;

    @FindBy(css = "primary-nav-item")
    private List<WebElement> primOpts;

    enum PrimeOpts {
        PATIENTS(By.cssSelector(".primary-section-link[href='#/patients']"), SubOpts.LISTING, SubOpts.ADMITTED),
        MEDICATION(By.cssSelector(".primary-section-link[href='#/medication']"), SubOpts.REQUESTS, SubOpts.COMPLETED, SubOpts.NEW_REQUEST, SubOpts.RETURN_MEDICATION);

        List<SubOpts> subs;
        By by;

        PrimeOpts(By by, SubOpts... subs){
            this.subs = new ArrayList<>(Arrays.asList(subs));
            this.by = by;
        }

        public List<SubOpts> getSubs() {
            return subs;
        }
    }

    public enum SubOpts {
        LISTING(By.cssSelector("")),
        ADMITTED(By.cssSelector("")),

        REQUESTS(By.cssSelector("")),
        COMPLETED(By.cssSelector("")),
        NEW_REQUEST(By.cssSelector("div.category-sub-items a[href=\"#/medication/edit/new\"]")),
        RETURN_MEDICATION(By.cssSelector("")),

        ;

        By by;
        SubOpts(By by) {
            this.by = by;
        }

        PrimeOpts getPrime(){
            PrimeOpts[] primeOpts = PrimeOpts.values();
            for (PrimeOpts primeOpt :
                    primeOpts) {
                if(primeOpt.getSubs().contains(this)){
                    return primeOpt;
                }
            }
            throw new RuntimeException("SubOpt not found in any PrimOpt");
        }
    }

    @Step("SideBar - > LogOut")
    public void logOut(){
        wait.until(ExpectedConditions.visibilityOf(settingsGear));
        settingsGear.click();
        logOut.click();
    }

    @Step("SideBar - > Click option {option}")
    public void clickOption(SubOpts option){
        driver.findElement(option.getPrime().by).click();
        driver.findElement(option.by).click();
    }

    @Step("Get from selected prim option names of sub options")
    private List<String> getSelectedPrimOptSubOptNames(){
        List<String> subOptNames= new ArrayList<>();
        subOpts.forEach(subOpt -> subOptNames.add(subOpt.getText()));
        return subOptNames;
    }

    @Step("Check which prim option is selected")
    private PrimeOpts checkWhichPrimOptIsSelected(){
        PrimeOpts[] primeOpts = PrimeOpts.values();
        for (PrimeOpts primeOpt : primeOpts){
            WebElement prOpt = driver.findElement(primeOpt.by);
            if(prOpt.findElements(By.xpath("./../../div")).size() > 1){
                return primeOpt;
            }
        }
        throw new RuntimeException("No one PrimOpt selected");
    }

    @Step("Assert that selected prim option contains sub options")
    public void assertThatSelectedPrimOptContainsSubOptions(){

        Assert.assertEquals(
                checkWhichPrimOptIsSelected()
                        .subs
                        .toString()
                        .toLowerCase()
                        .replace('_',' '),
                getSelectedPrimOptSubOptNames()
                        .toString()
                        .toLowerCase()
        );
    }

}
