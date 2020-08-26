package selenium;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import org.openqa.selenium.WebDriver;

public class DisplayExtractedBookInfo {

	public static void displayData(List<String> categories, List<String> names, WebDriver driver)
	{
		try
		{
			PrintStream fw = new PrintStream("C:\\Users\\cajy7\\OneDrive\\Documents\\Studies and Certs\\"
				+ "Automation Testing Masters\\Phase 1\\Assessment Project\\SimpliLearnP1Test-Assessment"
				+ "\\build\\AmazonExtractedBookInfo.txt");
			fw.println("Following Information will be added into the MyApp Database");
			fw.println("");
			fw.println("Book categories from Amazon");
			for(String i:categories)
			{
				fw.println(i);
			}
			fw.println("");
			fw.println("Most Gifted Action and Adventure book names");
			for(String i:names)
			{
				fw.println(i);
			}
			fw.flush();
			fw.close();
			driver.get("C:\\Users\\cajy7\\OneDrive\\Documents\\Studies and Certs"
				+ "\\Automation Testing Masters\\Phase 1\\Assessment Project\\"
				+ "SimpliLearnP1Test-Assessment\\build\\AmazonExtractedBookInfo.txt");
			TestScreenshots.takeSS(driver, "C:\\Users\\cajy7\\OneDrive\\Documents\\Studies and Certs"
					+ "\\Automation Testing Masters\\Phase 1\\Assessment Project\\Screenshots\\06-Local-extractedInfoDisplayed.jpg");
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
	}
}
