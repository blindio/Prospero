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
package io.github.blindio.prospero.iris;

import java.util.Deque;
import java.util.Iterator;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author tlittle
 */
public class IrisMailbox extends Observable {
    protected static Logger logger = Logger.getLogger(IrisMailbox.class);

    private Deque<IrisMessage> messages;

    private String mailboxName;
    private String recipientFilterPattern;
    private String senderFilterPattern;
    private String subjectFilterPattern;
    private String bodyFilterPattern;

    private Pattern recipientFilter;
    private Pattern senderFilter;
    private Pattern subjectFilter;
    private Pattern bodyFilter;

    public IrisMailbox(String name) {
	this(name, null, null, null, null);
    }

    public IrisMailbox(String mailboxName, String recipientFilterPattern,
	    String senderFilterPattern, String subjectFilterPattern,
	    String bodyFilterPattern) {
	messages = new LinkedBlockingDeque<IrisMessage>();
	this.mailboxName = mailboxName;
	setRecipientFilterPattern(recipientFilterPattern);
	setSenderFilterPattern(senderFilterPattern);
	setSubjectFilterPattern(subjectFilterPattern);
	setBodyFilterPattern(bodyFilterPattern);
    }

    public boolean addMessage(IrisMessage message) {
	boolean matchesFilter = true;

	if (recipientFilter != null && matchesFilter == true) {
	    matchesFilter = recipientFilter.matcher(message.getRecipient())
		    .find();
	}
	if (senderFilter != null && matchesFilter == true) {
	    matchesFilter = senderFilter.matcher(message.getFrom()).find();
	}
	if (subjectFilter != null && matchesFilter == true) {
	    matchesFilter = subjectFilter.matcher(message.getSubject()).find();
	}
	if (bodyFilter != null && matchesFilter == true) {
	    matchesFilter = bodyFilter.matcher(message.getBodyText()).find();
	}

	if (matchesFilter) {
	    messages.push(message);
	    setChanged();
	    notifyObservers(message);
	}

	return matchesFilter;
    }

    public String getMailboxName() {
	return mailboxName;
    }

    public void setMailboxName(String mailboxName) {
	this.mailboxName = mailboxName;
    }

    public String getRecipientFilterPattern() {
	return recipientFilterPattern;
    }

    public void setRecipientFilterPattern(String recipientFilterPattern) {
	this.recipientFilterPattern = recipientFilterPattern;
	this.recipientFilter = getRecipientFilterPattern() != null ? Pattern
		.compile(getRecipientFilterPattern()) : null;
    }

    public String getSenderFilterPattern() {
	return senderFilterPattern;
    }

    public void setSenderFilterPattern(String senderFilterPattern) {
	this.senderFilterPattern = senderFilterPattern;
	this.senderFilter = getSenderFilterPattern() != null ? Pattern
		.compile(getSenderFilterPattern()) : null;
    }

    public String getSubjectFilterPattern() {
	return subjectFilterPattern;
    }

    public void setSubjectFilterPattern(String subjectFilterPattern) {
	this.subjectFilterPattern = subjectFilterPattern;
	this.subjectFilter = getSubjectFilterPattern() != null ? Pattern
		.compile(getSubjectFilterPattern()) : null;
    }

    public String getBodyFilterPattern() {
	return bodyFilterPattern;
    }

    public void setBodyFilterPattern(String bodyFilterPattern) {
	this.bodyFilterPattern = bodyFilterPattern;
	this.bodyFilter = getBodyFilterPattern() != null ? Pattern
		.compile(getBodyFilterPattern()) : null;
    }

    public IrisObserver getNewObserver() {
	return new IrisObserver(this);
    }

    public Iterator<IrisMessage> getNewestToOldestIterator() {
	return messages.iterator();
    }

    public Iterator<IrisMessage> getOldestToNewestIterator() {
	return messages.descendingIterator();
    }

    public IrisMessage getOldestMessage() {
	return messages.getLast();
    }

    public IrisMessage getNewestMail() {
	return messages.getFirst();
    }

    public void clearMesssages() {
	messages.clear();
    }

    public void detach() {
	IrisServer.removeMailbox(this);
	deleteObservers();
    }

    public int size() {
	return messages.size();
    }
}
