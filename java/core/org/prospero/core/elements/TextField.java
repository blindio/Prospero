package org.prospero.core.elements;

import org.prospero.core.utils.Context;

public class TextField extends ClickableElement {
	
	public TextField(String locator) {
		super(locator);
	}
	
	public void enter(String entry) {
		Context.getBrowserDriver().delay();
		Context.getBrowserDriver().type(getLocator(), entry);
	}
	
	public String getText() {
		return Context.getBrowserDriver().getValue(getLocator());
	}
	
	public void typeKeys(String searchString) {
		Context.getBrowserDriver().delay();
		Context.getBrowserDriver().typeKeys(getLocator(), searchString);
	}
	
	public String getValue() {
		return getText();
	}
	
}
