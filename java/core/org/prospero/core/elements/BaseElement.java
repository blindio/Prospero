package org.prospero.core.elements;

import org.apache.log4j.Logger;

import org.prospero.core.utils.Context;

/**
 * The Base Object For all Object representing page elements. A PageObject is composed of it Child Objects
 * 
 * @author tlittle
 */
public abstract class BaseElement {
	
	protected static Logger logger = Logger.getLogger(BaseElement.class);
	
	private String locator;
	
	/**
	 * Constructor
	 * 
	 * @param locator
	 */
	public BaseElement(String locator) {
		this.locator = locator;
	}
	
	public String getLocator() {
		return locator;
	}
	
	protected void setLocator(String locator) {
		this.locator = locator;
	}
	
	/**
	 * Determines whether the element is present in the page's DOM
	 * 
	 * @return
	 */
	public boolean isPresent() {
		return Context.getBrowserDriver().isElementPresent(locator);
	}
	
	/**
	 * Is this element displayed or not? This method avoids the problem of having to parse an element's
	 * "style" attribute.
	 * 
	 * @return
	 */
	public boolean isVisible() {
		return Context.getBrowserDriver().isElementVisible(locator);
	}
	
	/**
	 * Gets the (whitespace-trimmed) value of an input field (or anything else with a value parameter). For
	 * checkbox/radio elements, the value will be "on" or "off" depending on whether the element is checked or
	 * not.
	 * 
	 * @return
	 */
	public String getValue() {
		return Context.getBrowserDriver().getValue(locator).trim();
	}
	
	/**
	 * Gets the internal text for this element from the DOM
	 * 
	 * @return
	 */
	public String getText() {
		return Context.getBrowserDriver().getText(locator).trim();
	}
	
}
