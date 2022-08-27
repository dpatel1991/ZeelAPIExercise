package Tests;

import Pages.APIPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class APITest {
    APIPage apiPage = new APIPage();

    @Test(priority = 1)
    public void verifyPatientListwithAppointmentDate() {
        apiPage.getPatientListwithAppointmentDate();
    }

    @Test(priority = 2)
    public void verifyPatientIsExistInList() {
        apiPage.getPatientList();
    }

    @Test(priority = 3)
    public void verifyPatientID() {
        apiPage.getPatientsID();
    }

    @Test(priority = 4)
    public void upadatePatientDetails() {
        apiPage.modifyPatientDetails();
    }

}
