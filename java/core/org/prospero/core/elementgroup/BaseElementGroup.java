package org.prospero.core.elementgroup;

import org.prospero.core.utils.Context;

public abstract class BaseElementGroup implements ElementGroup {
	
	private String id;
	
	public BaseElementGroup(String locator) {
		this.id = locator;
	}
	
	public BaseElementGroup() {
		this(null);
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String buildElementId(String elementIdSuffix) {
		String elementId;
		if (id == null) {
			elementId = elementIdSuffix;
		}
		else {
			elementId = id + "." + elementIdSuffix;
		}
		
		return elementId;
	}
	
	@Override
	public boolean isPresent() {
		// TODO: test each of these implementations
		// this checks for the presence of anythig with an id
		String presenceIndicator = "//*[starts-with(@id,\"" + id + "\")]";
		// String presenceIndicator = "//*[contains(@id,\"" + locator + "\")]";
		return Context.getBrowserDriver().isElementPresent(presenceIndicator);
	}
	
}
