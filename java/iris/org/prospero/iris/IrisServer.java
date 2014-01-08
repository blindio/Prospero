package org.prospero.iris;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.subethamail.smtp.TooMuchDataException;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import org.prospero.iris.exceptions.IrisRuntimeException;

public class IrisServer implements SimpleMessageListener
{
	public static final String ALLMAIL_MAILBOX_NAME = "ALL_MAIL";

	protected static Logger logger = Logger.getLogger( IrisServer.class );
	private SMTPServer server = null;
	private static IrisServer serverSingleton = null;
	private Map<String, IrisMailbox> mailboxes;

	private IrisServer()
	{
		server = new SMTPServer( new SimpleMessageListenerAdapter( this ) );
		mailboxes = new ConcurrentHashMap<String, IrisMailbox>();
		IrisMailbox allMail = new IrisMailbox( ALLMAIL_MAILBOX_NAME );
		mailboxes.put( allMail.getMailboxName(), allMail );
	}

	private IrisServer( int port )
	{
		this();
		server.setPort( port );
	}

	private IrisServer( int port, String hostname )
	{
		this();
		server.setPort( port );
		server.setHostName( hostname );
	}

	public static synchronized void startServer()
	{
		if ( serverSingleton == null )
		{
			serverSingleton = new IrisServer();
			serverSingleton.getServer().start();
		}
	}

	public static synchronized void startServer( int port )
	{
		if ( serverSingleton == null )
		{
			serverSingleton = new IrisServer( port );
			serverSingleton.getServer().start();
		}
	}

	public static synchronized void startServer( int port, String hostname )
	{
		if ( serverSingleton == null )
		{
			serverSingleton = new IrisServer( port, hostname );
			serverSingleton.getServer().start();
		}
	}

	public static synchronized void stopServer()
	{
		if ( serverSingleton != null )
		{
			serverSingleton.getServer().stop();
		}
	}

	public static synchronized void shutdownServer()
	{
		stopServer();
		serverSingleton = null;
	}

	private SMTPServer getServer()
	{
		return this.server;
	}

	public boolean accept( String from, String recipient )
	{
		return true;
	}

	public void deliver( String from, String recipient, InputStream data ) throws TooMuchDataException,
			IOException
	{

		IrisMessage message = new IrisMessage( from, recipient, data );
		for ( IrisMailbox currMailbox : getMailboxes() )
		{
			currMailbox.addMessage( message );
		}
	}

	public static void addMailbox( IrisMailbox newMailbox )
	{
		Iterator<IrisMessage> iter = getAllMailMailbox().getOldestToNewestIterator();
		while ( iter.hasNext() )
		{
			newMailbox.addMessage( iter.next() );
		}
		serverSingleton.mailboxes.put( newMailbox.getMailboxName(), newMailbox );
	}

	public static void removeMailbox( String mailboxName )
	{
		if ( mailboxName != ALLMAIL_MAILBOX_NAME )
		{
			serverSingleton.mailboxes.remove( mailboxName );
		}
		else
		{
			throw new IrisRuntimeException( "Cannot delete mailbox named: " + ALLMAIL_MAILBOX_NAME );
		}
	}

	public static void removeMailbox( IrisMailbox mailbox )
	{
		removeMailbox( mailbox.getMailboxName() );
	}

	public static IrisMailbox getMailbox( String mailboxName )
	{
		return serverSingleton.mailboxes.get( mailboxName );
	}

	public static IrisMailbox getAllMailMailbox()
	{
		return getMailbox( ALLMAIL_MAILBOX_NAME );
	}

	public static Collection<IrisMailbox> getMailboxes()
	{
		return serverSingleton.mailboxes.values();
	}

	public static int getPort()
	{
		return serverSingleton != null ? serverSingleton.server.getPort() : null;
	}

	public static String getHostName()
	{
		return serverSingleton != null ? serverSingleton.server.getHostName() : null;
	}
}
