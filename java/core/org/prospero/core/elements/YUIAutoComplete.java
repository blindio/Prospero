package org.prospero.core.elements;

import org.openqa.selenium.StaleElementReferenceException;

import org.prospero.core.exceptions.ProsperoRuntimeAutomationException;
import org.prospero.core.utils.Context;

/**
 * Represents a YUI Autcomplete widget
 * 
 * @author tlittle
 */
public class YUIAutoComplete extends BaseElement {
	
	private static final String SELECTION_LOCATOR_SUBSTR_MID = "//li[text()[contains(.,'";
	private static final String SELECTION_LOCATOR_SUBSTR_END = "')]]";
	
	/**
	 * Constructor.
	 * Parameter containerName can be found by looking for following elements<br />
	 * &lt;span id='XXXToggle' ... /&gt; and &lt;div id='XXXContainer ... /&gt;<br />
	 * Where XXX is the container name
	 * 
	 * @param containerName
	 */
	public YUIAutoComplete(String containerName) {
		super(containerName);
		// String containerLocator = "//div[@id='" + containerName + "Container']";
		// setLocator( containerLocator );
	}
	
	/**
	 * Element to select from YUI Autocomplete widget<br />
	 * Parameter selection is a case sensative substring from the selection options
	 * 
	 * @param selection
	 */
	public void select(String selection) {
		Context.getBrowserDriver().delay();
		
		try {
			Context.getBrowserDriver().click("//span[@id='" + getLocator() + "Toggle']//button");
			// getPage().getBrowserDriver().typeKeys( "//input[@id='" + getLocator() + "Input']", selection );
			Context.getBrowserDriver().click(
					"//div[@id='" + getLocator() + "Container']" + SELECTION_LOCATOR_SUBSTR_MID + selection +
							SELECTION_LOCATOR_SUBSTR_END);
			
			Context.getBrowserDriver().waitForPageToLoad();
			Context.getCurrentPage().waitUntilLoaded();
		}
		catch (StaleElementReferenceException sere) {
			throw new ProsperoRuntimeAutomationException(sere);
		}
	}
	
	public String getText() {
		return getValue();
	}
	
	public String getValue() {
		return Context.getBrowserDriver().getValue(getLocator() + "Input");
	}
}
