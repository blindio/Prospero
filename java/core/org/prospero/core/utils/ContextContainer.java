package org.prospero.core.utils;

import org.prospero.core.browserdrivers.BrowserDriver;
import org.prospero.core.pageobject.PageObject;

class ContextContainer {
	
	private StopWatch timer;
	private PageObject currentPage;
	private BrowserDriver browserDriver;
	
	ContextContainer() {
		currentPage = null;
		browserDriver = null;
		timer = new StopWatch();
	}
	
	StopWatch getTimer() {
		return timer;
	}
	
	void setTimer(StopWatch timer) {
		this.timer = timer;
	}
	
	public PageObject getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(PageObject currentPage) {
		this.currentPage = currentPage;
	}
	
	public BrowserDriver getBrowserDriver() {
		return browserDriver;
	}
	
	public void setBrowserDriver(BrowserDriver browserDriver) {
		this.browserDriver = browserDriver;
	}
}
