package org.prospero.iris.exceptions;

public class IrisMailboxWaitTimeoutException extends IrisRuntimeException
{
	private static final long serialVersionUID = 1L;

	public IrisMailboxWaitTimeoutException()
	{
		super();
	}

	public IrisMailboxWaitTimeoutException( String message, Throwable cause )
	{
		super( message, cause );
	}

	public IrisMailboxWaitTimeoutException( String message )
	{
		super( message );
	}

	public IrisMailboxWaitTimeoutException( Throwable cause )
	{
		super( cause );
	}

}
