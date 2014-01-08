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

import org.prospero.core.browserdrivers.remoteControl.LocatorStrategyIdentifier;
import org.prospero.core.utils.JqueryCodeFactory;

/**
 * Browser driver implemented using Selenium Remote Control (i.e. selenium 1)
 * Uses JQuery for location, but forces the use of css selectors rather than
 * Selenese Selectors
 * 
 * @author tlittle
 */
public class RemoteControlDriverWithJQuery extends RemoteControlDriver {

    protected static Logger logger = Logger
	    .getLogger(RemoteControlDriverWithJQuery.class);

    // private static final int MS_PER_SECOND = 1000;
    // public static final String CSS_PREFIX = "css=";

    protected RemoteControlDriverWithJQuery(String host, int port,
	    String startCommand, String url) {
	super(host, port, startCommand, url);
    }

    public boolean isElementVisible(String cssLocator) {
	injectJqueryIfAbsent();
	return Boolean.valueOf(executeJavascript(JqueryCodeFactory
		.getVisibilityCode(cssLocator)));
    }

    public void open(String url) {
	getSelenium().open(url);
	injectJqueryIfAbsent();
    }

    public void check(String locator) {
	getSelenium().check(
		LocatorStrategyIdentifier.prepareforSelenium(locator));
    }

    public boolean isElementPresent(String locator) {
	if (LocatorStrategyIdentifier.isNonSeleniumCss(locator))
	    return isElementVisible(locator);
	return getSelenium().isElementPresent(
		LocatorStrategyIdentifier.prepareforSelenium(locator));
    }

    public String getValue(String locator) {
	return getSelenium().getValue(
		LocatorStrategyIdentifier.prepareforSelenium(locator));
    }

    public void click(String locator) {
	if (LocatorStrategyIdentifier.isNonSeleniumCss(locator))
	    executeJavascript(JqueryCodeFactory.getClickCode(locator));
	else {
	    String preparedLocator = LocatorStrategyIdentifier
		    .prepareforSelenium(locator);
	    getSelenium().click(preparedLocator);
	}
    }

    public String getText(String locator) {
	if (LocatorStrategyIdentifier.isNonSeleniumCss(locator))
	    return executeJavascript(JqueryCodeFactory.getTextCode(locator));
	return getSelenium().getText(
		LocatorStrategyIdentifier.prepareforSelenium(locator));
    }

    public boolean isChecked(String locator) {
	return getSelenium().isChecked(
		LocatorStrategyIdentifier.prepareforSelenium(locator));
    }

    public void select(String locator, String selection) {
	getSelenium().select(
		LocatorStrategyIdentifier.prepareforSelenium(locator),
		selection);

    }

    public void type(String locator, String entry) {
	getSelenium().type(
		LocatorStrategyIdentifier.prepareforSelenium(locator), entry);

    }

    public void selectFrame(String locator) {
	getSelenium().selectFrame(
		LocatorStrategyIdentifier.prepareforSelenium(locator));
    }

    public String executeJavascript(String javascript) {
	return getSelenium().getEval(javascript);
    }

    public void injectJqueryIfAbsent() {
	logger.debug("jquery on page = " + jqueryDoesNotExistOnPage());
	if (jqueryDoesNotExistOnPage()) {
	    String jqueryLibraryString = JqueryCodeFactory
		    .getJqueryLibraryString();
	    executeJavascript(jqueryLibraryString);
	}
    }

    private boolean jqueryDoesNotExistOnPage() {
	logger.debug("returns = "
		+ getSelenium().getEval(
			JqueryCodeFactory.getJqueryUndefinedString()));
	return Boolean.valueOf(getSelenium().getEval(
		JqueryCodeFactory.getJqueryUndefinedString()));
    }

    public void waitForPageToLoad(String standardPageLoadWaitTime) {
	getSelenium().waitForPageToLoad(standardPageLoadWaitTime);
	injectJqueryIfAbsent();
    }

    public void reloadPage() {
	getSelenium().refresh();
	injectJqueryIfAbsent();
    }

    public void typeKeys(String locator, String searchString) {
	getSelenium().typeKeys(
		LocatorStrategyIdentifier.prepareforSelenium(locator),
		searchString);
    }

}
