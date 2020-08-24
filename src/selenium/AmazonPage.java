package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonPage {

	@FindBy(how = How.ID, using="searchDropdownBox")
	private WebElement navSearchDropDown;
	//private Select dropDown;
	
	@FindBy(how = How.ID, using="twotabsearchtextbox")
	private WebElement searchBar;
	
	@FindBy(how = How.XPATH, using="//*[@id=\"leftNav\"]/ul[1]/ul/div/li[1]/span/a/span")
	private WebElement actionAdventureLink;
	
	@FindBy(how = How.XPATH, using="//*[@class=\"a-spacing-mini acswidget-carousel__header\"]/a[@aria-label=\"See more Most gifted \"]")
	private WebElement showAllActionAdventureBooks;
	
	public void selectBooks()
	{
		//dropDown = new Select(navSearchDropDown);
		navSearchDropDown.sendKeys("Books");
		searchBar.submit();
	}
	
	public void selectActionAdventure()
	{
		actionAdventureLink.click();
	}
	
	public void listTopBooks()
	{
		showAllActionAdventureBooks.click();
	}
	
	public void waitForLoad(WebDriver driver) 
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
