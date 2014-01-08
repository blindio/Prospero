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

/**
 * Element representing a Dynamic HTML link opening a new container, rather than
 * causing a page load
 * 
 * @author tlittle
 * @param <T>
 */
public class DhtmlLink<T> extends ClickableElement {

    private Class<T> clazz;

    public DhtmlLink(String locator, Class<T> clazz) {
	super(locator);
	this.clazz = clazz;
    }

    public T clickToNewContainer() {
	try {
	    click();
	    return clazz.getConstructor(new Class[] {}).newInstance();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
