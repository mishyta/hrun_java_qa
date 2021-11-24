package com.corp.tests;

import com.corp.pages.patients.PatientListingPage;
import org.testng.annotations.Test;

public class LoginTest extends TestSetUp {

    @Test(description = "Login with valid creeds")
    void loginWithValidKeys(){
        loginPage.loginWithValidCreeds();
        PatientListingPage patientListingPage = new PatientListingPage(driver);
        patientListingPage.waitingForPageLoaded(10);
    }

    @Test(description = "Login with invalid creeds")
    void loginWithInvalidKeys(){
        loginPage.loginWithInvalidCreeds();
        loginPage.waitingForPageLoaded(5);
        loginPage.checkLoginAlertExists();
    }

    @Test(description = "Log out")
    void logOut(){
        loginPage.loginWithValidCreeds();
        PatientListingPage patientListingPage = new PatientListingPage(driver);
        patientListingPage.waitingForPageLoaded(5);
        patientListingPage.sideBar.logOut();
        loginPage.waitingForPageLoaded(5);
    }



}
