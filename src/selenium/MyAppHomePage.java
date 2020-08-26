package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAppHomePage {

	@FindBy(how = How.ID, using="BookIdentification")
	private WebElement bookID;
	
	@FindBy(how = How.ID_OR_NAME, using="bookName")
	private WebElement bookName;
	
	@FindBy(how = How.ID, using="getAllBooks")
	private WebElement showAllBooks;
	
	@FindBy(how = How.ID, using="getAllBookCat")
	private WebElement showAllBookCategories;
	
	@FindBy(how = How.ID, using="goHome")
	private WebElement homeLink;
	
	public boolean startHomeTest(WebDriver driver)
		throws InterruptedException
	{
		boolean testComplete = false;
		try
		{
			testValidBookID();
			Thread.sleep(3000);
			goHome();
			testInvalidBookID();
			Thread.sleep(3000);
			goHome();
			testBlankBookID();
			Thread.sleep(3000);
			goHome();
			testValidBookName();
			Thread.sleep(3000);
			goHome();
			testInvalidBookName();
			Thread.sleep(3000);
			goHome();
			testBlankBookName();
			Thread.sleep(3000);
			goHome();
			showAllBooks();
			Thread.sleep(3000);
			scrollDown(driver);
			goHome();
			showAllBookCategories();
			Thread.sleep(3000);
			scrollDown(driver);
			goHome();
			testComplete = true;
		}
		catch(NoSuchElementException nse)
		{
			testComplete = false;
		}
		return testComplete;
	}
	
	private void testValidBookID()
	{
		bookID.sendKeys("BKIN_120");
		bookID.submit();
	}
	
	private void testInvalidBookID()
	{
		bookID.sendKeys("BKIN12335");
		bookID.submit();
	}
	
	private void testBlankBookID()
	{
		bookID.sendKeys("");
		bookID.submit();
	}
	
	private void testValidBookName()
	{
		bookName.sendKeys("Harry");
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
}
