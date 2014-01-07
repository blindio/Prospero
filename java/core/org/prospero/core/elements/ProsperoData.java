package org.prospero.core.elements;

public class ProsperoData extends ElementAttribute {
	
	public static final String ATTRIBUTE_NAME = "data-prospero";
	
	public ProsperoData(String locator) {
		super(locator, ATTRIBUTE_NAME);
	}
	
}
