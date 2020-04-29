package tests;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahmed.excelizer.ExcelReader;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import static io.restassured.path.xml.XmlPath.CompatibilityMode.HTML;
import io.restassured.response.Response;
import pom.BaseClass;

public class RestAssuredRegisterationTest {
	private String url = "https://www.phptravels.net/account/signup";
	private String reportName = "ExtentReportRegisterationApiRequest.html";
	private String reportHeader = "RegisterExtentDemo";
	private String fileName = "\\data\\registeration_data.xlsx";

	@DataProvider(name = "DataProviderForusers")
	public Object[][] useraData() {
		return ExcelReader.loadTestData(System.getProperty("user.dir") + fileName,
				"Sheet1");
	}

	@BeforeClass
	public void beforeClass() {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.initializeReport(reportName, reportHeader);
	}

	@Test(dataProvider = "DataProviderForusers")
	public void restApiRegisterationTest(String firstName, String lastName, String mobileNumber, String email,
			String password, String confirmPassword, String expectedResult) {

		Response response = given().formParam("firstname", firstName).formParam("lastname", lastName)
				.formParam("phone", mobileNumber).formParam("email", email).formParam("password", password)
				.formParam("confirmpassword", confirmPassword).when().post(url)
				.then().contentType(ContentType.HTML).extract().response();
		assertEquals(response.getStatusCode(), 200);
		XmlPath htmlPath = new XmlPath(HTML, response.getBody().asString());

		String actualResult = htmlPath.getString("html.body");
		String expectedResultContent = new BaseClass().getExpectedResultContent(expectedResult);
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.assertAndSetReport(actualResult, expectedResultContent);

	}

	@AfterMethod
	public void endOfTest() {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.tearDown();
	}

}
