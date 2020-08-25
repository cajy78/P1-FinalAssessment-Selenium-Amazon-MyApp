package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RunSeleniumTests {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		WebDriver driver = SeleniumChromeDriver.initiateChromeDriver();
		driver.get("https://www.amazon.in");
		
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		Boolean completed = ap.startExtract(driver);
		
		if(completed)
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookNamesExtracted.html");
			System.out.println("Printing book categories");
			for(String i: ap.bookCategories)
			{
				System.out.println(i);
			}
			System.out.println("Printing book Names");
			System.out.println("Printing book Names");
			for(String i: ap.bookNames)
			{
				System.out.println(i);
			}
		}
		else
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/elementNotFound.html");
		}
	}
}
