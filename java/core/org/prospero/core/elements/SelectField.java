package org.prospero.core.elements;

import org.prospero.core.utils.Context;

public class SelectField extends BaseElement {
	
	// This is to be used if we ever allow xpath selector for select elements
	// private static final String SELECTION_LOCATOR_SUBSTR_MID = "//option[text()[contains(.,'";
	// private static final String SELECTION_LOCATOR_SUBSTR_END = "')]]";
	
	public SelectField(String locator) {
		super(locator);
	}
	
	public void select(String selection) {
		Context.getBrowserDriver().delay();
		// This is to be used if we ever allow xpath selector for select elements
		// String selectionLoc = getLocator() + SELECTION_LOCATOR_SUBSTR_MID + selection +
		// SELECTION_LOCATOR_SUBSTR_END;
		Context.getBrowserDriver().select(getLocator(), selection);
	}
	
}
