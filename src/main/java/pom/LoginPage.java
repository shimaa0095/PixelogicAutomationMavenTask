package pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	public void fillLoginData(String userName, String password) {
		WebDriver driver = BaseClass.getWebDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.name("username")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/button")).click();
	}

	public String getLoginErrorMessage() {
		BaseClass.getWebDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return BaseClass.getWebDriver().findElement(By.xpath("//*[@id=\"loginfrm\"]/div[1]/div")).getText();
	}
}
