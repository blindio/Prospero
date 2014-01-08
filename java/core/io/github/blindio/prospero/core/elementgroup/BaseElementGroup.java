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
package io.github.blindio.prospero.core.elementgroup;

import io.github.blindio.prospero.core.utils.Context;

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
	} else {
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
