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
		TestScreenshots.takeSS(driver, "C:\\Users\\cajy7\\OneDrive\\Documents\\Studies and Certs"
					+ "\\Automation Testing Masters\\Phase 1\\Assessment Project\\Screenshots\\00-Amazon-HomePage.jpg");
		
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		Boolean completed = ap.startExtract(driver);
		if(completed)
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookInfoExtracted.html");
			Alert a1 = driver.switchTo().alert();
			Thread.sleep(30000);
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
			boolean myAppTest = false;
			
			if(!nameExists && !catExists)
			{
				boolean test = InsertBookInfo.storeBookInformaton(ap.bookCategories, ap.bookNames);
				if(test)
				{
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/DBOperationSuccessful.html");
					Alert a2 = testCompletionWindow.switchTo().alert();
					Thread.sleep(30000);
					a2.accept();
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/index.html");
					TestScreenshots.takeSS(testCompletionWindow, "C:\\Users\\cajy7\\OneDrive\\Documents\\Studies and Certs"
					+ "\\Automation Testing Masters\\Phase 1\\Assessment Project\\Screenshots\\08-MyApp-HomePage.jpg");
					myAppTest = myapp.startHomeTest(testCompletionWindow);
				}
				else
				{
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/dbError.html");
					Thread.sleep(30000);
				}
			}
			else
			{
				testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookInfoAlreadyExists.html");
				Thread.sleep(30000);
				Alert a3 = testCompletionWindow.switchTo().alert();
				a3.accept();
				testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/index.html");
				myAppTest = myapp.startHomeTest(testCompletionWindow);
			}
			
			if(myAppTest)
				if(myapp.outputTestComplete && myapp.blankTestComplete && myapp.validityTestComplete)
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/TestsCompleted.html");
		}
		else
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/elementNotFound.html");
		}
	}
}