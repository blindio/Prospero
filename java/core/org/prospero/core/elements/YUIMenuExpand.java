package org.prospero.core.elements;

import org.prospero.core.utils.Context;

/**
 * Element representing expansion of a top level Menu in the YUI subnav
 * 
 * @author tlittle
 */
public class YUIMenuExpand extends BaseElement {
	
	public YUIMenuExpand(String locator) {
		super(locator);
	}
	
	/**
	 * Hover over the element
	 */
	public void hover() {
		Context.getBrowserDriver().delay();
		Context.getBrowserDriver().hover(getLocator());
		Context.getBrowserDriver().sleepForASecond();
	}
	
	/**
	 * Click on the element
	 */
	public void click() {
		Context.getBrowserDriver().delay();
		Context.getBrowserDriver().click(getLocator());
	}
}
