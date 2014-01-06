package org.prospero.core.utils;

import org.apache.log4j.Logger;

public class Constants {
	
	private static final Logger logger = Logger.getLogger(Constants.class);
	
	public static final String DRIVER_WEBDRIVER = "webdriver";
	public static final String DRIVER_REMOTECONTROL = "remotecontrol";
	public static final String DRIVER_REMOTECONTROL_WITH_JQUERY = "remotecontrolwithjquery";
	public static final String DRIVER_WEBDRIVER_BACKED_SELENIUM = "webdriverbackedselenium";
	
	public static final String BROWSER_FIREFOX = "firefox";
	public static final String BROWSER_INTERNET_EXPLORER = "iexplore";
	public static final String BROWSER_CHROME = "chrome";
	public static final String BROWSER_HTMLUNIT = "htmlunit";
	public static final String BROWSER_ANDROID = "android";
	public static final String BROWSER_IPHONE = "iphone";
	public static final String BROWSER_IPAD = "ipad";
	public static final String BROWSER_PHANTOMJS = "phantomjs";
	
	public enum SeleniumDriver {
		REMOTECONTROL(DRIVER_REMOTECONTROL),
		REMOTECONTROL_WITH_JQUERY(DRIVER_REMOTECONTROL_WITH_JQUERY),
		WEBDRIVER(DRIVER_WEBDRIVER),
		WEBDRIVER_BACKED_SELENIUM(DRIVER_WEBDRIVER_BACKED_SELENIUM);
		
		String driverString;
		
		SeleniumDriver(String driver) {
			driverString = driver;
		}
		
		public String toString() {
			return driverString;
		}
	}
	
	public enum AssertionFramework {
		JUNIT, TESTNG;
		
		public static AssertionFramework getAssertionFramework(String framework) {
			AssertionFramework returnFramework = null;
			
			if (framework.equalsIgnoreCase(JUNIT.toString())) {
				returnFramework = JUNIT;
			}
			else if (framework.equalsIgnoreCase(TESTNG.toString())) {
				returnFramework = TESTNG;
			}
			else {
				logger.warn("Invalid AssertionFramework \"" + framework + "\" Use values (" +
						TESTNG.toString() + ", " + JUNIT.toString() + ") returning null");
			}
			
			return returnFramework;
		}
	}
	
	public enum Browser {
		INTERNET_EXPLORER(BROWSER_INTERNET_EXPLORER, true),
		FIREFOX(BROWSER_FIREFOX, true),
		CHROME(BROWSER_CHROME, true),
		HTMLUNIT(BROWSER_HTMLUNIT, false),
		ANDROID(BROWSER_ANDROID, false),
		IPHONE(BROWSER_IPHONE, false),
		IPAD(BROWSER_IPAD, false),
		GHOSTDRIVER(BROWSER_PHANTOMJS, false);
		
		String browserString;
		boolean isWindowed;
		
		Browser(String browser, boolean isWindowed) {
			this.browserString = browser;
			this.isWindowed = isWindowed;
		}
		
		public static Browser getBrowser(String browserString) {
			Browser browser = null;
			if (browserString.equalsIgnoreCase(CHROME.toString())) {
				browser = CHROME;
			}
			else if (browserString.equalsIgnoreCase(FIREFOX.toString())) {
				browser = FIREFOX;
			}
			else if (browserString.equalsIgnoreCase(INTERNET_EXPLORER.toString())) {
				browser = INTERNET_EXPLORER;
			}
			else if (browserString.equalsIgnoreCase(HTMLUNIT.toString())) {
				browser = HTMLUNIT;
			}
			else if (browserString.equalsIgnoreCase(ANDROID.toString())) {
				browser = ANDROID;
			}
			else if (browserString.equalsIgnoreCase(IPHONE.toString())) {
				browser = IPHONE;
			}
			else if (browserString.equalsIgnoreCase(IPAD.toString())) {
				browser = IPAD;
			} 
			else if (browserString.equalsIgnoreCase(GHOSTDRIVER.toString()) 
					|| browserString.equalsIgnoreCase("GHOSTDRIVER")) {
				browser = GHOSTDRIVER;
			} 
			else {
				logger.fatal("Invalid browser: " + browserString + " valid values: " + CHROME.toString() +
						", " + FIREFOX.toString() + ", " + INTERNET_EXPLORER.toString() + ", " +
						HTMLUNIT.toString());
			}
			return browser;
		}
		
		public String toString() {
			return browserString;
		}
		
		public boolean isWindowed() {
			return isWindowed;
		}
	}
	
	public static final long RANDOM_SEED = 2112;
	
}
