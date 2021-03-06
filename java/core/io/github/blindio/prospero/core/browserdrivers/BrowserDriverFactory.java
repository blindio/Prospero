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

import io.github.blindio.prospero.core.browserdrivers.phantomjs.PhantomJSInstaller;
import io.github.blindio.prospero.core.exceptions.ProsperoParseException;
import io.github.blindio.prospero.core.utils.Config;
import io.github.blindio.prospero.core.utils.Constants;
import io.github.blindio.prospero.core.utils.PropertiesConstants;
import io.github.blindio.prospero.core.utils.Constants.AssertionFramework;
import io.github.blindio.prospero.core.utils.Constants.Browser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import static io.github.blindio.prospero.core.utils.AssertJUnitParams.*;

public class BrowserDriverFactory {

    public static final char REMOTE_CONTROL_BROWSER_PREFIX = '*';
    public static final String CHROME_WEBDRIVER_SYS_OPTION = "webdriver.chrome.driver";
    public static final String IE_WEBDRIVER_SYS_OPTION = "webdriver.ie.driver";
    public static final String CHROME_CMD_LINE = "chrome.switches";

    protected static final String DEFAULT_IWEBDRIVER_URL = "http://localhost:3001/wd/hub";

    private static Logger logger = Logger.getLogger(BrowserDriverFactory.class);

    /**
     * Return a new BrowserDriver object. This is typically performed by
     * org.prospero.core.utils.Context.init()
     * 
     * @return
     */
    public static BrowserDriver getBrowserDriver(String url) {

	String driverName = Config
		.getString(PropertiesConstants.SELENIUM_DRIVER);
	BrowserDriver newBrowserDriver = null;

	if (driverName.equalsIgnoreCase(Constants.SeleniumDriver.REMOTECONTROL
		.toString())) {
	    newBrowserDriver = buildRemoteControlDriver(url);
	} else if (driverName
		.equalsIgnoreCase(Constants.SeleniumDriver.REMOTECONTROL_WITH_JQUERY
			.toString())) {
	    newBrowserDriver = buildRemoteControlWithJQueryDriver(url);
	} else if (driverName
		.equalsIgnoreCase(Constants.SeleniumDriver.WEBDRIVER_BACKED_SELENIUM
			.toString())) {
	    newBrowserDriver = buildWebDriverBackedSeleniumDriver(url);
	} else if (driverName
		.equalsIgnoreCase(Constants.SeleniumDriver.WEBDRIVER.toString())) {
	    newBrowserDriver = buildWebDriverDriver(url);
	} else {
	    fail("Unknown value for property: "
		    + PropertiesConstants.SELENIUM_DRIVER + " value: "
		    + driverName);
	}

	logger.debug("opening driver with url='" + url + "'");
	newBrowserDriver.open(url);
	logger.debug("driver opened");
	return newBrowserDriver;
    }

    /**
     * Return a new BrowserDriver object. This is typically performed by
     * org.prospero.core.utils.Context.init()
     * 
     * @return
     */
    public static BrowserDriver getBrowserDriver() {
	String siteUrl = Config.getString(PropertiesConstants.SITE_URL);
	return getBrowserDriver(siteUrl);
    }

    /**
     * builds a WebDriver backed BrowserDriver and sets the starting url to
     * siteURL
     * 
     * @param siteURL
     * @return
     */
    private static WebDriverDriver buildWebDriverDriver(String siteURL) {
	WebDriverDriver driver = null;
	WebDriver wd = buildWebDriver();
	driver = new WebDriverDriver(wd, siteURL);

	return driver;
    }

    /**
     * builds a WebDriverBackedSelenium BrowserDriver and sets the starting url
     * to siteURL
     * 
     * @param siteURL
     * @return
     */
    private static WebDriverBackedSeleniumDriver buildWebDriverBackedSeleniumDriver(
	    String siteURL) {
	WebDriverBackedSeleniumDriver driver = null;
	WebDriver wd = buildWebDriver();
	driver = new WebDriverBackedSeleniumDriver(wd, siteURL);

	return driver;
    }

    /**
     * builds a WebDriver object for the correct Browser based upon the property
     * 'browser'
     * 
     * @param siteURL
     * @return
     */
    private static WebDriver buildWebDriver() {
	WebDriver wd = null;
	Browser browser = getBrowserFromConfig();

	// TODO: this needs a lot more work with Capabilities
	if (browser == Browser.FIREFOX) {
	    wd = new FirefoxDriver();
	}

	else if (browser == Browser.HTMLUNIT) {
	    wd = new HtmlUnitDriver(true);
	}

	else if (browser == Browser.INTERNET_EXPLORER) {
	    System.setProperty(IE_WEBDRIVER_SYS_OPTION,
		    Config.getString(PropertiesConstants.DRIVER_LOCATION));

	    wd = new InternetExplorerDriver();
	}

	else if (browser == Browser.CHROME) {
	    System.setProperty(CHROME_WEBDRIVER_SYS_OPTION,
		    Config.getString(PropertiesConstants.DRIVER_LOCATION));

	    wd = new ChromeDriver();
	}

	else if (browser == Browser.ANDROID) {
	    if (Config.containsKey(PropertiesConstants.REMOTE_WEBDRIVER_URL)) {
		try {
		    wd = new RemoteWebDriver(
			    new URL(
				    Config.getString(PropertiesConstants.REMOTE_WEBDRIVER_URL)),
			    DesiredCapabilities.android());
		} catch (MalformedURLException mue) {
		    throw new ProsperoParseException(mue);
		}
	    } else {
		wd = new AndroidDriver();
	    }
	}

	else if (browser == Browser.IPHONE || browser == Browser.IPAD) {
	    DesiredCapabilities dc;
	    if (browser == Browser.IPHONE) {
		dc = DesiredCapabilities.iphone();
	    } else {
		dc = DesiredCapabilities.ipad();
	    }

	    try {
		wd = new RemoteWebDriver(new URL(Config.getString(
			PropertiesConstants.REMOTE_WEBDRIVER_URL,
			DEFAULT_IWEBDRIVER_URL)), dc);
	    } catch (MalformedURLException mue) {
		throw new ProsperoParseException(mue);
	    }
	}

	else if (browser == Browser.GHOSTDRIVER) {
	    // TODO
	    // Find open socket
	    // ServerSocket sock = new ServerSocket(0);
	    // int openPort = sock.getLocalPort();
	    // sock.close();

	    // String phantomjsServerStartCmd = "phantomjs";
	    // Runtime.getRuntime().exec();

	    String phantomJSExecutable = PhantomJSInstaller.getPhantomJS();
	    DesiredCapabilities dCaps = new DesiredCapabilities();
	    dCaps.setCapability(
		    PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
		    phantomJSExecutable);
	    dCaps.setJavascriptEnabled(true);
	    dCaps.setCapability("takesScreenshot", false);
	    wd = new PhantomJSDriver(dCaps);
	}

	if (browser.isWindowed()
		&& Config.containsKey(PropertiesConstants.BROWSER_WIDTH)
		&& Config.containsKey(PropertiesConstants.BROWSER_HEIGHT)) {
	    wd.manage().window().setSize(getBrowserDimensions());
	}

	return wd;
    }

    /**
     * builds a RemoteControl based BrowserDriver and sets the starting url to
     * siteURL
     * 
     * @param siteURL
     * @return
     */
    private static RemoteControlDriver buildRemoteControlDriver(String siteURL) {
	String seleniumHost = Config
		.getString(PropertiesConstants.REMOTECONTROL_SERVER_HOST);
	int seleniumPort = Config
		.getInt(PropertiesConstants.REMOTECONTROL_SERVER_PORT);

	String browserStartCommand = buildRemoteControlBrowserStartCommand();

	return new RemoteControlDriver(seleniumHost, seleniumPort,
		browserStartCommand, siteURL.toString());
    }

    /**
     * Gets the browser to use from the property 'browser'
     */
    private static Browser getBrowserFromConfig() {
	String browserString = Config.getString(PropertiesConstants.BROWSER);
	return Browser.getBrowser(browserString);
    }

    /**
     * Builds the start command string used by RemoteControl Drivers
     * 
     * @return
     */
    private static String buildRemoteControlBrowserStartCommand() {
	StringBuilder startCommand = new StringBuilder();

	String browserPath;
	try {
	    browserPath = Config.getString(PropertiesConstants.BROWSER);
	} catch (NoSuchElementException nsee) {
	    browserPath = null;
	}

	String browserString = getBrowserFromConfig().toString();

	if (browserString != null) {
	    startCommand.append(REMOTE_CONTROL_BROWSER_PREFIX);
	    startCommand.append(browserString);
	    if (browserPath != null) {
		startCommand.append(' ');
	    }
	}

	if (browserPath != null) {
	    startCommand.append(browserPath);
	}

	return startCommand.toString();
    }

    /**
     * builds a RemoteControl based BrowserDriver that used jquery for some of
     * its functionality and sets the starting url to siteURL
     * 
     * @param siteURL
     * @return
     */
    private static RemoteControlDriver buildRemoteControlWithJQueryDriver(
	    String siteURL) {
	RemoteControlDriver driver = null;

	String seleniumHost = Config
		.getString(PropertiesConstants.REMOTECONTROL_SERVER_HOST);
	int seleniumPort = Config
		.getInt(PropertiesConstants.REMOTECONTROL_SERVER_PORT);

	String browserStartCommand = buildRemoteControlWithJQueryBrowserStartCommand();

	driver = new RemoteControlDriverWithJQuery(seleniumHost, seleniumPort,
		browserStartCommand, siteURL.toString());

	return driver;
    }

    /**
     * Builds the start command string used by RemoteControl Drivers using
     * jquery for some functionality
     * 
     * @return
     */
    private static String buildRemoteControlWithJQueryBrowserStartCommand() {
	StringBuilder startCommand = new StringBuilder();

	String browserPath = Config.getString(PropertiesConstants.BROWSER);
	String browserString = Config.getString(PropertiesConstants.BROWSER);

	if (browserString != null) {
	    startCommand.append(REMOTE_CONTROL_BROWSER_PREFIX);
	    startCommand.append(browserString);
	    if (browserPath != null) {
		startCommand.append(' ');
	    }
	}

	if (browserPath != null) {
	    startCommand.append(browserPath);
	}

	return startCommand.toString();
    }

    /**
     * get the assertionFramework from the property 'assert'. the assertion
     * frameworks are either junit or testng
     * 
     * @return
     */
    public static AssertionFramework getAssertionFramework() {
	String assertionFrameworkString = Config
		.getString(PropertiesConstants.ASSERTION_FRAMEWORK);
	return AssertionFramework
		.getAssertionFramework(assertionFrameworkString);
    }

    /**
     * gets the Browser Window Width & Height dimensions (in pixels) from
     * PropertiesConstants
     * 
     * @return A new Dimension object with width & height specified
     */
    private static Dimension getBrowserDimensions() {
	int browserWidth = Integer.parseInt(Config
		.getString(PropertiesConstants.BROWSER_WIDTH));
	int browserHeight = Integer.parseInt(Config
		.getString(PropertiesConstants.BROWSER_HEIGHT));

	return new Dimension(browserWidth, browserHeight);
    }

}
