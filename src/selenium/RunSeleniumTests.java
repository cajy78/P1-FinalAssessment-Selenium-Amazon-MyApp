package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Alert;
import myAppDBOperation.InsertBookInfo;
import javax.swing.JOptionPane;

public class RunSeleniumTests {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		WebDriver driver = SeleniumChromeDriver.initiateChromeDriver();
		driver.get("https://www.amazon.in");
		
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		Boolean completed = ap.startExtract(driver);
		if(completed)
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/BookInfoExtracted.html");
			Alert a1 = driver.switchTo().alert();
			Thread.sleep(8000);
			a1.accept();
			DisplayExtractedBookInfo.displayData(ap.bookCategories, ap.bookNames, driver);
			Thread.sleep(10000);
			driver.close();
			int selection = JOptionPane.showConfirmDialog(null, "Do you want to add the extracted book information from Amazon to "
					+ "the MyApp database", "Database Operation", JOptionPane.OK_CANCEL_OPTION);
			if(selection==0)
			{
				boolean test = InsertBookInfo.storeBookInformaton(ap.bookCategories, ap.bookNames);
				WebDriver testCompletionWindow = SeleniumChromeDriver.initiateChromeDriver();
				if(test)
				{
					JOptionPane.showMessageDialog(null, "Database operation successful. Click OK to continue","Success", JOptionPane.DEFAULT_OPTION);
					testCompletionWindow.get("http://localhost:8080/SimpliLearnP1Test-Assessment/index.html");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Error occured in adding values to the database. "
							+ "Check console log for exception stack trace","Database Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else
		{
			driver.get("http://localhost:8080/SimpliLearnP1Test-Assessment/elementNotFound.html");
		}
	}
}