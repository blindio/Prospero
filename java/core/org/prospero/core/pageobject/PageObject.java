package org.prospero.core.pageobject;

public interface PageObject {
	
	public String getHTMLSource();
	
	public boolean isLoaded();
	
	public boolean isVisible();
	
	public void waitUntilLoaded();
	
	public String getPageLoadedLocator();
	
	public abstract void closeBrowser();
}
