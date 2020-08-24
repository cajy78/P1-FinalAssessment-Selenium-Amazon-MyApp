package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.FindAll;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class AmazonPage {

	@FindBy(how = How.ID, using="searchDropdownBox")
	private WebElement navSearchDropDown;
	private Select dropDown;
	
	@FindBy(how = How.ID, using="twotabsearchtextbox")
	private WebElement searchBar;
	
	@FindBy(how = How.XPATH, using="//*[@id=\"leftNav\"]/ul[1]/ul/div/li[1]/span/a/span")
	private WebElement actionAdventureLink;
	
	@FindBy(how = How.XPATH, using="//*[@class=\"a-spacing-mini acswidget-carousel__header\"]/a[@aria-label=\"See more Most gifted \"]")
	private WebElement showAllActionAdventureBooks;
	
	@FindAll({@FindBy(how = How.CLASS_NAME, using="p13n-sc-truncated")})
	private List<WebElement> bookElements = new ArrayList<WebElement>();
	
	public void startExtract(WebDriver driver)
	{
		selectBooks();
		waitForLoad(driver);
		scrollDown(driver);
		selectActionAdventure();
		waitForLoad(driver);
		listTopBooks();
		waitForLoad(driver);
		getAllBooks();
		driver.close();
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
	
	private void getAllBooks()
	{
		System.out.println("Function called to display All books");
		//bookElement = new ArrayList<WebElement>();
		//bookElements = new ArrayList<WebElement>();
		System.out.println("Current list size"+bookElements.size());
		//Iterator elementIterator = bookElements.iterator();
		for(WebElement book:bookElements)
		{
			String name = book.getText();
			System.out.println(name);
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
