package pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterationPage {
	public void fillRegisterationData(String fName, String lName, String phone, String email, String passW,
			String confirmPW) {
		WebDriver driver = BaseClass.getWebDriver();

		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		driver.findElement(By.name("firstname")).sendKeys(fName);
		driver.findElement(By.name("lastname")).sendKeys(lName);
		driver.findElement(By.name("phone")).sendKeys(phone);
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(passW);
		driver.findElement(By.name("confirmpassword")).sendKeys(confirmPW);
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"headersignupform\"]/div[8]/button")).click();
	}

	public String getRegisterationErrorMessage() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.xpath("//*[@id=\"headersignupform\"]/div[2]/div/p")).getText();
	}

	public String getpopUpMessageForFirstName() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.name("firstname")).getAttribute("validationMessage");
	}

	public String getpopUpMessageForLastName() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.name("lastname")).getAttribute("validationMessage");
	}

	public String getpopUpMessageForMobileNumber() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.name("phone")).getAttribute("validationMessage");
	}

	public String getpopUpMessageForEmail() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.name("email")).getAttribute("validationMessage");
	}

	public String getpopUpMessageForPassword() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.name("password")).getAttribute("validationMessage");
	}

	public String getpopUpMessageForConfirmPassword() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.name("confirmpassword")).getAttribute("validationMessage");
	}
}
