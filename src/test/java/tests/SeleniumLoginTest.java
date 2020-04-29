package tests;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahmed.excelizer.ExcelReader;

import pom.BaseClass;
import pom.LoginPage;

public class SeleniumLoginTest {
	private String url = "https://www.phptravels.net/login";
	private static String fileName = "\\data\\login_data.xlsx";
	private String reportName = "ExtentReportLogin.html";
	private String reportHeader = "LoginExtentDemo";

	@DataProvider(name = "DataProviderForusers")
	public static Object[][] useraData() {
		return ExcelReader.loadTestData(System.getProperty("user.dir") + fileName, "Sheet1");
	}

	@BeforeClass
	public void beforeClass() {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.initializeReport(reportName, reportHeader);
	}

	@Test(dataProvider = "DataProviderForusers")
	public void testLogin(String userName, String password, String expectedResult)
			throws IOException, InterruptedException {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.setUpChromeDriver(url);
		LoginPage localLoginpage = new LoginPage();
		localLoginpage.fillLoginData(userName, password);
		localBaseClass.compareResultAndSetReport(expectedResult, localLoginpage);
		BaseClass.getWebDriver().quit();
	}

	@AfterMethod
	public void endOfTest() {
		BaseClass localBaseClass = new BaseClass();
		localBaseClass.tearDown();
	}

}
