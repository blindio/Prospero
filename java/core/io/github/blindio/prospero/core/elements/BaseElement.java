/*******************************************************************************
 * Copyright 2014 S. Thorson Little
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package io.github.blindio.prospero.core.elements;

import io.github.blindio.prospero.core.utils.Context;

import org.apache.log4j.Logger;


/**
 * The Base Object For all Object representing page elements. A PageObject is
 * composed of it Child Objects
 * 
 * @author tlittle
 */
public abstract class BaseElement {

    protected static Logger logger = Logger.getLogger(BaseElement.class);

    private String locator;

    /**
     * Constructor
     * 
     * @param locator
     */
    public BaseElement(String locator) {
	this.locator = locator;
    }

    public String getLocator() {
	return locator;
    }

    protected void setLocator(String locator) {
	this.locator = locator;
    }

    /**
     * Determines whether the element is present in the page's DOM
     * 
     * @return
     */
    public boolean isPresent() {
	return Context.getBrowserDriver().isElementPresent(locator);
    }

    /**
     * Is this element displayed or not? This method avoids the problem of
     * having to parse an element's "style" attribute.
     * 
     * @return
     */
    public boolean isVisible() {
	return Context.getBrowserDriver().isElementVisible(locator);
    }

    /**
     * Gets the (whitespace-trimmed) value of an input field (or anything else
     * with a value parameter). For checkbox/radio elements, the value will be
     * "on" or "off" depending on whether the element is checked or not.
     * 
     * @return
     */
    public String getValue() {
	return Context.getBrowserDriver().getValue(locator).trim();
    }

    /**
     * Gets the internal text for this element from the DOM
     * 
     * @return
     */
    public String getText() {
	return Context.getBrowserDriver().getText(locator).trim();
    }

}
