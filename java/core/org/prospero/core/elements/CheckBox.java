package org.prospero.core.elements;

import org.prospero.core.utils.Context;

/**
 * Element Representing a checkbox input type
 * 
 * @author tlittle
 */
public class CheckBox extends ClickableElement {
	
	public CheckBox(String locator) {
		super(locator);
	}
	
	/**
	 * Toggles the checkbox for this CheckBox element
	 */
	public void check() {
		Context.getBrowserDriver().delay();
		Context.getBrowserDriver().check(getLocator());
	}
	
	public boolean isChecked() {
		return Context.getBrowserDriver().isChecked(getLocator());
	}
	
	public void check(boolean toCheck) {
		if ((toCheck && !isChecked()) || (!toCheck && isChecked())) {
			check();
		}
		
	}
}
