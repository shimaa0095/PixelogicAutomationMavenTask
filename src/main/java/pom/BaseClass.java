package pom;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {
	static WebDriver driver;
	static ExtentTest test;
	static ExtentReports report;

	// initialize drive, report and start test
	public void setUpChromeDriver(String url) {
		driver = new ChromeDriver();
		// driver.manage().window().maximize();
		driver.get(url);
	}

	public void initializeReport(String reportName, String reportHeader) {
		report = new ExtentReports(System.getProperty("user.dir\\") + reportName);
		test = report.startTest(reportHeader);
	}

	public String captureImage() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("src/../BStackImages/" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

	public void compareResultAndSetReport(String expectedResult, Object obj) throws IOException, InterruptedException {

		String[] expectedCellSplit = expectedResult.split("->");
		String expectedResultKind = expectedCellSplit[0];
		String expectedResultContent = expectedCellSplit[1];

		Thread.sleep(3000);
		if (expectedResultKind.contains("url")) {
			if (expectedResultContent.equals(driver.getCurrentUrl())) {
				test.log(LogStatus.PASS, "Test Passed With Navigated to the specified URL");
			} else {
				test.log(LogStatus.FAIL, test.addScreenCapture(captureImage()) + "Test Failed");
			}

		} // end if 1

		// if my account page loaded and expected result is any other thing
		else if (driver.getCurrentUrl().contains("account"))

		{
			System.out.println(driver.getCurrentUrl());
			test.log(LogStatus.FAIL, test.addScreenCapture(captureImage()) + "Test Failed");
		} // end if 2

		else if (expectedResultKind.contains("pop up") && obj instanceof RegisterationPage) {

			String popUpMessage = "";
			if (expectedResultKind.contains("1")) {
				popUpMessage = ((RegisterationPage) obj).getpopUpMessageForFirstName();
			} else if (expectedResultKind.contains("2")) {
				popUpMessage = ((RegisterationPage) obj).getpopUpMessageForLastName();
			} else if (expectedResultKind.contains("3")) {
				popUpMessage = ((RegisterationPage) obj).getpopUpMessageForMobileNumber();
			} else if (expectedResultKind.contains("4")) {
				popUpMessage = ((RegisterationPage) obj).getpopUpMessageForEmail();
			} else if (expectedResultKind.contains("5")) {
				popUpMessage = ((RegisterationPage) obj).getpopUpMessageForPassword();
			} else if (expectedResultKind.contains("6")) {
				popUpMessage = ((RegisterationPage) obj).getpopUpMessageForConfirmPassword();
			}

			if (expectedResultContent.equals(popUpMessage)) {
				test.log(LogStatus.PASS, "Test Passed With Displaying pop up message for required field");

			} else {
				System.out.println(2);
				test.log(LogStatus.FAIL, test.addScreenCapture(captureImage()) + "Test Failed");
			}

		} // end if 3

		// expectedResultKind is containing error message
		else {
			String errorMessage;
			if (obj instanceof RegisterationPage) {
				errorMessage = ((RegisterationPage) obj).getRegisterationErrorMessage();
			} else { // instance of login page
				errorMessage = ((LoginPage) obj).getLoginErrorMessage();
			}

			if (expectedResultContent.equals(errorMessage)) {
				test.log(LogStatus.PASS, "Test Passed With Displaying The Suitable Error Messsage");

			} else {
				test.log(LogStatus.FAIL, test.addScreenCapture(captureImage()) + "Test Failed");
			}
		} // end if 4

	}

	public String getExpectedResultContent(String expectedResult) {
		String[] expectedCellSplit = expectedResult.split("->");
		String expectedResultKind = expectedCellSplit[0];
		String expectedResultContent = expectedCellSplit[1];
		if (expectedResultKind.contains("url")) {
			expectedResultContent = "true";
		} else if (expectedResultKind.contains("pop up")) {
			expectedResultContent = "required";
		}
		return expectedResultContent;
	}

	public void assertAndSetReport(String actual, String expected) {

		if (actual.equals(expected) || actual.contains("required")) {
			test.log(LogStatus.PASS, "Test Passed With " + actual);
		} else {
			test.log(LogStatus.FAIL, "Test Failed With " + actual);
		}
	}

	public static WebDriver getWebDriver() {
		return driver;
	}

	public static ExtentTest getTest() {
		return test;
	}

	public void tearDown() {
		// baseClass.getWebDriver().quit();
		report.endTest(test);
		report.flush();
	}

}
