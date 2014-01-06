package org.prospero.core.pageobject;

import org.apache.log4j.Logger;

import org.prospero.core.utils.Context;
import org.prospero.core.utils.StopWatch;

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
				Context.getBrowserDriver().getCurrentUrl() + " load time: [" + Context.getElapsedTime() + "]");
	}
	
	public boolean isLoaded() {
		return Context.getBrowserDriver().isElementPresent(getPageLoadedLocator());
	}
	
	public boolean isVisible() {
		return Context.getBrowserDriver().isElementVisible(getPageLoadedLocator());
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
