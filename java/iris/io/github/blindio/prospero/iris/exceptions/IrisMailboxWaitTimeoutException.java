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
package io.github.blindio.prospero.iris.exceptions;

public class IrisMailboxWaitTimeoutException extends IrisRuntimeException {
    private static final long serialVersionUID = 1L;

    public IrisMailboxWaitTimeoutException() {
	super();
    }

    public IrisMailboxWaitTimeoutException(String message, Throwable cause) {
	super(message, cause);
    }

    public IrisMailboxWaitTimeoutException(String message) {
	super(message);
    }

    public IrisMailboxWaitTimeoutException(Throwable cause) {
	super(cause);
    }

}
