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
import org.prospero.core.pageobject.PageObject;

class ContextContainer {

    private StopWatch timer;
    private PageObject currentPage;
    private BrowserDriver browserDriver;

    ContextContainer() {
	currentPage = null;
	browserDriver = null;
	timer = new StopWatch();
    }

    StopWatch getTimer() {
	return timer;
    }

    void setTimer(StopWatch timer) {
	this.timer = timer;
    }

    public PageObject getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(PageObject currentPage) {
	this.currentPage = currentPage;
    }

    public BrowserDriver getBrowserDriver() {
	return browserDriver;
    }

    public void setBrowserDriver(BrowserDriver browserDriver) {
	this.browserDriver = browserDriver;
    }
}
