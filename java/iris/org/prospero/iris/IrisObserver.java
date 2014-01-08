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
package org.prospero.iris;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.prospero.iris.exceptions.IrisMailboxWaitTimeoutException;

public class IrisObserver implements Observer {

    public static final long MILLIS_IN_SECONDS = 1000;
    public static final long ONE_HUNDRED_MILLISECONDS = 100;
    public static final int ONE_MINUTE = 60;

    private IrisMailbox mailbox;
    private IrisMessage latestMail;
    private boolean hasNewMail;

    public IrisObserver(IrisMailbox mailbox) {
	this.mailbox = mailbox;
	mailbox.addObserver(this);
	setNewMail(false);
    }

    @Override
    public void update(Observable o, Object arg) {
	mailbox = (IrisMailbox) o;
	latestMail = (IrisMessage) arg;
	setNewMail(true);
    }

    public IrisMailbox getMailbox() {
	return mailbox;
    }

    public IrisMessage getLatestMail() {
	return latestMail;
    }

    public IrisMessage waitForMail(int timeout) {
	long startTime = new Date().getTime();
	long timeOutInMillis = timeout * MILLIS_IN_SECONDS;

	while (!hasNewMail()) {
	    try {
		Thread.sleep(ONE_HUNDRED_MILLISECONDS);
	    } catch (InterruptedException ie) {
		// TODO: do something
	    }

	    long currentWait = new Date().getTime() - startTime;

	    if (currentWait > timeOutInMillis) {
		throw new IrisMailboxWaitTimeoutException(
			"Wait time for RequestResponse of [" + currentWait
				+ "] exceeds timeout of [" + timeOutInMillis
				+ "] milliseconds");
	    }
	}

	setNewMail(false);
	return getLatestMail();
    }

    public IrisMessage waitForMail() {
	return waitForMail(ONE_MINUTE);
    }

    private boolean hasNewMail() {
	return hasNewMail;
    }

    private void setNewMail(boolean newMail) {
	this.hasNewMail = newMail;
    }

    public void detach() {
	getMailbox().deleteObserver(this);
    }

    @Override
    protected void finalize() throws Throwable {
	detach();
    }
}
