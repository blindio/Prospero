package org.prospero.core.elements;

import org.prospero.core.utils.Context;

/**
 * @author tlittle
 */
public class RadioButton extends ClickableElement {
	
	public RadioButton(String locator) {
		super(locator);
	}
	
	public boolean isChecked() {
		return Context.getBrowserDriver().isChecked(getLocator());
	}
	
}
