package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumChromeDriver {

	private static WebDriver driver;
	
	public static WebDriver initiateChromeDriver()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\cajy7\\Downloads\\chromedriver_win32\\chromedriver.exe");
		ChromeOptions cmo = new ChromeOptions();
		cmo.addArguments("start-maximized");
		driver = new ChromeDriver(cmo);
		return driver;
	}
	
}
