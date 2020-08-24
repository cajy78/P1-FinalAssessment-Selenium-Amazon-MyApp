package selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class AmazonPage {

	@FindBy(how = How.ID, using="searchDropdownBox")
	private WebElement navSearchDropDown;
	//private Select dropDown;
	
	@FindBy(how = How.ID, using="twotabsearchtextbox")
	private WebElement searchBar;
	
	public void selectBooks()
	{
		//dropDown = new Select(navSearchDropDown);
		navSearchDropDown.sendKeys("Books");
		searchBar.submit();
	}
}
