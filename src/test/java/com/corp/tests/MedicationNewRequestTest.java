package com.corp.tests;

import com.corp.pages.elements.SideBar.*;
import com.corp.pages.medications.NewRequestPage;
import com.corp.pages.patients.PatientListingPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MedicationNewRequestTest extends TestSetUp{

    public PatientListingPage patientListingPage;
    public NewRequestPage newRequestPage;

    @BeforeMethod
    void login(){
        loginPage.loginWithValidCreeds();
        patientListingPage = new PatientListingPage(driver);
        patientListingPage.waitingForPageLoaded(5);
        patientListingPage.sideBar.clickOption(SubOpts.NEW_REQUEST);
        newRequestPage = new NewRequestPage(driver);
        newRequestPage.waitingForPageLoaded(5);

    }



    @Test(description = "Assert that Medication Section contains next 4 items: Requests, Completed, New Request, Return Medication")
    void assertThatMedicationSectionContains(){
        newRequestPage.sideBar.assertThatSelectedPrimOptContainsSubOptions();
    }

    @Test(invocationCount = 1,description = "Medication - New Request:Fill all fields using next data")
    void medicationNewRequestFillForm(){
        newRequestPage.waitingForPageLoaded(5);
        newRequestPage.scriptExecutor();
        newRequestPage.inputPatientField("Test Patient","Test - Patient - P00201");
        newRequestPage.selectEnyAvailableDateInVisit();
        newRequestPage.choseYesterdayInPrescriptionDate();
        newRequestPage.inputMedication("Pramoxine");
        newRequestPage.inputPrescription("Testing prescription");
        newRequestPage.inputQuantityRequested(1,5);
        newRequestPage.inputQuantityRefills(5,10);
        newRequestPage.addRequest();
        newRequestPage.popUp.waitUntilPopupDisplayed();
        newRequestPage.popUp.assertPopUpOfContainsElements();
        newRequestPage.popUp.assertThatPopupContainsText("Request saved");
        newRequestPage.popUp.clickPopupOk();
        newRequestPage.popUp.assertPopupIsNotPresent();

    }


    @Test
    void assertThatErrorPopupShowed(){
        newRequestPage.waitingForPageLoaded(5);
        newRequestPage.addRequest();
        newRequestPage.popUp.waitUntilPopupDisplayed();
        newRequestPage.popUp.assertPopUpOfContainsElements();
        newRequestPage.popUp.assertThatPopupContainsText("Warning");
    }

}
