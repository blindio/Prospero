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

import java.io.InputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class IrisMessage {
    public static final String CONTENT_TYPE_TEXT = "text";
    public static final String CONTENT_TYPE_MULTIPART = "multipart";
    public static final String CONTENT_DISPOSITION_INLINE = "inline";

    protected static Logger logger = Logger.getLogger(IrisMessage.class);
    MimeMessage mimeMessage;
    String from;
    String recipient;

    public IrisMessage(String from, String recipient, InputStream messageStream) {
	this.from = from;
	this.recipient = recipient;
	try {
	    mimeMessage = new MimeMessage(
		    Session.getDefaultInstance(new Properties()), messageStream);
	} catch (MessagingException me) {
	    logger.error(me);
	    mimeMessage = new MimeMessage(
		    Session.getDefaultInstance(new Properties()));
	}
    }

    public String getFrom() {
	return from;
    }

    public String getRecipient() {
	return recipient;
    }

    public MimeMessage getMimeMessage() {
	return mimeMessage;
    }

    public String getContentType() {
	try {
	    return getMimeMessage().getContentType();
	} catch (MessagingException me) {
	    logger.error(me);
	    return null;
	}
    }

    public String getSubject() {
	try {
	    return getMimeMessage().getSubject();
	} catch (MessagingException me) {
	    logger.error(me);
	    return null;
	}
    }

    public String getBodyText() {
	try {
	    Object content = getMimeMessage().getContent();
	    return getBodyText(content).trim();
	} catch (Exception e) {
	    logger.error(e);
	    return null;
	}
    }

    private String getBodyText(Object content) {
	if (content instanceof String) {
	    return (String) content;
	} else if (content instanceof MimeMultipart) {
	    return getBodyText((MimeMultipart) content);
	} else {
	    String contentType;
	    try {
		contentType = getMimeMessage().getContentType();
	    } catch (MessagingException me) {
		logger.error(me);
		contentType = null;
	    }
	    logger.error("Unknown MIME content-type: '" + contentType + "'");
	    return null;
	}
    }

    private String getBodyText(MimeMultipart multipart) {
	StringBuilder builder = new StringBuilder();
	int size;
	try {
	    size = multipart.getCount();
	} catch (MessagingException me) {
	    logger.error(me);
	    size = 0;
	}
	for (int partIter = 0; partIter < size; partIter++) {
	    BodyPart part;
	    try {
		part = multipart.getBodyPart(partIter);
		builder.append(getBodyText(part));
	    } catch (MessagingException me) {
		logger.error(me);
	    }
	}
	if (builder.toString().trim().isEmpty()) {
	    return null;
	} else {
	    return builder.toString();
	}
    }

    private String getBodyText(BodyPart bodyPart) {
	Object content;
	String disposition;
	String bodyText = null;

	try {
	    content = bodyPart.getContent();
	    disposition = bodyPart.getDisposition();
	    if (content instanceof String
		    && disposition.toLowerCase().contains(
			    CONTENT_DISPOSITION_INLINE)) {
		return (String) bodyText;
	    }
	} catch (Exception e) {
	    logger.error(e);
	    return "";
	}

	return bodyText;
    }

}
