package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RunSeleniumTests {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		WebDriver driver = SeleniumChromeDriver.initiateChromeDriver();
		driver.get("https://www.amazon.in");
		
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		ap.startExtract(driver);
	}
}
