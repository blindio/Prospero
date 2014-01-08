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

import static org.prospero.core.utils.AssertJUnitParams.fail;

import org.apache.log4j.Logger;

public class StopWatch {

    private static Logger logger = Logger.getLogger(StopWatch.class);

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    public static final int MS_PER_SECOND = 1000;

    public void start() {
	this.startTime = System.currentTimeMillis();
	this.running = true;
    }

    public void stop() {
	this.stopTime = System.currentTimeMillis();
	this.running = false;
    }

    public boolean isRunning() {
	return running;
    }

    // elaspsed time in milliseconds
    public long getElapsedTime() {
	long elapsed;
	if (running) {
	    elapsed = (System.currentTimeMillis() - startTime);
	} else {
	    elapsed = (stopTime - startTime);
	}
	return elapsed;
    }

    // elaspsed time in seconds
    public long getElapsedTimeSecs() {
	long elapsed;
	if (running) {
	    elapsed = ((System.currentTimeMillis() - startTime) / 1000);
	} else {
	    elapsed = ((stopTime - startTime) / 1000);
	}
	return elapsed;
    }

    /**
     * Call Thread.sleep and call AssertionFramework.fail if an Interrupted
     * Exception is Thrown
     * 
     * @param milliseconds
     */
    public static void sleep(int milliseconds) {
	try {
	    Thread.sleep(milliseconds);
	} catch (InterruptedException e) {
	    fail(e.getMessage());
	}
    }

    /**
     * Call Thread.sleep for one second
     */
    public static void sleepForASecond() {
	sleep(MS_PER_SECOND);
    }

    public static Logger getLogger() {
	return logger;
    }
}
