package org.prospero.iris.exceptions;

public class IrisRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public IrisRuntimeException()
	{
	}

	public IrisRuntimeException( String message )
	{
		super( message );
	}

	public IrisRuntimeException( Throwable cause )
	{
		super( cause );
	}

	public IrisRuntimeException( String message, Throwable cause )
	{
		super( message, cause );
	}

}
