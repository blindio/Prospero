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
package org.prospero.core.browserdrivers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.thoughtworks.selenium.Selenium;

/**
 * Returns a BrowserDriver based upon the WebDriverBackedSelenium implementation
 * of the Selenium interface
 * 
 * @author tlittle
 */
public class WebDriverBackedSeleniumDriver extends RemoteControlDriver {

    protected static Logger logger = Logger
	    .getLogger(WebDriverBackedSeleniumDriver.class);

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
