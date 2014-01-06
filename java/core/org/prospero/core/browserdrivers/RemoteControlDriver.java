package org.prospero.core.browserdrivers;

import org.apache.log4j.Logger;

import org.prospero.core.elements.TextLabel;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import static org.prospero.core.utils.AssertJUnitParams.*;

/**
 * Browser driver implemented using Selnium Remote Control (i.e. selenium 1)
 * 
 * @author tlittle
 */
public class RemoteControlDriver extends BrowserDriver {
	
	protected static Logger logger = Logger.getLogger(RemoteControlDriver.class);
	private Selenium selenium;
	
	private String serverHost;
	private int serverPort;
	private String browserStartCommand;
	
	protected RemoteControlDriver() {
		// USED ONLY FOR DERIVED CLASSES
	}
	
	protected RemoteControlDriver(String host, int port, String startCommand, String url) {
		serverHost = host;
		serverPort = port;
		browserStartCommand = startCommand;
		setSiteUrl(url);
		startSelenium();
	}
	
	protected Selenium getSelenium() {
		if (selenium == null) {
			startSelenium();
		}
		return selenium;
	}
	
	protected void setSelenium(Selenium selenium) {
		this.selenium = selenium;
	}
	
	protected void startSelenium() {
		selenium = new DefaultSelenium(serverHost, serverPort, browserStartCommand, getSiteUrl());
		selenium.start();
	}
	
	public void stopEverything() {
		killSelenium();
		// SeleniumProxySingleton.stopJettyProxy();
	}
	
	public void killSelenium() {
		if (selenium != null) {
			selenium.close();
			selenium.stop();
			selenium = null;
		}
	}
	
	public void restartSelenium() {
		killSelenium();
		startSelenium();
	}
	
	public boolean isElementVisible(String locator) {
		// TODO why didnt I use isElementVisible
		// return isElementPresent( locator );
		return getSelenium().isVisible(locator);
	}
	
	public void assertNoAlertPresent() {
		assertTrue(!getSelenium().isAlertPresent());
	}
	
	public void open(String url) {
		getSelenium().open(url);
		getSelenium().getEval(EVAL_RESIZE);
		getSelenium().getEval(EVAL_MOVE);
	}
	
	public void check(String locator) {
		getSelenium().check(locator);
	}
	
	public boolean isElementPresent(String locator) {
		return getSelenium().isElementPresent(locator);
	}
	
	public String getValue(String locator) {
		return getSelenium().getValue(locator);
	}
	
	public int getOptionCount(String locator) {
		fail("getOptionCount is not supported");
		return 0;
	}
	
	public void chooseCancelOnNextConfirmation() {
		getSelenium().chooseCancelOnNextConfirmation();
	}
	
	public void chooseOkOnNextConfirmation() {
		getSelenium().chooseOkOnNextConfirmation();
	}
	
	public void click(String locator) {
		getSelenium().click(locator);
	}
	
	public String getAlert() {
		return getSelenium().getAlert();
	}
	
	public String getAttribute(String attributeLocator) {
		return getSelenium().getAttribute(attributeLocator);
	}
	
	public String getConfirmation() {
		return getSelenium().getConfirmation();
	}
	
	public String getText(String locator) {
		return getSelenium().getText(locator);
	}
	
	public boolean isChecked(String locator) {
		return getSelenium().isChecked(locator);
	}
	
	public void select(String locator, String selection) {
		getSelenium().select(locator, selection);
		
	}
	
	public void type(String locator, String entry) {
		getSelenium().type(locator, entry);
	}
	
	public void clickOkOnAlert() {
		getAlert();
	}
	
	public void selectFrame(String locator) {
		getSelenium().selectFrame(locator);
	}
	
	public void assertLabelContainsText(TextLabel element, String expectedText) {
		assertTrue(element.getText().contains(expectedText));
	}
	
	public String executeJavascript(String javascript) {
		return getSelenium().getEval(javascript);
	}
	
	public void waitForPageToLoad(String standardPageLoadWaitTime) {
		getSelenium().waitForPageToLoad(standardPageLoadWaitTime);
	}
	
	public void waitForFrameToLoad(String frameAddress) {
		getSelenium().waitForFrameToLoad(frameAddress, STANDARD_PAGE_LOAD_WAIT_TIME);
	}
	
	public void reloadPage() {
		getSelenium().refresh();
	}
	
	public void typeKeys(String locator, String entry) {
		getSelenium().typeKeys(locator, entry);
	}
	
	public String getHtmlSource() {
		return getSelenium().getHtmlSource();
	}
	
	public String getCurrentUrl() {
		return getSelenium().getLocation();
	}
	
	public void hover(String locator) {
		getSelenium().mouseOver(locator);
	}
	
	public void shiftClick(String locator) {
		fail("shiftClick is not supported");
	}
}
