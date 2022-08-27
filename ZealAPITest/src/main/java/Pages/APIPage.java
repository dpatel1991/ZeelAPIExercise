package Pages;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APIPage {
    Payload payload = new Payload();

    public void getPatientListwithAppointmentDate() {
        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com/test/patient";

        String response = given().when().get().then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        List<String> appointmentDates = js.getList("appointment_date");
        int count = 0;
        for (int i = 0; i < appointmentDates.size(); i++) {
            if (appointmentDates.get(i).contains("06")) {
                count++;
            }
        }
        System.out.println("Total number of appointments for June month:" + count);
        Assert.assertTrue(count >= 1, "Appointments for June month is not 1 or more than 1");
    }

    public void getPatientList() {
        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com/test/patient";

        String response = given().when().get().then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        List<String> patientFirstNames = js.getList("name.firstName");

        Assert.assertTrue(patientFirstNames.contains("Steve"), "Patient does not exist");
    }

    public void getPatientsID() {
        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com/test/patient";

        String response = given().when().get().then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        List<String> id = js.getList("id");
        List<String> birthDate = js.getList("birthdate");
        List<String> appointmentDate = js.getList("appointment_date");
        List<String> firstName = js.getList("name.firstName");
        List<String> lastName = js.getList("name.lastName");

        for (int i = 0; i < id.size(); i++) {
            String birthdate = birthDate.get(i);
            String appointmentdate = appointmentDate.get(i);
            String firstname = firstName.get(i);
            String lastname = lastName.get(i);

            char firstnamechar = firstname.charAt(0);
            char lastnamechar = lastname.charAt(0);
            String fourdigitbyear = birthdate.substring(0, 4);
            String twodigitbmonth = birthdate.substring(5, 7);
            String twodigitbday = birthdate.substring(8, 10);
            String fourdigitappointmentyear = appointmentdate.substring(0, 4);
            String twodigitappointmentmonth = appointmentdate.substring(5, 7);
            String twodigitappointmentday = appointmentdate.substring(8, 10);

            StringBuilder sb = new StringBuilder();
            sb.insert(0, firstnamechar);
            sb.insert(1, lastnamechar);
            sb.insert(2, fourdigitbyear);
            sb.insert(6, twodigitbmonth);
            sb.insert(8, twodigitbday);
            sb.insert(10, fourdigitappointmentyear);
            sb.insert(14, twodigitappointmentmonth);
            sb.insert(16, twodigitappointmentday);

            String ID = id.get(i);
            Assert.assertTrue(ID.contains(sb), "ID is not as per the format");
        }
    }

    public void modifyPatientDetails() {
        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com";

        String response = given().log().all().queryParam("api_key", "940893157622").header("Content-Type", "application/json")
                .body(payload.patientDetails()).when().patch("/test/update").then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        String id = js.getString("id");
        String birthdate = js.getString("birthdate");
        String phone = js.getString("phone");
        String appointment_date = js.getString("appointment_date");
        String lastName = js.getString("name.lastName");
        String firstName = js.getString("name.firstName");
        String street = js.getString("address.street");
        String city = js.getString("address.city");
        String state = js.getString("address.state");
        String zip = js.getString("address.zip");

        Assert.assertEquals(id, "SR19760827202206208364");
        Assert.assertEquals(birthdate, "1976-08-27");
        Assert.assertEquals(phone, "347-555-9876");
        Assert.assertEquals(appointment_date, "2022-06-20");
        Assert.assertEquals(lastName, "Tester");
        Assert.assertEquals(firstName, "Awesome");
        Assert.assertEquals(street, "123 123 Park");
        Assert.assertEquals(city, "New Jersy");
        Assert.assertEquals(state, "CA");
        Assert.assertEquals(zip, "12345");
    }
}
