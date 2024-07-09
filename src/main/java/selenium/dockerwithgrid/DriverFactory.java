package selenium.dockerwithgrid;

import java.time.Duration;

import org.openqa.selenium.By;

public interface DriverFactory {
	
	
	void launchBrowser(String browserType);
	void click(By element);
	void sendText(By element, String text);
	void explicitWait_untilElementIsVisible(By element, Duration seconds);
	void explicitWait_untilElementIsNotVisible(By element, Duration seconds);
	boolean elementIsDisplayed(By element);
	String getElementText(By element);
	String getElementAttribute(By element, String attrName);
	boolean elementIsDisplayed(By element,Duration seconds);
	String getElementText(By element,Duration seconds);
	String getElementAttribute(By element,String attrName,Duration seconds);
	void closeBrowser();
}
