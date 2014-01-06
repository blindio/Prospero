package org.prospero.core.elements;

import org.prospero.core.exceptions.ProsperoRuntimeAutomationException;
import org.prospero.core.pageobject.PageObject;
import org.prospero.core.utils.Context;

/**
 * Represents a clickable hyperlink on a page that load a new page.
 * 
 * @author tlittle
 * @param <T>
 *            The Target PageObject
 */
public class PageLink<T extends PageObject> extends ClickableElement {
	
	private Class<T> clazz;
	
	/**
	 * Constructor. Used by PageObjects defined in application objects package
	 * 
	 * @param locator
	 *            Selenium 1.x based locator for this link element
	 * @param clazz
	 *            Class of the Target PageObject
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageLink(String locator, Class clazz) {
		// NOTE: I used Class rather than Class<T> to allow using an Interface for T and an implemention for
		// clazz
		super(locator);
		this.clazz = clazz;
	}
	
	/**
	 * Click this page link and return the new page
	 * 
	 * @return Target PageObject
	 */
	public T clickToNewPage() {
		// Delay the click configured amount
		Context.getBrowserDriver().delay();
		// Start a page load timer
		Context.startTimer();
		try {
			Context.getBrowserDriver().click(getLocator());
			Context.getBrowserDriver().waitForPageToLoad();
			return clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});
		}
		catch (Exception e) {
			String message = e.getMessage() + " element locator: '" + getLocator() + "'";
			logger.error(e.getMessage());
			throw new ProsperoRuntimeAutomationException(message, e);
		}
	}
}
