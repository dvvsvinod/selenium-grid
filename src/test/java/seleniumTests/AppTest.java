package seleniumTests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.dockerwithgrid.DriverManager;
import selenium.pageFactory.LandingPage;


public class AppTest extends DriverManager{
	 
	@Test
	@Parameters({"browser","username","password"})
   public void testcase_01(@Optional("chrome") String browser,String username, String password) {
	   launchBrowser(browser);
	   getDriverPool().get("https://www.saucedemo.com/");
	   LandingPage lPage = new LandingPage();
	   lPage.loginToSwagLabs(username,password);
	   takeScreenshot();
	   closeBrowser();
	   
   }

}
