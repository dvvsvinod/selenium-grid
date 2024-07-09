package selenium.dockerwithgrid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverManager extends LogManager implements DriverFactory{
	
	
	private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<WebDriver>();
	private WebDriverWait wait;
	
	private void setDriverPool(WebDriver driver) {
		driverPool.set(driver);
	}
	
	public static WebDriver getDriverPool() {
		return driverPool.get();
	}

	@Override
	public void launchBrowser(String browserType) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		switch(browserType) {
			case "chrome":
				capabilities.setBrowserName(browserType);
				break;
			case "edge":
				capabilities.setBrowserName(browserType);
				break;
			default:
				break;
		}
		try {
			this.setDriverPool(new RemoteWebDriver(new URL("http://localhost:4444"),capabilities));
			logger().info("Host Url - http://localhost:4444");
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendText(By element,String text) {
		getDriverPool().findElement(element).sendKeys(text);
		logger().info("Input value for element - "+ text+" elementINFO - "+element);
	}

	@Override
	public void click(By element) {
		getDriverPool().findElement(element).click();
		logger().info("Performed click for element: elementINFO - "+element);
	}

	@Override
	public void explicitWait_untilElementIsVisible(By element,Duration seconds) {
		wait = new WebDriverWait(getDriverPool(),seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		logger().info("Waited for element(Visibility): elementINFO - "+element);
	}

	@Override
	public void explicitWait_untilElementIsNotVisible(By element,Duration seconds) {
		wait = new WebDriverWait(getDriverPool(),seconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
		logger().info("Waited for element(InVisibility): elementINFO - "+element);
	}

	@Override
	public boolean elementIsDisplayed(By element) {
		try {
			return getDriverPool().findElement(element).isDisplayed();
		}catch(NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}	
	}

	@Override
	public String getElementText(By element) {
		return getDriverPool().findElement(element).getText();
	}

	@Override
	public String getElementAttribute(By element,String attrName) {
		return getDriverPool().findElement(element).getAttribute(attrName);
		
	}

	@Override
	public boolean elementIsDisplayed(By element, Duration seconds) {
		try {
			explicitWait_untilElementIsVisible(element,seconds);
			return getDriverPool().findElement(element).isDisplayed();
		}catch(NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
	}

	@Override
	public String getElementText(By element, Duration seconds) {
		explicitWait_untilElementIsVisible(element,seconds);
		return getDriverPool().findElement(element).getText();
		
	}

	@Override
	public String getElementAttribute(By element, String attrName, Duration seconds) {
			explicitWait_untilElementIsVisible(element,seconds);
			return getDriverPool().findElement(element).getAttribute(attrName);
	}
	
	
	public static void takeScreenshot() {
		TakesScreenshot exec = ((TakesScreenshot)getDriverPool());
		File outputFile = exec.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(outputFile, new File("target/screenshots/img_"+Math.random()*100+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void closeBrowser() {
		getDriverPool().close();
		
	}
	
	
}