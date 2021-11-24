package com.corp.pages.medications;

import com.corp.pages.AbstractMainPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.util.Objects;
import java.util.Random;

public class NewRequestPage extends AbstractMainPage {

    @FindBy(xpath = "//*[text()=\"Patient\"]/../div/span/input[2]")
    private WebElement patientField;

    @FindBy(css = "div.tt-menu.tt-open")
    private WebElement patientHintDropdown;

    @FindBy(xpath = "//*[text()=\"Visit\"]/../div/select")
    private WebElement visitSelect;

    @FindBy(xpath = "//*[text()=\"Medication\"]/../div/span/input[2]")
    private WebElement medicationField;

    @FindBy(xpath = "//*[text()=\"Prescription\"]/../div/textarea")
    private WebElement prescriptionField;

    @FindBy(xpath = "//*[text()=\"Prescription Date\"]/../div")
    private WebElement prescriptionDate;

    @FindBy(xpath = "//*[text()=\"Quantity Requested\"]/../div/input")
    private WebElement quantityRequestedField;

    @FindBy(xpath = "//*[text()=\"Refills\"]/../div/input")
    private WebElement refillsField;

    @FindBy(xpath = "//button[text()=\"Add\"]")
    private WebElement addButton;

    public NewRequestPage(WebDriver driver) {
        super(driver);
    }

    @Step("Execute script =)")
    public void scriptExecutor()  {
        while (true) {

            patientField.sendKeys("t");
            if(patientHintDropdown.isDisplayed()){
                patientField.sendKeys(Keys.BACK_SPACE);
                break;
            }
            patientField.sendKeys(Keys.BACK_SPACE);
        }
    }

    @Step("Patient field: Start input '{startInput}', and then select from dropdown hint '{selectFromHintDD}'")
    public void inputPatientField(String startInput,String selectFromHintDD){

        patientField.sendKeys(startInput);
        driver.findElement(By.xpath(String.format("//*[text()='%s']",selectFromHintDD))).click();
    }

    @Step("Visit select: Select any avaible date")
    public void selectEnyAvailableDateInVisit(){
        Select select = new Select(visitSelect);

        long start = System.currentTimeMillis();
        long end = start + 3*1000;
        while (System.currentTimeMillis() < end) {
            if (select.getOptions().size() > 1) {
                select.selectByIndex(2); // TODO select randome date
                break;
            }
        }
    }

    @Step("Medication field: start input {startInput}, and then picck any avaible value from dropdown hint")
    public void inputMedication(String startInput){
        medicationField.sendKeys(startInput);
        driver.findElement(with(By.cssSelector(".tt-suggestion")).near(driver.findElement(By.cssSelector(".test-medication-input .tt-input")))).click();
    }

    @Step("Prescription field: input {textForInput}")
    public void inputPrescription(String textForInput){
        prescriptionField.sendKeys(textForInput);
    }

    @Step("Prescription date: choose yesterday")
    public void chooseYesterdayInPrescriptionDate(){
        Actions actions = new Actions(driver);
        actions.click(prescriptionDate);
        actions.sendKeys(Keys.LEFT, Keys.ENTER);
        actions.perform();


    }

    @Step("Quantity Requested: input random value from {from} to {to} ")
    public void inputQuantityRequested(int from, int to){
        quantityRequestedField.sendKeys(Objects.toString(new Random().nextInt(to-from) + from));
    }

    @Step("Refills: input random value from {from} to {to} ")
    public void inputQuantityRefills(int from, int to){
        refillsField.sendKeys(Objects.toString(new Random().nextInt(to-from) + from));
    }

    @Step("Click add button")
    public void addRequest(){
        while (true){ //TODO think what do with that
            if(driver.findElements(By.cssSelector(".has-error")).isEmpty()){
                break;
            }
        }
        addButton.click();
    }




}
