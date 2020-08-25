package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import selenium.AmazonPage;
import selenium.SeleniumChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


@WebServlet("/MyApp_StartSeleniumTests")
public class MyApp_StartSeleniumTests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyApp_StartSeleniumTests() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		startSeleniumTests();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void startSeleniumTests()
	{
		WebDriver driver = SeleniumChromeDriver.initiateChromeDriver();
		driver.get("https://www.amazon.in");
		
		AmazonPage ap = PageFactory.initElements(driver, AmazonPage.class);
		ap.startExtract(driver);
	}

}
