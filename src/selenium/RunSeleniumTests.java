package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RunSeleniumTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = SeleniumChromeDriver.initiateChromeDriver();
		driver.get("https://www.amazon.in");
		
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		ap.selectBooks();
		ap.waitForLoad(driver);
		ap.selectActionAdventure();
		ap.waitForLoad(driver);
		ap.listTopBooks();
	}
}
