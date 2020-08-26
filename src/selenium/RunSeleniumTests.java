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
		// Project path variable used to locate project parent directory for accessing files and and storing screenshots
		String projectPath = "C:\\Users\\cajy7\\OneDrive\\Documents\\Studies and Certs"
				+ "\\Automation Testing Masters\\Phase 1\\Assessment Project\\";
		
		//Selenium Web driver.
		WebDriver driver = SeleniumChromeDriver.initiateChromeDriver();
		driver.get("https://www.amazon.in");
		TestScreenshots.takeSS(driver, projectPath+"Screenshots\\00-Amazon-HomePage.jpg");
		
		//Amazon Page POM / Page Factory used to initiated elements.
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		//public function called to extract elements from Amazon page and action accordingly.
		Boolean completed = ap.startExtract(driver, projectPath);
		
		if(completed)
		{
			//successfully extracted confirmation page
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookInfoExtracted.html");
			Alert a1 = driver.switchTo().alert();
			Thread.sleep(5000); //sleep added to view alert and alert message
			a1.accept();
			//Static function to display extracted info using a temporary text file.
			DisplayExtractedBookInfo.displayData(ap.bookCategories, ap.bookNames, driver);
			Thread.sleep(5000); //sleep added to show list of extracted information
			driver.close();
			//Temporary text file emptied after displaying data to user.
			PrintStream fw = new PrintStream(projectPath+"SimpliLearnP1Test-Assessment\\build\\AmazonExtractedBookInfo.txt");
			fw.println("");
			fw.flush();
			fw.close();
			
			boolean nameExists = CheckBookInfo.checkNameInfo(ap.bookNames);
			boolean catExists = CheckBookInfo.checkCatInfo(ap.bookCategories);
			
			//New web driver initiated for MyApp Application
			WebDriver testCompletionWindow = SeleniumChromeDriver.initiateChromeDriver();
			//MyApp page POM / Page Factory used to initialise elements
			MyAppHomePage myapp = PageFactory.initElements(testCompletionWindow, MyAppHomePage.class);
			boolean myAppTest = false;
			
			if(!nameExists && !catExists)
			{
				//If info does not exist, call to insert  information into database.
				boolean test = InsertBookInfo.storeBookInformaton(ap.bookCategories, ap.bookNames);
				if(test)
				{
					//Successful DB Operation
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/DBOperationSuccessful.html");
					Alert a2 = testCompletionWindow.switchTo().alert();
					Thread.sleep(5000); //sleep added to view alert page and showcase alert message
					a2.accept();
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/index.html");
					TestScreenshots.takeSS(testCompletionWindow, projectPath + "Screenshots\\08-MyApp-HomePage.jpg");
					//MyApp Application Test initiated
					myAppTest = myapp.startHomeTest(testCompletionWindow, projectPath);
				}
				else
				{
					//DB error Page
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/dbError.html");
					Thread.sleep(5000); //sleep added to view db error page popup message
				}
			}
			else
			{
				//If info already exists, DB Operation is skipped
				testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookInfoAlreadyExists.html");
				Thread.sleep(5000); //sleep added to view and read popup on page
				Alert a3 = testCompletionWindow.switchTo().alert();
				a3.accept();
				testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/index.html");
				//MyApp Application test initiated
				myAppTest = myapp.startHomeTest(testCompletionWindow, projectPath);
			}
			
			//Test outcome
			if(myAppTest)
				if(myapp.outputTestComplete && myapp.blankTestComplete && myapp.validityTestComplete)
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/TestsCompleted.html");
		}
		else
		{
			//If element does not exist due to changed values on Amazon page
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/elementNotFound.html");
		}
	}
}