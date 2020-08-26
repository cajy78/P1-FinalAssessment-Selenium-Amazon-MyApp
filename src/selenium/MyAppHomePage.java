package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.junit.Assert;
import java.io.IOException;

public class MyAppHomePage {
	
	public boolean validityTestComplete, blankTestComplete, outputTestComplete;

	@FindBy(how = How.ID, using="BookIdentification")
	private WebElement bookID;
	
	@FindBy(how = How.ID, using="invalidinput")
	private WebElement invalidInput;
	
	@FindBy(how = How.ID, using="blankIDErrorL1")
	private WebElement blankIDError;
	
	@FindBy(how = How.CLASS_NAME, using="success")
	private WebElement searchComplete;
	
	@FindBy(how = How.ID, using="blankNameErrorL1")
	private WebElement blankNameError;
	
	@FindBy(how = How.ID_OR_NAME, using="bookName")
	private WebElement bookName;
	
	@FindBy(how = How.ID, using="getAllBooks")
	private WebElement showAllBooks;
	
	@FindBy(how = How.ID, using="getAllBookCat")
	private WebElement showAllBookCategories;
	
	@FindBy(how = How.ID, using="goHome")
	private WebElement homeLink;
	
	public boolean startHomeTest(WebDriver driver, String projectPath)
		throws InterruptedException, IOException
	{
		boolean testComplete = false;
		try
		{
			//Thread.sleep(1000);
			testValidBookID(driver, projectPath + "Screenshots\\09-MyApp-ValidBookID.jpg");
			waitForLoad(driver);
			//Thread.sleep(1000);
			outputTestComplete = assertSearchComplete("Following information is extracted from the MyApp Database", searchComplete.getText());
			TestScreenshots.takeSS(driver, projectPath + "Screenshots\\10-MyApp-BookbyID.jpg");
			goHome();
			waitForLoad(driver);
			//Thread.sleep(1000);
			testInvalidBookID(driver, projectPath + "Screenshots\\11-MyApp-invalidID.jpg");
			validityTestComplete = assertInvalidInput("No data exists with the current selection", invalidInput.getText());
			TestScreenshots.takeSS(driver, projectPath + "Screenshots\\12-MyApp-invalidInput.jpg");
			waitForLoad(driver);
			//Thread.sleep(1000);
			goHome();
			waitForLoad(driver);
			//Thread.sleep(1000);
			testBlankBookID();
			blankTestComplete = assertBlankID("Please enter a Book ID", blankIDError.getText());
			TestScreenshots.takeSS(driver, projectPath + "Screenshots\\13-MyApp-blankBookIDError.jpg");
			//Thread.sleep(1000);
			goHome();
			waitForLoad(driver);
			//Thread.sleep(1000);
			testValidBookName(driver, projectPath + "Screenshots\\14-MyApp-ValidBookName.jpg");
			waitForLoad(driver);
			outputTestComplete = assertSearchComplete("Following information is extracted from the MyApp Database", searchComplete.getText());
			TestScreenshots.takeSS(driver, projectPath + "Screenshots\\14-MyApp-ValidBookNameOutput.jpg");
			//Thread.sleep(1000);
			goHome();
			waitForLoad(driver);
			//Thread.sleep(1000);
			testInvalidBookName();
			waitForLoad(driver);
			validityTestComplete = assertInvalidInput("No data exists with the current selection", invalidInput.getText());
			//Thread.sleep(1000);
			goHome();
			//Thread.sleep(1000);
			testBlankBookName();
			waitForLoad(driver);
			blankTestComplete = assertBlankName("Please enter a Book Name", blankNameError.getText());
			TestScreenshots.takeSS(driver, projectPath + "Screenshots\\15-MyApp-BlankBookNameError.jpg");
			//Thread.sleep(1000);
			goHome();
			//Thread.sleep(1000);
			showAllBooks();
			waitForLoad(driver);
			outputTestComplete = assertSearchComplete("Following information is extracted from the MyApp Database", searchComplete.getText());
			TestScreenshots.takeSS(driver, projectPath + "Screenshots\\16-MyApp-showAllMGAnABooks.jpg");
			//Thread.sleep(1000);
			scrollDown(driver);
			//Thread.sleep(1000);
			goHome();
			waitForLoad(driver);
			//Thread.sleep(1000);
			showAllBookCategories();
			waitForLoad(driver);
			outputTestComplete = assertSearchComplete("Following information is extracted from the MyApp Database", searchComplete.getText());
			TestScreenshots.takeSS(driver, projectPath + "Screenshots\\17-MyApp-showAllBookCategories.jpg");
			//Thread.sleep(1000);
			scrollDown(driver);
			//Thread.sleep(1000);
			goHome();
			testComplete = true;
		}
		catch(NoSuchElementException nse)
		{
			nse.printStackTrace();
			testComplete = false;
		}
		return testComplete;
	}
	
	private void testValidBookID(WebDriver driver, String path)
		throws IOException
	{
		bookID.sendKeys("BKIN_120");
		TestScreenshots.takeSS(driver, path);
		bookID.submit();
	}
	
	private void testInvalidBookID(WebDriver driver, String path)
		throws IOException
	{
		bookID.sendKeys("BKIN12335");
		TestScreenshots.takeSS(driver, path);
		bookID.submit();
	}
	
	private void testBlankBookID()
	{
		bookID.sendKeys("");
		bookID.submit();
	}
	
	private void testValidBookName(WebDriver driver, String path)
		throws IOException
	{
		bookName.sendKeys("Harry");
		TestScreenshots.takeSS(driver, path);
		bookName.submit();
	}
	
	private void testInvalidBookName()
	{
		bookName.sendKeys("Testing1234");
		bookName.submit();
	}
	
	private void testBlankBookName()
	{
		bookName.sendKeys("");
		bookName.submit();
	}
	
	private void showAllBooks()
	{
		showAllBooks.click();
	}
	
	private void showAllBookCategories()
	{
		showAllBookCategories.click();
	}
	
	private void goHome()
	{
		homeLink.click();
	}
	
	private void scrollDown(WebDriver driver)
	{
		JavascriptExecutor jse  = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", homeLink);
	}
	
	private void waitForLoad(WebDriver driver) 
	{
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }
	
	private boolean assertInvalidInput(String expectedResult, String actualResult)
	{
		boolean assertPassed = false;
		try
		{
			Assert.assertEquals(expectedResult, actualResult);
			assertPassed = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return assertPassed;
	}
	
	private boolean assertBlankID(String expectedResult, String actualResult)
	{
		boolean assertPassed = false;
		try
		{
			Assert.assertEquals(expectedResult, actualResult);
			assertPassed = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return assertPassed;
	}
	
	private boolean assertSearchComplete(String expectedResult, String actualResult)
	{
		boolean assertPassed = false;
		try
		{
			Assert.assertEquals(expectedResult, actualResult);
			assertPassed = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return assertPassed;
	}
	
	private boolean assertBlankName(String expectedResult, String actualResult)
	{
		boolean assertPassed = false;
		try
		{
			Assert.assertEquals(expectedResult, actualResult);
			assertPassed = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return assertPassed;
	}
}
