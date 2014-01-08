/*******************************************************************************
 * Copyright 2014 S. Thorson Little
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package io.github.blindio.prospero.core.browserdrivers;

import static io.github.blindio.prospero.core.utils.AssertJUnitParams.*;
import io.github.blindio.prospero.core.elements.TextLabel;
import io.github.blindio.prospero.core.utils.BySeleneseLocator;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * Browser Driver using the WebDriver libraries (i.e. Selenium 2)
 * 
 * @author tlittle
 */
public class WebDriverDriver extends BrowserDriver {

    public static final String ATTR_VALUE = "value";
    public static final int AT_SYMBOL_CODE_POINT = 64;
    public static final int EQUALS_SYMBOL_CODE_POINT = 61;
    public static final String GET_CONFIRMATION_JS = "return document.last_confirm";
    public static final String SELECT_STRATEGY_LABEL = "label";
    public static final String SELECT_STRATEGY_VALUE = "value";
    public static final String SELECT_STRATEGY_ID = "id";
    public static final String SELECT_STRATEGY_INDEX = "index";

    private WebDriver webdriver;

    public WebDriver getWebDriver() {
	return webdriver;
    }

    protected void setWebDriver(WebDriver wd) {
	webdriver = wd;
    }

    protected WebDriverDriver() {
	// DO NOTHING
    }

    public WebDriverDriver(WebDriver wd, String url) {
	super();
	webdriver = wd;
	setSiteUrl(url);
    }

    public void stopEverything() {
	if (getWebDriver() != null) {
	    getWebDriver().quit();
	    webdriver = null;
	}
    }

    public boolean isElementVisible(String locator) {
	try {
	    WebElement elem;
	    elem = getWebDriver().findElement(
		    BySeleneseLocator.seleneseLocator(locator));
	    return elem.isDisplayed();
	} catch (NoSuchElementException nsee) {
	    return false;
	}
    }

    public boolean isElementPresent(String locator) {
	try {
	    getWebDriver().findElement(
		    BySeleneseLocator.seleneseLocator(locator));
	    return true;
	} catch (NoSuchElementException nfe) {
	    return false;
	}
    }

    public void assertNoAlertPresent() {
	assertTrue(!isAlertPresent());
    }

    public boolean isAlertPresent() {
	boolean returnBool;
	try {
	    getWebDriver().switchTo().alert();
	    returnBool = true;
	} catch (NoAlertPresentException nape) {
	    returnBool = false;
	}

	return returnBool;
    }

    public void open(String url) {
	getWebDriver().get(url);

    }

    public void check(String locator) {
	getWebDriver().findElement(BySeleneseLocator.seleneseLocator(locator))
		.click();
    }

    public String getValue(String locator) {
	return getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator)).getAttribute(
		ATTR_VALUE);
    }

    public int getOptionCount(String locator) {
	return getWebDriver().findElements(
		BySeleneseLocator.seleneseLocator(locator)).size();
    }

    public void chooseCancelOnNextConfirmation() {
	getWebDriver().switchTo().alert().dismiss();
    }

    public void chooseOkOnNextConfirmation() {
	getWebDriver().switchTo().alert().accept();
    }

    public void click(String locator) {
	getWebDriver().findElement(BySeleneseLocator.seleneseLocator(locator))
		.click();
    }

    public String getAttribute(String attributeLocator) {
	int lastAtSymbol = attributeLocator.lastIndexOf(AT_SYMBOL_CODE_POINT);
	String locator = attributeLocator.substring(0, lastAtSymbol);
	String attribute = attributeLocator.substring(lastAtSymbol + 1);
	return getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator)).getAttribute(
		attribute);
    }

    public String getAlert() {
	return getWebDriver().switchTo().alert().getText();
    }

    public String getConfirmation() {
	return executeJavascript(GET_CONFIRMATION_JS);
    }

    public String getText(String locator) {
	return getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator)).getText();
    }

    public boolean isChecked(String locator) {
	return getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator)).isSelected();
    }

    public void select(String locator, String selection) {
	int equalsPos = selection.indexOf(EQUALS_SYMBOL_CODE_POINT);
	String selectOptionStrategy;
	String selectionValue;

	if (equalsPos > -1) {
	    selectOptionStrategy = selection.substring(0, equalsPos);
	    selectionValue = selection.substring(equalsPos + 1);
	} else {
	    selectOptionStrategy = SELECT_STRATEGY_LABEL;
	    selectionValue = selection;
	}

	Select select = new Select(getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator)));
	if (selectOptionStrategy.equalsIgnoreCase(SELECT_STRATEGY_LABEL)) {
	    select.selectByVisibleText(selectionValue);
	} else if (selectOptionStrategy.equalsIgnoreCase(SELECT_STRATEGY_VALUE)) {
	    select.selectByValue(selectionValue);
	} else if (selectOptionStrategy.equalsIgnoreCase(SELECT_STRATEGY_INDEX)) {
	    select.selectByIndex(Integer.parseInt(selectionValue));
	} else if (selectOptionStrategy.equalsIgnoreCase(SELECT_STRATEGY_VALUE)) {
	    fail("WebDriver does not support 'id' selection strategy");
	} else {
	    fail("Invalid selection strategy: " + selectOptionStrategy);
	}

    }

    public void type(String locator, String entry) {
	WebElement textBox = getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator));
	textBox.sendKeys("");
	textBox.clear();
	textBox.sendKeys(entry);
    }

    public void clickOkOnAlert() {
	getWebDriver().switchTo().alert().accept();
    }

    public void selectFrame(String locator) {
	int equalsPos = locator.indexOf(EQUALS_SYMBOL_CODE_POINT);
	String strategy = "";
	String value = null;

	if (equalsPos > -1) {
	    strategy = locator.substring(0, equalsPos);
	    value = locator.substring(equalsPos + 1);
	} else {
	    fail("Invalid locator: " + locator
		    + " locator strategy should read: 'index=int'");
	}

	if (strategy.equalsIgnoreCase(SELECT_STRATEGY_INDEX)) {
	    setWebDriver(getWebDriver().switchTo().frame(
		    Integer.parseInt(value)));
	} else {
	    fail("Invalid locator: " + locator
		    + " locator strategy should read: 'index=int'");
	}
    }

    public void assertLabelContainsText(TextLabel element, String expectedText) {
	assertTrue(element.getText().contains(expectedText));
    }

    public String executeJavascript(String javascript) {
	String returnVal = null;
	try {
	    JavascriptExecutor jse = (JavascriptExecutor) webdriver;
	    returnVal = jse.executeScript(javascript, (Object[]) null)
		    .toString();
	} catch (ClassCastException cce) {
	    // TODO
	}
	return returnVal;
    }

    public void waitForPageToLoad(String standardPageLoadWaitTime) {
	// Do Nothing WebDriver Blocks until page loads (let's hope)
    }

    public void waitForFrameToLoad(String frameAddress) {
	// Do Nothing WebDriver Blocks until page loads (let's hope)
    }

    public void reloadPage() {
	getWebDriver().navigate().refresh();
    }

    public void typeKeys(String locator, String entry) {
	getWebDriver().findElement(BySeleneseLocator.seleneseLocator(locator))
		.sendKeys(entry);
    }

    public String getHtmlSource() {
	return getWebDriver().getPageSource();
    }

    public String getCurrentUrl() {
	return getWebDriver().getCurrentUrl();
    }

    public void hover(String locator) {
	WebElement element = getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator));
	Actions builder = new Actions(getWebDriver());
	builder.moveToElement(element).build().perform();
    }

    public void shiftClick(String locator) {
	WebElement element = getWebDriver().findElement(
		BySeleneseLocator.seleneseLocator(locator));
	Actions builder = new Actions(getWebDriver());
	builder.keyDown(Keys.SHIFT).click(element).keyUp(Keys.SHIFT).build()
		.perform();
    }
}
