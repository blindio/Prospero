package org.prospero.core.exceptions;

public class ProsperoConfigConversionException extends ProsperoRuntimeAutomationException {
	
	private static final long serialVersionUID = 3581075498863287223L;
	
	public ProsperoConfigConversionException() {
	}
	
	public ProsperoConfigConversionException(String message) {
		super(message);
	}
	
	public ProsperoConfigConversionException(Throwable cause) {
		super(cause);
	}
	
	public ProsperoConfigConversionException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
