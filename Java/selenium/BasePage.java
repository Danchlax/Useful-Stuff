package monitora.qa.core;

import static monitora.qa.core.DriverFactory.getDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

public class BasePage {
	
	private final WebDriverWait wait = new WebDriverWait(getDriver(), TestProperties.TIMEOUT_SECONDS);
	private final Actions action = new Actions(getDriver());
	private static final JavascriptExecutor jsExec = (JavascriptExecutor) getDriver();
	private final EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(getDriver());
	
	public Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
	        .retryIfResult(Predicates.isNull())
	        .retryIfExceptionOfType(IOException.class)
	        .retryIfRuntimeException()
	        .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
	        .withStopStrategy(StopStrategies.stopAfterAttempt(20))
	        .build();

	protected void goToWebpage(String Url){
		getDriver().get(Url);
	}

	protected boolean elementExists(By locator){
		return !getDriver().findElements(locator).isEmpty();
	}

	public void closeTab(){
		getDriver().close();
	}

	protected void waitThenMoveCursorTo(By locator) {
		WebElement element = waitForAndGetWebElement(locator);
		action.moveToElement(element).perform();
	}
	
	protected void waitThenClickOn(By locator) {
		WebElement button = waitForAndGetWebElement(locator);
		button.click();
	}

	protected void clickOnElementIfItExists(By locator) {
		if(elementExists(locator)){
			waitThenClickOn(locator);
		}
	}
	
	protected void waitThenSendKeys(By locator, String input) {
		WebElement inputBox = waitForAndGetWebElement(locator);
		inputBox.clear();
		inputBox.sendKeys(input);
	}

	protected void waitThenUploadFile(By locator, String filePath) {
		WebElement inputBox = waitForAndGetWebElementSimple(locator);
		inputBox.sendKeys(filePath);
	}
	
	protected void waitThenSendKeysOnDateInput(By locator, String input) {
		WebElement inputBox = waitForAndGetWebElement(locator);
		inputBox.clear();
		inputBox.sendKeys(Keys.ARROW_LEFT);
		inputBox.sendKeys(Keys.ARROW_LEFT);
		inputBox.sendKeys(Keys.ARROW_LEFT);
		inputBox.sendKeys(input);
	}
	
	protected void waitThenSelectFromCombo(By locator, String selectedOption) {
		WebElement combo = waitForAndGetWebElement(locator);
		Select comboBox = new Select(combo);
		comboBox.selectByVisibleText(selectedOption);
	}
	
	protected String waitThenGetTextFrom(By locator) {
		waitForPresenceOf(locator);
		WebElement textElement = getDriver().findElement(locator);
		return textElement.getText();
	}

	protected double waitThenGetDoubleFrom(By locator) {
		waitForPresenceOf(locator);
		WebElement textElement = getDriver().findElement(locator);
		String rawText = textElement.getText();
		return Double.parseDouble(rawText.replaceAll("[$A-Za-z:,\\s]", ""));
	}
	
	protected List<String> waitThenGetAllTextFrom(By locator) {
		List<String> textList = new ArrayList<String>();
		waitForPresenceOf(locator);
		List<WebElement> textElements = getDriver().findElements(locator);
		for(WebElement textElement : textElements) {
			textList.add(textElement.getText());
		}
		return textList;
	}
	
	protected List<String> waitThenGetStringListFrom(By locator){
		List<WebElement> elementList = getDriver().findElements(locator);
		List<String> stringList = new ArrayList<String>();
		for(WebElement element : elementList) {
			stringList.add(element.getText());
		}
		return stringList;
	}
	
	protected String acceptAlertAndReturnMessage() {
		waitForAlertToBePresent();
		Alert alert = getDriver().switchTo().alert();
		String message = alert.getText();
		alert.accept();
		return message;
	}
	
	protected WebElement waitForAndGetWebElementSimple(By locator) {
		waitForPresenceOf(locator);
		return getDriver().findElement(locator);
	}

	protected WebElement waitForAndGetWebElement(By locator) {
		waitForPresenceOf(locator);
		waitForElementToBeClickable(locator);
		return getDriver().findElement(locator);
	}
	
	protected void waitForInvisibilityOf(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	protected void sendEnterKeyToInput(By locator) {
		WebElement inputBox = waitForAndGetWebElement(locator);
		inputBox.sendKeys(Keys.ENTER);
	}
	
	protected void waitForPresenceOf(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	protected void waitForElementToBeClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	protected void waitForAlertToBePresent() {
		wait.until(ExpectedConditions.alertIsPresent());
	}
	
	protected void switchToIframe(By locator) {
		WebElement iframe = waitForAndGetWebElement(locator);
		getDriver().switchTo().frame(iframe);
	}
	
	protected void returnToDefaultFrame() {
		getDriver().switchTo().defaultContent();
	}
	
	protected void switchToTab(int tabNumber) {
		ArrayList<String> newTab = new ArrayList<String>(getDriver().getWindowHandles());
	    getDriver().switchTo().window(newTab.get(tabNumber));
	}
	
	protected void switchToNewestTab() {
		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
	    getDriver().switchTo().window(tabs.get(tabs.size() - 1));
	}
	
	protected void scrollPageToTop() {
		jsExec.executeScript("window.scrollTo(0, 0)");
	}

	protected void scrollPageToBottom() {
		jsExec.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	protected void scrollPageToElement(By locator) {
		WebElement element = waitForAndGetWebElement(locator);
		jsExec.executeScript("arguments[0].scrollIntoView();", element);
	}

	protected void executeScript(String script) {
		eventFiringWebDriver.executeScript(script);
	}

	protected void waitThenJSClick(By locator) {
		WebElement button = waitForAndGetWebElement(locator);
		jsExec.executeScript("arguments[0].click();", button);
	}
	
	public void refreshBrowser() {
		getDriver().navigate().refresh();
	}
}
