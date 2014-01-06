package org.prospero.core.browserdrivers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.thoughtworks.selenium.Selenium;

/**
 * Returns a BrowserDriver based upon the WebDriverBackedSelenium implementation of the Selenium interface
 * 
 * @author tlittle
 */
public class WebDriverBackedSeleniumDriver extends RemoteControlDriver {
	
	protected static Logger logger = Logger.getLogger(WebDriverBackedSeleniumDriver.class);
	
	protected WebDriverBackedSeleniumDriver(WebDriver wd, String url) {
		super();
		setSiteUrl(url);
		Selenium wdbs = new WebDriverBackedSelenium(wd, url);
		setSelenium(wdbs);
		startSelenium();
	}
	
	@Override
	protected void startSelenium() {
		// TODO: Why is this commented out?
		// getSelenium().start();
	}
}
