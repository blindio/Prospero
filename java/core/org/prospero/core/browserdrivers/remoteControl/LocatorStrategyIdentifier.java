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
package org.prospero.core.browserdrivers.remoteControl;

public class LocatorStrategyIdentifier {

    private static final String CSS_PREFIX = "css=";

    public static String prepareforSelenium(String locator) {
	if (isCss(locator))
	    return prependCssSuffix(locator);

	return locator;
    }

    private static String prependCssSuffix(String locator) {
	return CSS_PREFIX + locator;
    }

    public static boolean isCss(String locator) {
	return (!locator.contains("/"));
    }

    public static boolean isNonSeleniumCss(String locator) {
	return locator.contains(":eq(");
    }

}
