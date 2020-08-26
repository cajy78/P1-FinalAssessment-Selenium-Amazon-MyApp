package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.FindAll;
import java.util.List;
import java.util.ArrayList;


public class AmazonPage {

	public List<String> bookCategories, bookNames;
	
	@FindBy(how = How.ID, using="searchDropdownBox")
	private WebElement navSearchDropDown;
	private Select dropDown;
	
	@FindBy(how = How.ID, using="twotabsearchtextbox")
	private WebElement searchBar;
	
	@FindAll({@FindBy(how = How.XPATH, using="//*[@id=\"leftNav\"]/ul[1]/ul/div/li")})
	private List<WebElement> bookCatElements = new ArrayList<WebElement>();
	
	@FindBy(how = How.XPATH, using="//*[@id=\"leftNav\"]/ul[1]/ul/div/li[1]/span/a/span")
	private WebElement actionAdventureLink;
	
	@FindBy(how = How.XPATH, using="//*[@class=\"a-spacing-mini acswidget-carousel__header\"]/a[@aria-label=\"See more Most gifted \"]")
	private WebElement showAllActionAdventureBooks;
	
	@FindAll({@FindBy(how = How.CLASS_NAME, using="p13n-sc-truncated")})
	private List<WebElement> bookElements = new ArrayList<WebElement>();
	
	public boolean startExtract(WebDriver driver)
	{
		Boolean complete = false;
		try
		{
			selectBooks();
			waitForLoad(driver);
			scrollDown(driver);
			getAllBookCategories();
			selectActionAdventure();
			waitForLoad(driver);
			listTopBooks();
			waitForLoad(driver);
			getAllBooksNames();
			waitForLoad(driver);
			complete = true;
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			complete = false;
		}
		return complete;
	}
	
	private void selectBooks()
	{
		dropDown = new Select(navSearchDropDown);
		dropDown.selectByIndex(11);
		searchBar.submit();
	}
	
	private void scrollDown(WebDriver driver)
	{
		JavascriptExecutor jse  = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", actionAdventureLink);
	}
	
	private void selectActionAdventure()
	{
		actionAdventureLink.click();
	}
	
	private void listTopBooks()
	{
		showAllActionAdventureBooks.click();
	}
	
	private void getAllBookCategories()
	{		
		bookCategories = new ArrayList<String>();
		
		for(WebElement bookCat:bookCatElements)
		{
			String name = bookCat.getText();
			//System.out.println(name);
			bookCategories.add(name);
		}
	}
	
	private void getAllBooksNames()
	{
		bookNames = new ArrayList<String>();
		for(WebElement book:bookElements)
		{
			String name = book.getText();
			bookNames.add(name);
		}
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
