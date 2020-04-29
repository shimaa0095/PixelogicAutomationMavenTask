package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahmed.excelizer.ExcelReader;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.XmlPath.CompatibilityMode.HTML;
import static org.testng.Assert.assertEquals;

import pom.BaseClass;

public class RestAssuredLoginTest {
	private String url = "https://www.phptravels.net/account/login";
	private String reportName = "ExtentReportLoginApiRequest.html";
	private String reportHeader = "LoginExtentDemo";
	private String fileName = "\\data\\login_data.xlsx";

	@DataProvider(name = "DataProviderForusers")
	public Object[][] useraData() {
		return ExcelReader.loadTestData(System.getProperty("user.dir") + fileName, "Sheet1");
	}

	@BeforeClass
	public void beforeClass() {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.initializeReport(reportName, reportHeader);
	}

	@Test(dataProvider = "DataProviderForusers")
	public void restApiLoginnTest(String userName, String password, String expectedResult) {

		Response response = given().formParam("username", userName).formParam("password", password).when()
				.post(url).then().contentType(ContentType.HTML).extract()
				.response();
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
