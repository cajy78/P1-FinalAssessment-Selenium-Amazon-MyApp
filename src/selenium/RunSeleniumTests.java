package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Alert;

import myAppDBOperation.CheckBookInfo;
import myAppDBOperation.InsertBookInfo;

import java.io.PrintStream;
import java.io.IOException;

public class RunSeleniumTests {

	public static void main(String[] args) throws InterruptedException, IOException{
		// TODO Auto-generated method stub
		WebDriver driver = SeleniumChromeDriver.initiateChromeDriver();
		driver.get("https://www.amazon.in");
		
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		Boolean completed = ap.startExtract(driver);
		if(completed)
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookInfoExtracted.html");
			Alert a1 = driver.switchTo().alert();
			//Thread.sleep(1000);
			a1.accept();
			DisplayExtractedBookInfo.displayData(ap.bookCategories, ap.bookNames, driver);
			//Thread.sleep(1000);
			driver.close();
			PrintStream fw = new PrintStream("C:\\Users\\cajy7\\OneDrive\\Documents\\Studies and Certs\\"
					+ "Automation Testing Masters\\Phase 1\\Assessment Project\\SimpliLearnP1Test-Assessment"
					+ "\\build\\AmazonExtractedBookInfo.txt");
			fw.println("");
			fw.flush();
			fw.close();
			
			boolean nameExists = CheckBookInfo.checkNameInfo(ap.bookNames);
			boolean catExists = CheckBookInfo.checkCatInfo(ap.bookCategories);
			WebDriver testCompletionWindow = SeleniumChromeDriver.initiateChromeDriver();
			MyAppHomePage myapp = PageFactory.initElements(testCompletionWindow, MyAppHomePage.class);
			if(!nameExists && !catExists)
			{
				boolean test = InsertBookInfo.storeBookInformaton(ap.bookCategories, ap.bookNames);
				if(test)
				{
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/DBOperationSuccessful.html");
					//Thread.sleep(1000);
					Alert a2 = testCompletionWindow.switchTo().alert();
					a2.accept();
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/index.html");
					myapp.startHomeTest(testCompletionWindow);
				}
				else
				{

					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/dbError.html");
				}
			}
			else
			{
				testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookInfoAlreadyExists.html");
				//Thread.sleep(1000);
				Alert a3 = testCompletionWindow.switchTo().alert();
				a3.accept();
				testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/index.html");
				if(myapp.startHomeTest(testCompletionWindow))
					if(myapp.outputTestComplete && myapp.blankTestComplete && myapp.validityTestComplete)
						System.out.println("Tests successfully completed");
					else
						System.out.println("Some error has occurred");
			}
		}
		else
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/elementNotFound.html");
		}
	}
}