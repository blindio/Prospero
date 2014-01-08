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

import org.prospero.core.browserdrivers.BrowserDriver;
import org.prospero.core.browserdrivers.BrowserDriverFactory;
import org.prospero.core.pageobject.PageObject;

public class Context {

    private static final ThreadLocal<ContextContainer> threadLocal = new ThreadLocal<ContextContainer>() {

	protected synchronized ContextContainer initialValue() {
	    return new ContextContainer();
	}
    };

    public static void init(String url) {
	if (threadLocal.get() == null) {
	    ContextContainer cc = new ContextContainer();
	    threadLocal.set(cc);
	}

	BrowserDriver driver = Context.getBrowserDriver();
	if (driver == null) {
	    driver = url == null ? BrowserDriverFactory.getBrowserDriver()
		    : BrowserDriverFactory.getBrowserDriver(url);
	    Context.setBrowserDriver(driver);
	} else if (driver != null && url != null) {
	    driver.open(url);
	}
    }

    public static void init() {
	init(null);
    }

    public static void startTimer() {
	threadLocal.get().getTimer().start();
    }

    public static void stopTimer() {
	threadLocal.get().getTimer().stop();
    }

    public static boolean isTimerRunning() {
	return threadLocal.get().getTimer().isRunning();
    }

    public static long getElapsedTime() {
	return threadLocal.get().getTimer().getElapsedTime();
    }

    public static long getElapsedTimeSecs() {
	return threadLocal.get().getTimer().getElapsedTimeSecs();
    }

    public static BrowserDriver getBrowserDriver() {
	return threadLocal.get().getBrowserDriver();
    }

    public static void setBrowserDriver(BrowserDriver bd) {
	threadLocal.get().setBrowserDriver(bd);
    }

    public static PageObject getCurrentPage() {
	return threadLocal.get().getCurrentPage();
    }

    public static void setCurrentPage(PageObject page) {
	threadLocal.get().setCurrentPage(page);
    }

    public static void closeBrowser() {
	if (getBrowserDriver() != null) {
	    getBrowserDriver().stopEverything();
	    setBrowserDriver(null);
	}
    }

    public static boolean isBrowserOpen() {
	return (getBrowserDriver() != null);
    }
}
