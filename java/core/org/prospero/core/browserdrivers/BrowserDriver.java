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
package org.prospero.core.browserdrivers;

import java.util.Random;

import org.apache.log4j.Logger;

import org.prospero.core.elements.TextLabel;
import org.prospero.core.utils.Config;
import org.prospero.core.utils.Constants;
import org.prospero.core.utils.Context;
import org.prospero.core.utils.PropertiesConstants;
import org.prospero.core.utils.StopWatch;

import static org.prospero.core.utils.AssertJUnitParams.*;

/**
 * Defines an object that runs Selenium commands. <h3><a
 * name="locators"></a>Element Locators</h3>
 * <p>
 * Element Locators tell Selenium which HTML element a command refers to. The
 * format of a locator is:
 * </p>
 * <blockquote><em>locatorType</em><strong>=</strong><em>argument</em>
 * </blockquote>
 * <p>
 * We support the following strategies for locating elements:
 * </p>
 * <ul>
 * <li><strong>identifier</strong>=<em>id</em>: Select the element with the
 * specified @id attribute. If no match is found, select the first element whose @name
 * attribute is <em>id</em>. (This is normally the default; see below.)</li>
 * <li><strong>id</strong>=<em>id</em>: Select the element with the specified @id
 * attribute.</li>
 * <li><strong>name</strong>=<em>name</em>: Select the first element with the
 * specified @name attribute.
 * <ul class="first last simple">
 * <li>username</li>
 * <li>name=username</li>
 * </ul>
 * <p>
 * The name may optionally be followed by one or more <em>element-filters</em>,
 * separated from the name by whitespace. If the <em>filterType</em> is not
 * specified, <strong>value</strong> is assumed.
 * </p>
 * <ul class="first last simple">
 * <li>name=flavour value=chocolate</li>
 * </ul>
 * </li>
 * <li><strong>dom</strong>=<em>javascriptExpression</em>: Find an element by
 * evaluating the specified string. This allows you to traverse the HTML
 * Document Object Model using JavaScript. Note that you must not return a value
 * in this string; simply make it the last expression in the block.
 * <ul class="first last simple">
 * <li>dom=document.forms['myForm'].myDropdown</li>
 * <li>dom=document.images[56]</li>
 * <li>dom=function foo() { return document.links[1]; }; foo();</li>
 * </ul>
 * </li>
 * <li><strong>xpath</strong>=<em>xpathExpression</em>: Locate an element using
 * an XPath expression.
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
 * <li><strong>css</strong>=<em>cssSelectorSyntax</em>: Select the element using
 * css selectors. Please refer to <a
 * href="http://www.w3.org/TR/REC-CSS2/selector.html">CSS2 selectors</a>, <a
 * href="http://www.w3.org/TR/2001/CR-css3-selectors-20011113/">CSS3
 * selectors</a> for more information. You can also check the TestCssLocators
 * test in the selenium test suite for an example of usage, which is included in
 * the downloaded selenium core package.
 * <ul class="first last simple">
 * <li>css=a[href="#id3"]</li>
 * <li>css=span#firstChild + span</li>
 * </ul>
 * <p>
 * Currently the css selector locator supports all css1, css2 and css3 selectors
 * except namespace in css3, some pseudo classes(:nth-of-type,
 * :nth-last-of-type, :first-of-type, :last-of-type, :only-of-type, :visited,
 * :hover, :active, :focus, :indeterminate) and pseudo elements(::first-line,
 * ::first-letter, ::selection, ::before, ::after).
 * </p>
 * </li>
 * <li><strong>ui</strong>=<em>uiSpecifierString</em>: Locate an element by
 * resolving the UI specifier string to another locator, and evaluating it. See
 * the <a href=
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
 * useful for refining a list of similarly-named toggle-buttons.</blockquote>
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
 * <li><strong>glob:</strong><em>pattern</em>: Match a string against a "glob"
 * (aka "wildmat") pattern. "Glob" is a kind of limited regular-expression
 * syntax typically used in command-line shells. In a glob pattern, "*"
 * represents any sequence of characters, and "?" represents any single
 * character. Glob patterns match against the entire string.</li>
 * <li><strong>regexp:</strong><em>regexp</em>: Match a string using a
 * regular-expression. The full power of JavaScript regular-expressions is
 * available.</li>
 * <li><strong>regexpi:</strong><em>regexpi</em>: Match a string using a
 * case-insensitive regular-expression.</li>
 * <li><strong>exact:</strong><em>string</em>: Match a string exactly, verbatim,
 * without any of that fancy wildcard stuff.</li>
 * </ul>
 * <p>
 * If no pattern prefix is specified, Selenium assumes that it's a "glob"
 * pattern.
 * </p>
 * <p>
 * For commands that return multiple values (such as verifySelectOptions), the
 * string being matched is a comma-separated list of the return values, where
 * both commas and backslashes in the values are backslash-escaped. When
 * providing a pattern, the optional matching syntax (i.e. glob, regexp, etc.)
 * is specified once, as usual, at the beginning of the pattern.
 * </p>
 */
public abstract class BrowserDriver {

    protected static Logger logger = Logger.getLogger(BrowserDriver.class);

    public static final String FIREFOX = "*firefox";
    public static final String IEXPLORE = "*iexplore";

    public static final String STANDARD_PAGE_LOAD_WAIT_TIME = "60000";
    public static final String STANDARD_DHTML_LOAD_WAIT_TIME = "60000";

    public static final String EVAL_RESIZE = "window.resizeTo(800,600)";
    public static final String EVAL_MOVE = "window.moveTo(25,25)";

    public static String selectedBrowser = FIREFOX;

    private static boolean performanceTest = false;

    private static boolean useDelay = false;
    private static int minDelay = 0;
    private static int maxDelay = 0;

    private Random randomizer;

    private String siteUrl;

    static {
	useDelay = Config.getBoolean(PropertiesConstants.USE_DELAY);
	minDelay = Config.getInt(PropertiesConstants.MIN_DELAY);
	maxDelay = Config.getInt(PropertiesConstants.MAX_DELAY);
    }

    /**
     * Constructor. Should only be used by sub classes
     */
    protected BrowserDriver() {
	randomizer = new Random(Constants.RANDOM_SEED);
	Context.setBrowserDriver(this);
    }

    protected void setSiteUrl(String siteUrl) {
	this.siteUrl = siteUrl;
    }

    public String getSiteUrl() {
	return siteUrl;
    }

    /**
     * Close and stop Selenium
     */
    public abstract void stopEverything();

    /**
     * Determines if the specified element is visible. An element can be
     * rendered invisible by setting the CSS "visibility" property to "hidden",
     * or the "display" property to "none", either for the element itself or one
     * if its ancestors. This method will fail if the element is not present.
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     * @return true if the specified element is visible, false otherwise
     */
    public abstract boolean isElementVisible(String locator);

    /**
     * Calls Assertion framework to Assert if an Alert Dialog is present
     */
    public abstract void assertNoAlertPresent();

    /**
     * Opens an URL in the test frame.
     * 
     * @param url
     */
    public abstract void open(String url);

    /**
     * Check a toggle-button (checkbox/radio)
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     */
    public abstract void check(String locator);

    /**
     * Verifies that the specified element is somewhere on the page.
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     * @return true if the element is present, false otherwise
     */
    public abstract boolean isElementPresent(String locator);

    /**
     * Gets the (whitespace-trimmed) value of an input field (or anything else
     * with a value parameter). For checkbox/radio elements, the value will be
     * "on" or "off" depending on whether the element is checked or not.
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     * @return the element value, or "on/off" for checkbox/radio elements
     */
    public abstract String getValue(String locator);

    public abstract int getOptionCount(String locator);

    public abstract void chooseCancelOnNextConfirmation();

    public abstract void chooseOkOnNextConfirmation();

    /**
     * Clicks on a link, button, checkbox or radio button.
     * 
     * @param locator
     *            an element locator
     */
    public abstract void click(String locator);

    /**
     * Retrieves the message of a JavaScript alert generated during the previous
     * action, or fail if there were no alerts.
     * 
     * @param locator
     *            an element locator
     * @return The message of the most recent JavaScript alert
     */
    public abstract String getAlert();

    /**
     * Gets the value of an element attribute. The value of the attribute may
     * differ across browsers (this is the case for the "style" attribute, for
     * example).
     * 
     * @param attributeLocator
     *            an element locator followed by an @ sign and then the name of
     *            the attribute, e.g. "foo@bar"
     * @return the value of the specified attribute
     */
    public abstract String getAttribute(String attributeLocator);

    /**
     * Retrieves the message of a JavaScript confirmation dialog generated
     * during the previous action.
     * 
     * @return the message of the most recent JavaScript confirmation dialog
     */
    public abstract String getConfirmation();

    /**
     * Gets the text of an element. This works for any element that contains
     * text. This command uses either the textContent (Mozilla-like browsers) or
     * the innerText (IE-like browsers) of the element, which is the rendered
     * text shown to the user.
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     * @return the text of the element
     */
    public abstract String getText(String locator);

    /**
     * Gets whether a toggle-button (checkbox/radio) is checked. Fails if the
     * specified element doesn't exist or isn't a toggle-button.
     * 
     * @param locator
     *            an <a href="#locators">element locator</a> pointing to a
     *            checkbox or radio button
     * @return true if the checkbox is checked, false otherwise
     */
    public abstract boolean isChecked(String locator);

    /**
     * Select an option from a drop-down using an option locator.
     * <p>
     * Option locators provide different ways of specifying options of an HTML
     * Select element (e.g. for selecting a specific option, or for asserting
     * that the selected option satisfies a specification). There are several
     * forms of Select Option Locator.
     * </p>
     * <ul>
     * <li><strong>label</strong>=<em>labelPattern</em>: matches options based
     * on their labels, i.e. the visible text. (This is the default.)
     * <ul class="first last simple">
     * <li>label=regexp:^[Oo]ther</li>
     * </ul>
     * </li>
     * <li><strong>value</strong>=<em>valuePattern</em>: matches options based
     * on their values.
     * <ul class="first last simple">
     * <li>value=other</li>
     * </ul>
     * </li>
     * <li><strong>id</strong>=<em>id</em>: matches options based on their ids.
     * <ul class="first last simple">
     * <li>id=option1</li>
     * </ul>
     * </li>
     * <li><strong>index</strong>=<em>index</em>: matches an option based on its
     * index (offset from zero).
     * <ul class="first last simple">
     * <li>index=2</li>
     * </ul>
     * </li>
     * </ul>
     * <p>
     * If no option locator prefix is provided, the default behaviour is to
     * match on <strong>label</strong>.
     * </p>
     * 
     * @param selectLocator
     *            an <a href="#locators">element locator</a> identifying a
     *            drop-down menu
     * @param optionLocator
     *            an option locator (a label by default)
     */
    public abstract void select(String locator, String selection);

    /**
     * Sets the value of an input field, as though you typed it in.
     * <p>
     * Can also be used to set the value of combo boxes, check boxes, etc. In
     * these cases, value should be the value of the option selected, not the
     * visible text.
     * </p>
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     * @param value
     *            the value to type
     */
    public abstract void type(String locator, String entry);

    public abstract void clickOkOnAlert();

    /**
     * Selects a frame within the current window. (You may invoke this command
     * multiple times to select nested frames.) To select the parent frame, use
     * "relative=parent" as a locator; to select the top frame, use
     * "relative=top". You can also select a frame by its 0-based index number;
     * select the first frame with "index=0", or the third frame with "index=2".
     * <p>
     * You may also use a DOM expression to identify the frame you want
     * directly, like this: <code>dom=frames["main"].frames["subframe"]</code>
     * </p>
     * 
     * @param locator
     *            an <a href="#locators">element locator</a> identifying a frame
     *            or iframe
     */
    public abstract void selectFrame(String locator);

    /**
     * Calls the Assertion Framework to Assert that the TextLabel object
     * contains the text
     * 
     * @param element
     *            a TextLabel object
     * @param expectedText
     */
    public abstract void assertLabelContainsText(TextLabel element,
	    String expectedText);

    /**
     * Gets the result of evaluating the specified JavaScript snippet. The
     * snippet may have multiple lines, but only the result of the last line
     * will be returned.
     * <p>
     * Note that, by default, the snippet will run in the context of the
     * "selenium" object itself, so <code>this</code> will refer to the Selenium
     * object. Use <code>window</code> to refer to the window of your
     * application, e.g. <code>window.document.getElementById('foo')</code>
     * </p>
     * <p>
     * If you need to use a locator to refer to a single element in your
     * application page, you can use
     * <code>this.browserbot.findElement("id=foo")</code> where "id=foo" is your
     * locator.
     * </p>
     * 
     * @param script
     *            the JavaScript snippet to run
     * @return the results of evaluating the snippet
     */
    public abstract String executeJavascript(String javascript);

    /**
     * Call Thread.sleep and call AssertionFramework.fail if an Interrupted
     * Exception is Thrown
     * 
     * @param milliseconds
     */
    public void sleep(int milliseconds) {
	StopWatch.sleep(milliseconds);
    }

    /**
     * Call Thread.sleep for one second
     */
    public void sleepForASecond() {
	StopWatch.sleepForASecond();
    }

    /**
     * Wait for up to one minute for a element to become visible
     * 
     * @param locator
     */
    public void waitForElementVisible(String locator) {
	for (int second = 0;; second++) {
	    if (second >= Integer
		    .valueOf(BrowserDriver.STANDARD_DHTML_LOAD_WAIT_TIME)
		    / StopWatch.MS_PER_SECOND)
		fail("Timeout waiting for element to become visible. locator: "
			+ locator);

	    if (isElementVisible(locator))
		break;
	    sleepForASecond();
	}
    }

    public void waitForElementPresent(String locator) {
	for (int second = 0;; second++) {
	    if (second >= Integer
		    .valueOf(BrowserDriver.STANDARD_DHTML_LOAD_WAIT_TIME)
		    / StopWatch.MS_PER_SECOND)
		fail("Timeout waiting for element to become visible. locator: "
			+ locator);

	    if (isElementPresent(locator))
		break;
	    sleepForASecond();
	}
    }

    /**
     * Wait for up to one minute for a element cease to be visible
     * 
     * @param locator
     */
    public void waitForElement_NOT_Visible(String locator) {
	for (int second = 0;; second++) {
	    if (second >= Integer
		    .valueOf(BrowserDriver.STANDARD_DHTML_LOAD_WAIT_TIME)
		    / StopWatch.MS_PER_SECOND)
		fail("Timeout waiting for element to become not visible: "
			+ locator);

	    try {
		boolean elementPresent = isElementVisible(locator);

		if (!elementPresent)
		    break;
	    } catch (Exception e) {
		fail(e.getMessage());
	    }
	    sleepForASecond();
	}
    }

    /**
     * Waits for a new page to load.
     * 
     * @param timeout
     *            a timeout in milliseconds, after which this command will
     *            return with an error
     */
    public abstract void waitForPageToLoad(String standardPageLoadWaitTime);

    /**
     * Wait 60 Seconds for a page to load
     */
    public void waitForPageToLoad() {
	waitForPageToLoad(STANDARD_PAGE_LOAD_WAIT_TIME);
    }

    /**
     * Wait 60 seconds for a new frame to load.
     * 
     * @param frameAddress
     *            FrameAddress from the server side
     */
    public abstract void waitForFrameToLoad(String frameAddress);

    /**
     * Reload the current page
     */
    public abstract void reloadPage();

    /**
     * Simulates keystroke events on the specified element, as though you typed
     * the value key-by-key.
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     * @param entry
     *            the value to type
     */
    public abstract void typeKeys(String locator, String entry);

    /**
     * Returns the entire HTML source between the opening and closing "html"
     * tags.
     * 
     * @return the entire HTML source
     */
    public abstract String getHtmlSource();

    /**
     * Gets the absolute URL of the current page.
     * 
     * @return the absolute URL of the current page
     */
    public abstract String getCurrentUrl();

    /**
     * Simulates a user hovering a mouse over the specified element.
     * 
     * @param locator
     *            an <a href="#locators">element locator</a>
     */
    public abstract void hover(String locator);

    public abstract void shiftClick(String locator);

    /**
     * sleep for a random amount of time defined by the properties: delay.on,
     * delay.minimum and delay.maximum
     */
    public void delay() {
	if (useDelay) {
	    int delayTime = minDelay
		    + (int) (randomizer.nextFloat() * (maxDelay - minDelay));
	    sleep(delayTime);
	}
    }

    public static boolean isPerformanceTest() {
	return performanceTest;
    }

    public boolean isBrowserOpen() {
	return Context.isBrowserOpen();
    }

}
