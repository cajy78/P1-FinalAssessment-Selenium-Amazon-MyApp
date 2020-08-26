package selenium;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class TestScreenshots {

	protected static void takeSS(WebDriver driver, String path)
			throws IOException
		{
			TakesScreenshot scrnSht = ((TakesScreenshot)driver);
			
			File scrFile = scrnSht.getScreenshotAs(OutputType.FILE);
			File destFile = new File(path);
			FileUtils.copyFile(scrFile, destFile);
		}
}
