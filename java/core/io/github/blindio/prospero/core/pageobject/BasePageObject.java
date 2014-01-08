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
package io.github.blindio.prospero.core.pageobject;

import io.github.blindio.prospero.core.utils.Context;
import io.github.blindio.prospero.core.utils.StopWatch;

import org.apache.log4j.Logger;


public abstract class BasePageObject implements PageObject {

    protected Logger logger = Logger.getLogger(BasePageObject.class);

    public BasePageObject() {
	this(null);
    }

    public BasePageObject(String url) {
	Context.init(url);
	Context.setCurrentPage(this);

	if (!Context.isTimerRunning()) {
	    Context.startTimer();
	}

	waitUntilLoaded();
	Context.stopTimer();
	StopWatch.getLogger().info(
		Context.getBrowserDriver().getCurrentUrl() + " load time: ["
			+ Context.getElapsedTime() + "]");
    }

    public boolean isLoaded() {
	return Context.getBrowserDriver().isElementPresent(
		getPageLoadedLocator());
    }

    public boolean isVisible() {
	return Context.getBrowserDriver().isElementVisible(
		getPageLoadedLocator());
    }

    public void waitUntilLoaded() {
	String pageLoadedLocator = getPageLoadedLocator();
	if (pageLoadedLocator != null && pageLoadedLocator.trim().length() > 0) {
	    Context.getBrowserDriver().waitForElementPresent(pageLoadedLocator);
	}
    }

    public abstract String getPageLoadedLocator();

    public void closeBrowser() {
	Context.closeBrowser();
    }

    public String getHTMLSource() {
	return Context.getBrowserDriver().getHtmlSource();
    }
}
