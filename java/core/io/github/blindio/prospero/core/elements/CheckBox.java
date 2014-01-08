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
