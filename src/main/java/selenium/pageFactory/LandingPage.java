package selenium.pageFactory;


import org.openqa.selenium.By;

import selenium.dockerwithgrid.DriverManager;

public class LandingPage extends DriverManager{
	
	
	
	private By swagLabsLogo = By.className("login_logo");
	private By swagLabsUsername = By.cssSelector("#user-name");
	private By swagLabsPassword = By.cssSelector("#password");
	private By swagLabsLoginButton = By.id("login-button");
	
	
	
	public void enterUsername(String userName) {
		sendText(swagLabsUsername, userName);
	}
	
	public void enterPassword(String password) {
		sendText(swagLabsPassword, password);
	}
	
	public void clickLoginCta() {
		click(swagLabsLoginButton);
	}
	
	public void swagLogoIsDisplayed() {
		elementIsDisplayed(swagLabsLogo);
	}
	
	public void loginToSwagLabs(String userName,String password) {
		swagLogoIsDisplayed();
		enterUsername(userName);
		enterPassword(password);
		clickLoginCta();
	}
	

}
