package tests;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahmed.excelizer.ExcelReader;

import pom.BaseClass;
import pom.RegisterationPage;

public class SeleniumRegisterationTest {
	private String url = "https://www.phptravels.net/register";
	private static String fileName = "\\data\\registeration_data.xlsx";
	private String reportName = "ExtentReportRegisteration.html";
	private String reportHeader = "RegisterExtentDemo";

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
	public void testRegisteration(String firstName, String lastName, String mobileNumber, String email, String password,
			String confirmPassword, String expectedResult) throws IOException, InterruptedException {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.setUpChromeDriver(url);
		RegisterationPage registerationPage = new RegisterationPage();
		registerationPage.fillRegisterationData(firstName, lastName, mobileNumber, email, password, confirmPassword);
		localBaseClass.compareResultAndSetReport(expectedResult, registerationPage);
		BaseClass.getWebDriver().quit();
	}

	@AfterMethod
	public void endOfTest() {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.tearDown();
	}

}
