package org.prospero.core.elements;

import org.prospero.core.utils.Context;

public class ElementAttribute extends BaseElement {
	
	public static final char ATTRIBUTE_LOCATOR_SEPARATOR = '@';
	private String attributeName;
	
	public ElementAttribute(String locator, String attributeName) {
		super(locator);
		this.attributeName = attributeName;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	@Override
	public String getValue() {
		return Context.getBrowserDriver().getAttribute(
				getLocator() + ATTRIBUTE_LOCATOR_SEPARATOR + getAttributeName());
	}
	
	@Override
	public String getText() {
		return getValue();
	}
}
