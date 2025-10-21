package com.qa.opencart.utils;

import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.CustomException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

/* 
 * This is the  utility to be reused. static in methods and Driver prevents parallel execution
 */
public class ElementUtil {
	List<WebElement> list;
	private static final Logger log=LogManager.getLogger(ElementUtil.class);
	//Properties prop;
	JavaScriptUtil jutil;
	private Actions action;//action is coming from the private var, class var
	private WebDriver driver = null;//No need to specify WebDriver as pricate or public // Keep the WebDriver as private or default no one from outside of the class
	// should access, otherwise if
//u specify ElementUtil.driver.getTitle() from other class throws Null pointer

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		action=new Actions(driver);
		//prop=new Properties();
		jutil=new JavaScriptUtil(driver);
	}

	public WebDriver initDriver(String browser) {
		switch (browser.toLowerCase().trim()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new InternetExplorerDriver();
			break;
		default:
			System.out.println("Invalid Browser");
			throw new CustomException("-------------Invalid Browser----------");
		}
		return driver;

	}

	/*
	 * Check error handling of Url should not be blank
	 */

	private void urlCheck(String urlValue) {
		if (urlValue.length() == 0) {
			log.warn("Url should not be blank");
			throw new CustomException("Empty value");

		}

	}

	/*
	 * Check http in the 0th position in the url. Handle with exception if entered
	 * without http in the url
	 */
	private void httpCheck(String urlValue) {
		if (urlValue.indexOf("http") != 0) {
			log.warn(
					"Please check whether there is http in the 0th position in the url. Enter the correct valid url");
			throw new CustomException("Invalid url");
		}

	}

	public String getPageTitle() {
		String actual = driver.getTitle();
		log.info("Title is" + " " + actual);
		return actual;
	}

	public String launchUrl(String url) throws Exception {
		nullCheck(url);
		urlCheck(url);
		httpCheck(url);
		driver.get(url);

		String actTitle = getPageTitle();

		/*
		 * if(actTitle.equals("Google")) { System.out.println("Correct site is launched"
		 * +" " +actTitle); } else { System.out.println("Incorrect site is launched"
		 * +" " +actTitle); }
		 */

		getPageUrl();
		return actTitle;

	}

	public void nullCheck(String value) {
		if (value == null) {
			log.info("You entered null value in the url of URL");
			throw new CustomException("Value is null");
		}
	}

	public String getPageUrl() {
		String url = driver.getCurrentUrl();
		log.info("You are navigated to this url" + " " + url);
		return url;
	}

	public void closeBrowser() {
		if (driver != null) {
			driver.close();
		}
	}

	public void quitBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}

	public String launchUrl(URL url) // Method overloading
	{
		String stUrl = String.valueOf(url);
		nullCheck(stUrl);
		urlCheck(stUrl);
		httpCheck(stUrl);
		driver.navigate().to(stUrl);
		String actTitle = getPageTitle();
		getPageUrl();
		return actTitle;
	}

	/*
	 * identify element by locator by passing locator to the method
	 */
	@Step("finding the element using:{0}")
	public WebElement getElement(By locator)// m2 method
	{
		WebElement element = driver.findElement(locator);
		
			if(Boolean.parseBoolean(DriverFactory.highlight))
			{	
				jutil.flash(element);
			}
		
		return element;
	}

	/*
	 * Enter text to the webelement, by passing locator & value to be passed to the
	 * method.
	 */
	public void doEnterText(By locator, String value) {
		WebElement element = driver.findElement(locator);
		element.sendKeys(value);
		
	}

	/*
	 * Submit button
	 */
	@Step("clicking on element using : {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void nullCharSeqorStrWhenSendKeys(String val) {
		if (val == null) {
			throw new CustomException("Value should not be null");
		}
	}

	public String getDomAtMethod(By locator, String attrValue) {
		nullCharSeqorStrWhenSendKeys(attrValue);
		String value = getElement(locator).getDomAttribute(attrValue);
		return value;
	}

	public String getDomProperMethod(By locator, String propertyValue) {
		nullCharSeqorStrWhenSendKeys(propertyValue);
		String value = getElement(locator).getDomProperty(propertyValue);
		return value;
	}
	@Step("Entering value:{1} into element: {0}")
	public void doSendKeys(By locator, String value) {
		if (isElementDisplayed(locator)) {
			nullCharSeqorStrWhenSendKeys(value);
			getElement(locator).clear();
			getElement(locator).sendKeys(value);
		} else {
			log.info("Element is not displayed in the page");
		}

	}

	public void doSendKeysWithChar(By locator, CharSequence... value1) // comma seperated values can be entered such as
																		// address in a textbox
	{
		if (isElementDisplayed(locator))// It throws exception in this line when calling isElementDisplayed method, so
										// this m1 should be handled with try, catch
		{
			// nullCharSeqorStrWhenSendKeys(value1);//It does not work for CharSequence null
			// check
			System.out.println("Element is displayed" + "\t" + isElementDisplayed(locator));
			getElement(locator).clear();
			getElement(locator).sendKeys(value1);
		} else {

			System.out.println("------FAIL---");// we should not place throw statement for custom exception here

		}

	}

	public Boolean isElementDisplayed(By locator)// m1 method calling m2 method. m1 should be handled with try, catch
	{
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {

			log.info("Element is not displayed in the page using locator" + "\t" + locator);
			return false;
		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);

	}

	/*
	 * Iterate to list and interact with the element for each
	 */
	public void iterateList(By locator) {
		list = getElements(locator);
		getCount(locator);
		for (WebElement e : list) {
			if (e.getText().length() != 0)// To avoid the empty list . If u want to print the index of empty list, use
											// for(int i=0) etc
			{
				System.out.println("Links in the page" + "\t" + e.getText());
			}
		}
	}

	/*
	 * Get count in the list of Webelements
	 */
	public int getCount(By locator) {
		try
		{
		System.out.println("Get Count of elements" +getElements(locator).size());
		return getElements(locator).size();
		}
		catch(Exception e)
		{
			return 0;
		}
			
		
		
	}

	public boolean checkElementDisplayed(By locator) {

		if (getElements(locator).size() == 1) {
			log.info("Element is displayed with locator" + locator + "on the page one time");
			return true;
		}
		return false;
	}

	public boolean checkElementDisplayed(By locator, int expectedCount)// overloaded method.If login, forgot password
																		// link appears twice or more times in the page,
																		// use this,
	{
		if (getElements(locator).size() == expectedCount) {
			log.info(
					"Element is displayed with locator" + locator + "\t" + expectedCount + " times on the page");
			return true;
		}
		return false;
	}

	public void doAutoSuggestionClick(By locator, String value) {
		List<WebElement> elements = getElements(locator);
		for (WebElement e : elements) {
			System.out.println("Retrieve the text from the list" + e.getText());
			if (e.getText().equals(value)) {
				e.click();
				System.out.println("You clicked on locator value" + "\t" + value);
				break;
			}
		}
	}

	public boolean doSelectByIndex(By locator, int index) {
		try {
			Select sel = new Select(driver.findElement(locator));
			sel.selectByIndex(index);
			return true;
		} catch (Exception e) {
			// throw new CustomException("---Not able to find option values for select
			// class. No Dropdown---");
			System.out.println(index + " is not present in the dropdown");
			return false;
		}
	}

	public boolean doSelectByValue(By locator, String value) {
		try {
			Select sel = new Select(driver.findElement(locator));
			sel.selectByValue(value);
			return true;
		} catch (Exception e) {
			// throw new CustomException("---Not able to find option values for select
			// class. No Dropdown---");
			System.out.println(value + " is not present in the dropdown");
			return false;
		}
	}

	public boolean doSelectByVisibleText(By locator, String visibleText) {
		try {
			Select sel = new Select(driver.findElement(locator));
			sel.selectByVisibleText(visibleText);
			return true;
		} catch (Exception e) {
			// throw new CustomException("---Not able to find option values for select
			// class. No Dropdown---");
			System.out.println(visibleText + " is not present in the dropdown");
			return false;

		}
	}

	/*
	 * JQuery multiselection dropdown works for any dropdown that does not have
	 * select options in the html dom
	 */
	public void doSelectChoices(By dropDownClick, By choi, String... choiceValue) throws Exception {
		getElement(dropDownClick).click();
		Thread.sleep(2000);
		List<WebElement> list = getElements(choi);

		System.out.println("Choices count" + "\t" + list.size());

		if (choiceValue[0].equalsIgnoreCase("all")) {
			for (WebElement e : list) {
				e.click();
			}
		} else {
			for (WebElement e : list) {
				String text = e.getText();
				log.info("The list of items" + "\n" + text);

				for (String value : choiceValue) {
					if (text.equals(value)) {

						e.click();
						break;
					}
				}

			}
		}

	}

	/*
	 * String based locator common generic method
	 */
	public By getBy(String locatorType, String locatorValue) {
		By locator = null;
		switch (locatorType.toUpperCase()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "CLASS":
			locator = By.className(locatorValue);
			break;
		default:
			log.warn("Please send the correct locatorType");
		}
		return locator;
	}

	public void doMouseOver(By locator) {
		//Actions action = new Actions(driver);
		// Instead of creating object again and again for the Actions class, create one
		// common Actions object and access inside the method.
		action.moveToElement(getElement(locator)).build().perform();// perform() alone contains both build & perform

	}

	public void doHandlingParentSubMenuTwoLevel(By locator) throws Exception {
		doMouseOver(locator);
		Thread.sleep(1000);
		doClick(locator);
	}

	public void handleFourLevelsOfAction(By locatorLevel1, By locatorLevel2, By locatorLevel3, By locatorLevel4)
			throws Exception {
		doClick(locatorLevel1);
		Thread.sleep(1000);
		doMouseOver(locatorLevel2);
		Thread.sleep(1000);
		doMouseOver(locatorLevel3);
		Thread.sleep(1000);
		doClick(locatorLevel4);

	}
	/*
	 * Handling multiple actions and return the actions to an interface Action
	 */

	public Action handlemultipleActions(By source, By target) {
		//Actions action = new Actions(driver);
		Action act = action // Action is an interface, whereas Actions are a class that has multiple methods
							// scrollToElement, moveToElement, mouseOver etc
				.clickAndHold(getElement(source)).moveToElement(getElement(target)).release().build();
		act.perform();
		return act;

	}

	public void doScrollByActions() throws Exception {
		//Actions action = new Actions(driver);
		action.sendKeys(Keys.END).perform();
		Thread.sleep(2000);
		action.sendKeys(Keys.HOME).perform();
		Thread.sleep(2000);
	}

	public void doScrollToElement(By locator) throws Exception {
		//Actions action = new Actions(driver);
		Action act = action.scrollToElement(getElement(locator)).pause(200).build();
		performActionInterafterBuild(act);
	}

	public void randomStringGenerationForUsername() {
		/*
		 * It's not working. StringUtils class is from Apache Common Lang package. The
		 * below random generation of strings using nextAlphabetic & nextAlphanumeric is
		 * not working String generateNum=StringUtils.nextAlphabetic(9); String
		 * generateStr=StringUtils.nextAlphanumeric(12);
		 */
		String[] arr = { "ABAYADF", "BSDSDSD", "CGSSDSD", "DSDSDSD" };

		// RandomStringUtils.random(7, 'z[]', 0-9); Not working
		Random random = new Random();
		System.out.println("Random integers" + "\t" + random.nextInt(100));

		// randomly selects an index from the arr
		int select = random.nextInt(arr.length);

		// prints out the value at the randomly selected index
		System.out.println("Random String selected: " + arr[select]);

		// random.next(200); - Not working
		// RandomStringUtils.randomAlphaNumeric(12); Math.random()
	}

	/*
	 * Inputting char by char in the text field
	 */
	public void inputtingCharbyCharSendKeys(By locator, String value, Long pauseTime) {

		Action acths;
		char ch[] = value.toCharArray();
		//Actions action = new Actions(driver);
		// Char Sequence holds String, StringBuffer, StringBuilder. So below ch only
		// gives error, so charSequence holds String values as String.valueOf(ch)
		for (char c : ch) {
			acths = action.sendKeys(getElement(locator), String.valueOf(c)) // to get inputted character by character
					.pause(pauseTime)

					.build();

			performActionInterafterBuild(acths);

		}

	}

	public void createTabActionAccessibility(By locator, Long pauseTime) {
		//Actions action = new Actions(driver);

		Action a = action.sendKeys(getElement(locator), Keys.TAB).pause(900)

				.sendKeys(Keys.TAB).pause(200).sendKeys(Keys.TAB).pause(200).sendKeys(Keys.TAB).pause(200)
				.sendKeys(Keys.TAB).pause(200).sendKeys(Keys.TAB).pause(200).sendKeys(Keys.TAB).pause(200)
				.sendKeys(Keys.TAB).pause(200).sendKeys(Keys.TAB).pause(200).sendKeys(Keys.SPACE).pause(200)
				.sendKeys(Keys.ENTER)

				.build();

		performActionInterafterBuild(a);
	}

	public void performActionInterafterBuild(Action a) {
		a.perform();

	}
	/*
	 * For rightclick, there is action.contextClick()
	 */

	/*
	 * Switch to alert and handle
	 */
	public void handleAlert(By locator, String value) throws Exception {
		Alert alert = driver.switchTo().alert();
		System.out.println(value + "\t" + alert.getText());
		alert.dismiss();

	}

	public void handlePromptSendkeys(By locator, String value) throws Exception {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys("Nazima");
		Thread.sleep(1000);
		alert.accept();
	}
	/*
	 * For file upload, tab tab to the nearest Browse textbox and sendKeys of the
	 * file to upload in the textbox. No need to click on Browse option
	 */
	/*
	 * Window Handling
	 */

	public void handlingWindowwithslee() {

		String parent = driver.getWindowHandle();
		log.info("Parent window" + parent);
		Set<String> handles = driver.getWindowHandles();
		Iterator it = handles.iterator();
		while (it.hasNext()) {
			String child = (String) it.next();
			driver.switchTo().window(child);
			System.out.println("Child Window Id" + child + "\tTitle of the child" + driver.getTitle());
			if (!parent.equals(child)) {
				driver.close();
			}
		}
	}
	/*
	 * Wait-Frame Handling
	 */
	public void singleFrameByLocatorSendParentGText(By frameId, By firstName, By verifyParentHeader,String value,int timeout) {
		//driver.switchTo().frame(getElement(frameId)); // frame-one748593425
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
		getElement(firstName).sendKeys(value);
		driver.switchTo().parentFrame(); // defaultContent() also works fine or with parentFrame()
		System.out.println(getElement(verifyParentHeader).getText());
	}
	public void singleFrameByFrameLocatorSendParentGText(String frameIdOrName, By firstName, By verifyParentHeader,String value,int timeout) {
		//Identify frame by id or name which is a string
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));
		getElement(firstName).sendKeys(value);
		driver.switchTo().parentFrame(); // defaultContent() also works fine or with parentFrame()
		System.out.println(getElement(verifyParentHeader).getText());
	}
	public void singleFrameByFrameIndexSendParentGText(int index, By firstName, By verifyParentHeader,String value,int timeout) {
		//Identify frame by id or name which is a string
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
		getElement(firstName).sendKeys(value);
		driver.switchTo().parentFrame(); // defaultContent() also works fine or with parentFrame()
		System.out.println(getElement(verifyParentHeader).getText());
	}
	public void singleFrameByFrameWebElmtSendParentGText(WebElement element, By firstName, By verifyParentHeader,String value,int timeout) {
		//Identify frame by id or name which is a string
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		getElement(firstName).sendKeys(value);
		driver.switchTo().parentFrame(); // defaultContent() also works fine or with parentFrame()
		System.out.println(getElement(verifyParentHeader).getText());
	}

	//*******
	
	public void navigateForwardFromFrame(By locator, By locatorSendKeys, String valueSendKeys) {
		driver.switchTo().frame(getElement(locator));
		getElement(locatorSendKeys).sendKeys(valueSendKeys);
	}

	public void navigateBackwardFromFrame(By locator, String valueSendKeys) {
		driver.switchTo().parentFrame();
		getElement(locator).sendKeys(valueSendKeys);
	}

	/*
	 * Wait-Window Handling
	 */
	public void waitWithWindowHandle(int expectedWindowsToBeOpened, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindowsToBeOpened));
			System.out.println("Number of Windows  expected are correct");

		} catch (Exception e) {
			System.out.println("Number of Windows are incorrect");
			throw new CustomException("Please enter correct number of windows opened");
		}
	}
	/*
	 * Wait-Visibility of single unique element
	 */
	@Step("wait for element using: {0} and timeout : {1}")
	public WebElement waitForElementVisible(By locator,int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight))
		{
			jutil.flash(element);
		}
		log.info("Visibility of element" +element);
		return element;
	}
	
	/*
	 * Wait visibility for multiple elmts in the page
	 */
	public List<WebElement> waitForAllElementsVisible(By locator,int timeout)
	{
		try
		{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		catch(Exception e)
		{
			return Collections.EMPTY_LIST;
		}
		
	}
	
	public WebElement waitForElementPresence(By locator,int timeout)
	{//chks only the presence in DOM. Does not chk for visibility in the page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public String waitForUrlContains(String fractionUrl,int timeout)
	{
		try
		{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.urlContains(fractionUrl));
		log.info("Title is" +driver.getCurrentUrl());
		return driver.getCurrentUrl();
		}
		catch(Exception e)
		{
			return null; 
		}
	}
	
	public String waitForUrlToBe(String exactUrl,int timeout)
	{
		try
		{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.urlToBe(exactUrl));
		log.info("Title is" +driver.getCurrentUrl());
		return driver.getCurrentUrl();
		}
		catch(Exception e)
		{
			return null; 
		}
	}
	public String waitForTitleContains(String title,int timeout)
	{
		try
		{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
		}
		catch(Exception e)
		{
			return null; 
		}
	}
	
	public String waitForTitleIs(String exactTitle,int timeout)
	{
		try
		{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.titleIs(exactTitle));
		log.info("Title is" +driver.getTitle());
		return driver.getTitle();
		}
		catch(Exception e)
		{
			return null; 
		}
	}
	/*
	 * Wait->Alert
	 */
	public Alert waitForAlert(int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	public void cancelAlert(int timeout)
	{
		waitForAlert(timeout).dismiss();
		
	}
	public void acceptAlert(int timeout)
	{
		waitForAlert(timeout).accept();
		
	}
	public void sendKeys(int timeout,String value)
	{
		waitForAlert(timeout).sendKeys(value);
		
	}
	
	public void getText(int timeout)
	{
		waitForAlert(timeout).getText();
		
	}
	
	/*
	 * SendKeys with wait
	 */
	public void waitWhileSendKeys(By locator,int timeout,CharSequence... value)
	{
		waitForElementVisible(locator,timeout).sendKeys(value);;
	}
	
	/*
	 * ClickWithWait
	 */
	public void clickWithWait(By locator,int timeout)
	{
		waitForElementVisible(locator,timeout).click();
	}
	
	/*
	 * Element to be visible and enabled while click- This method is not working(elementToBeClickable)
	 */
	@Step("wait for element using: {0} and timeout : {1}")
	public void clickWhenReady(By locator,int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	/*
	 * Element to be visible and enabled while click
	 */
	public void clickWhenReadyByWebElement(WebElement element,int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/*
	 * Click on the element
	 */
	public void doClickWebElement(WebElement element) 
	{
		
		element.click();
	}
	
	/*
	 * Get text
	 */
	@Step("fetching the element text using: {0}")
	public String doElementGetText(By locator)
	{
	String text=driver.findElement(locator).getText();
	System.out.println("element text =>" +text);
	return text;
		 
	}
}
