package org.prospero.core.elements;

import org.prospero.core.utils.Context;

/**
 * Base element for all clickable elements (i.e. radio, link, checkbox and button)
 * 
 * @author tlittle
 */
public abstract class ClickableElement extends BaseElement {
	
	public ClickableElement(String locator) {
		super(locator);
	}
	
	public void click() {
		Context.getBrowserDriver().delay();
		Context.getBrowserDriver().click(getLocator());
	}
}
