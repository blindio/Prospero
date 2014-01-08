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
