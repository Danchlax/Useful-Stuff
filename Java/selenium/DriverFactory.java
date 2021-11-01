package monitora.qa.core;

import junit.framework.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

	private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
		@Override
		protected synchronized WebDriver initialValue() {
			return initDriver();
		}
	};

	private DriverFactory() {
	}

	public static WebDriver getDriver() {
		return threadDriver.get();
	}

	public static WebDriver initDriver() {
		WebDriver driver = null;
		switch (TestProperties.browser) {
			case FIREFOX: {
				//System.setProperty("webdriver.gecko.driver", "src/main/resources/Drivers/geckodriver.exe");
				FirefoxOptions options = new FirefoxOptions();
				if (TestProperties.RUN_ON_HEADLESS_MODE) {
					options.addArguments("--headless");
				}
				driver = new FirefoxDriver(options);
				break;
			}
			case CHROME: {
				//System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				if(TestProperties.USE_SPECIFIC_CHROME_PROFILE){
					options.addArguments("user-data-dir=" + TestProperties.ChromeProfilePath);
				}
				if(TestProperties.RUN_ON_HEADLESS_MODE) {
					options.addArguments("--window-size=1920,1080");
					options.addArguments("--disable-gpu");
					options.addArguments("--disable-extensions");
					options.addArguments("--start-maximized");
					options.addArguments("--headless");
				}
				driver = new ChromeDriver(options);
				break;
			}
		}
		driver.manage().window().setSize(new Dimension(1200, 765));

		return driver;
	}

	public static void killDriver() {
		WebDriver  driver = getDriver();
		if (driver != null) {
			driver.quit();
		}
		if(threadDriver != null) {
			threadDriver.remove();
		}
	}
}
