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
package org.prospero.core.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;

import org.prospero.core.exceptions.ProsperoUnsupportedLocatorException;

public class BySeleneseLocator extends By {

    /**
     * <h3><a name="locators"></a>Element Locators</h3>
     * <p>
     * Element Locators tell Selenium which HTML element a command refers to.
     * The format of a locator is:
     * </p>
     * <blockquote><em>locatorType</em><strong>=</strong><em>argument</em>
     * </blockquote>
     * <p>
     * We support the following strategies for locating elements:
     * </p>
     * <ul>
     * <li><strong>identifier</strong>=<em>id</em>: Select the element with the
     * specified @id attribute. If no match is found, select the first element
     * whose @name attribute is <em>id</em>. (This is normally the default; see
     * below.)</li>
     * <li><strong>id</strong>=<em>id</em>: Select the element with the
     * specified @id attribute.</li>
     * <li><strong>name</strong>=<em>name</em>: Select the first element with
     * the specified @name attribute.
     * <ul class="first last simple">
     * <li>username</li>
     * <li>name=username</li>
     * </ul>
     * <p>
     * The name may optionally be followed by one or more
     * <em>element-filters</em>, separated from the name by whitespace. If the
     * <em>filterType</em> is not specified, <strong>value</strong> is assumed.
     * </p>
     * <ul class="first last simple">
     * <li>name=flavour value=chocolate</li>
     * </ul>
     * </li>
     * <li><strong>dom</strong>=<em>javascriptExpression</em>: Find an element
     * by evaluating the specified string. This allows you to traverse the HTML
     * Document Object Model using JavaScript. Note that you must not return a
     * value in this string; simply make it the last expression in the block.
     * <ul class="first last simple">
     * <li>dom=document.forms['myForm'].myDropdown</li>
     * <li>dom=document.images[56]</li>
     * <li>dom=function foo() { return document.links[1]; }; foo();</li>
     * </ul>
     * </li>
     * <li><strong>xpath</strong>=<em>xpathExpression</em>: Locate an element
     * using an XPath expression.
     * <ul class="first last simple">
     * <li>xpath=//img[@alt='The image alt text']</li>
     * <li>xpath=//table[@id='table1']//tr[4]/td[2]</li>
     * <li>xpath=//a[contains(@href,'#id1')]</li>
     * <li>xpath=//a[contains(@href,'#id1')]/@class</li>
     * <li>xpath=(//table[@class='stylee'])//th[text()='theHeaderText']/../td</li>
     * <li>xpath=//input[@name='name2' and @value='yes']</li>
     * <li>xpath=//*[text()="right"]</li>
     * </ul>
     * </li>
     * <li><strong>link</strong>=<em>textPattern</em>: Select the link (anchor)
     * element which contains text matching the specified <em>pattern</em>.
     * <ul class="first last simple">
     * <li>link=The link text</li>
     * </ul>
     * </li>
     * <li><strong>css</strong>=<em>cssSelectorSyntax</em>: Select the element
     * using css selectors. Please refer to <a
     * href="http://www.w3.org/TR/REC-CSS2/selector.html">CSS2 selectors</a>, <a
     * href="http://www.w3.org/TR/2001/CR-css3-selectors-20011113/">CSS3
     * selectors</a> for more information. You can also check the
     * TestCssLocators test in the selenium test suite for an example of usage,
     * which is included in the downloaded selenium core package.
     * <ul class="first last simple">
     * <li>css=a[href="#id3"]</li>
     * <li>css=span#firstChild + span</li>
     * </ul>
     * <p>
     * Currently the css selector locator supports all css1, css2 and css3
     * selectors except namespace in css3, some pseudo classes(:nth-of-type,
     * :nth-last-of-type, :first-of-type, :last-of-type, :only-of-type,
     * :visited, :hover, :active, :focus, :indeterminate) and pseudo
     * elements(::first-line, ::first-letter, ::selection, ::before, ::after).
     * </p>
     * </li>
     * <li><strong>ui</strong>=<em>uiSpecifierString</em>: Locate an element by
     * resolving the UI specifier string to another locator, and evaluating it.
     * See the <a href=
     * "http://svn.openqa.org/fisheye/browse/~raw,r=trunk/selenium/trunk/src/main/resources/core/scripts/ui-doc.html"
     * >Selenium UI-Element Reference</a> for more details.
     * <ul class="first last simple">
     * <li>ui=loginPages::loginButton()</li>
     * <li>ui=settingsPages::toggle(label=Hide Email)</li>
     * <li>ui=forumPages::postBody(index=2)//a[2]</li>
     * </ul>
     * </li>
     * </ul>
     * <p>
     * Without an explicit locator prefix, Selenium uses the following default
     * strategies:
     * </p>
     * <ul class="simple">
     * <li><strong>dom</strong>, for locators starting with "document."</li>
     * <li><strong>xpath</strong>, for locators starting with "//"</li>
     * <li><strong>identifier</strong>, otherwise</li>
     * </ul>
     * <h3><a name="element-filters">Element Filters</a></h3><blockquote>
     * <p>
     * Element filters can be used with a locator to refine a list of candidate
     * elements. They are currently used only in the 'name' element-locator.
     * </p>
     * <p>
     * Filters look much like locators, ie.
     * </p>
     * <blockquote><em>filterType</em><strong>=</strong><em>argument</em>
     * </blockquote>
     * <p>
     * Supported element-filters are:
     * </p>
     * <p>
     * <strong>value=</strong><em>valuePattern</em>
     * </p>
     * <blockquote> Matches elements based on their values. This is particularly
     * useful for refining a list of similarly-named
     * toggle-buttons.</blockquote>
     * <p>
     * <strong>index=</strong><em>index</em>
     * </p>
     * <blockquote> Selects a single element based on its position in the list
     * (offset from zero).</blockquote></blockquote><h3><a
     * name="patterns"></a>String-match Patterns</h3>
     * <p>
     * Various Pattern syntaxes are available for matching string values:
     * </p>
     * <ul>
     * <li><strong>glob:</strong><em>pattern</em>: Match a string against a
     * "glob" (aka "wildmat") pattern. "Glob" is a kind of limited
     * regular-expression syntax typically used in command-line shells. In a
     * glob pattern, "*" represents any sequence of characters, and "?"
     * represents any single character. Glob patterns match against the entire
     * string.</li>
     * <li><strong>regexp:</strong><em>regexp</em>: Match a string using a
     * regular-expression. The full power of JavaScript regular-expressions is
     * available.</li>
     * <li><strong>regexpi:</strong><em>regexpi</em>: Match a string using a
     * case-insensitive regular-expression.</li>
     * <li><strong>exact:</strong><em>string</em>: Match a string exactly,
     * verbatim, without any of that fancy wildcard stuff.</li>
     * </ul>
     * <p>
     * If no pattern prefix is specified, Selenium assumes that it's a "glob"
     * pattern.
     * </p>
     * <p>
     * For commands that return multiple values (such as verifySelectOptions),
     * the string being matched is a comma-separated list of the return values,
     * where both commas and backslashes in the values are backslash-escaped.
     * When providing a pattern, the optional matching syntax (i.e. glob,
     * regexp, etc.) is specified once, as usual, at the beginning of the
     * pattern.
     * </p>
     */

    public static final int EQUALS_SYMBOL_CODE_POINT = 61;

    public static final String STRATEGY_IDENTIFIER = "identifier";
    public static final String STRATEGY_ID = "id";
    public static final String STRATEGY_NAME = "name";
    public static final String STRATEGY_DOM = "dom";
    public static final String STRATEGY_XPATH = "xpath";
    public static final String STRATEGY_LINK = "link";
    public static final String STRATEGY_CSS = "css";
    public static final String STRATEGY_UI = "ui";

    public static final String IMPLICIT_STRATEGY_DOM = "document";
    public static final String IMPLICIT_STRATEGY_XPATH = "//";

    private By seleneseLocatedBy;

    public BySeleneseLocator(String seleneseLocator)
	    throws ProsperoUnsupportedLocatorException {
	this.seleneseLocatedBy = seleneseLocator(seleneseLocator);
    }

    @Override
    public WebElement findElement(SearchContext context) {
	return seleneseLocatedBy.findElement(context);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
	return seleneseLocatedBy.findElements(context);
    }

    @Override
    public boolean equals(Object o) {
	return seleneseLocatedBy.equals(o);
    }

    public static By seleneseLocator(String seleneseLocator)
	    throws ProsperoUnsupportedLocatorException {
	By parsedBy = null;

	int index = seleneseLocator.indexOf(EQUALS_SYMBOL_CODE_POINT);
	String strategy = null;
	String locator = null;
	if (index != -1) {
	    strategy = seleneseLocator.substring(0, index);
	    locator = seleneseLocator.substring(index + 1);

	    if (!(strategy.equalsIgnoreCase(STRATEGY_IDENTIFIER)
		    || strategy.equalsIgnoreCase(STRATEGY_ID)
		    || strategy.equalsIgnoreCase(STRATEGY_NAME)
		    || strategy.equalsIgnoreCase(STRATEGY_DOM)
		    || strategy.equalsIgnoreCase(STRATEGY_XPATH)
		    || strategy.equalsIgnoreCase(STRATEGY_LINK)
		    || strategy.equalsIgnoreCase(STRATEGY_CSS) || strategy
			.equalsIgnoreCase(STRATEGY_UI))) {
		strategy = null;
	    }
	}

	if (strategy == null) {
	    locator = seleneseLocator;
	    if (seleneseLocator.substring(0, IMPLICIT_STRATEGY_DOM.length())
		    .toLowerCase().equals(IMPLICIT_STRATEGY_DOM)) {
		strategy = STRATEGY_DOM;
	    } else if (seleneseLocator.substring(0,
		    IMPLICIT_STRATEGY_XPATH.length()).equals(
		    IMPLICIT_STRATEGY_XPATH)) {
		strategy = STRATEGY_XPATH;
	    } else {
		strategy = STRATEGY_IDENTIFIER;
	    }
	}

	if (strategy.equalsIgnoreCase(STRATEGY_IDENTIFIER)) {
	    parsedBy = new ByIdOrName(locator);
	} else if (strategy.equalsIgnoreCase(STRATEGY_ID)) {
	    parsedBy = By.id(locator);
	} else if (strategy.equalsIgnoreCase(STRATEGY_NAME)) {
	    parsedBy = By.name(locator);
	    // TODO: use ByChained to parse element-filters
	} else if (strategy.equalsIgnoreCase(STRATEGY_DOM)) {
	    parsedBy = null;
	    throw new ProsperoUnsupportedLocatorException(
		    "Selenium 1.x Locator Strategy: "
			    + STRATEGY_DOM
			    + " is not supported in Webdriver.  Selenium recommend using the "
			    + STRATEGY_XPATH + " strategy (" + seleneseLocator
			    + ")");
	} else if (strategy.toLowerCase().equals(STRATEGY_XPATH)) {
	    if (locator.endsWith("/")) {
		parsedBy = By.xpath(locator.substring(0, locator.length() - 1));
	    } else {
		parsedBy = By.xpath(locator);
	    }
	} else if (strategy.equalsIgnoreCase(STRATEGY_LINK)) {
	    parsedBy = By.partialLinkText(seleneseLocator);
	} else if (strategy.equalsIgnoreCase(STRATEGY_CSS)) {
	    parsedBy = By.cssSelector(locator);
	} else if (strategy.equalsIgnoreCase(STRATEGY_UI)) {
	    parsedBy = null;
	    throw new ProsperoUnsupportedLocatorException(
		    "Selenium 1.x Locator Strategy: " + STRATEGY_UI
			    + " is not supported in Webdriver ("
			    + seleneseLocator + ")");
	} else {
	    // TODO
	}

	return parsedBy;
    }
}
