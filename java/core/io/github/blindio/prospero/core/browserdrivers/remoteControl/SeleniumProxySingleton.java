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
package io.github.blindio.prospero.core.browserdrivers.remoteControl;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import org.apache.log4j.Logger;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

public abstract class SeleniumProxySingleton {

    private static SeleniumServer jettyProxyInstance;

    public static final String SELENIUM_SERVER_HOST = "localhost";
    public static final int DEFAULT_SELENIUM_SERVER_PORT = 4444;
    private static boolean jettyProxyWasStartedByATest = false;

    static Logger logger = Logger.getLogger(SeleniumProxySingleton.class);

    private SeleniumProxySingleton() {
	throw new RuntimeException("Don't instantiate me");
    }

    public static void startSeleniumServer() throws Exception {
	startSeleniumServer(DEFAULT_SELENIUM_SERVER_PORT);
    }

    public static void startSeleniumServer(int port) throws Exception {
	if (isPortAvailable(port)) {
	    launchJettyProxy(port);
	    jettyProxyWasStartedByATest = true;
	}
    }

    private static void launchJettyProxy(int port) throws Exception {
	logger.info("Nothing is listening on port " + port);
	logger.info("Launching SeleniumServer on port " + port);
	startJettyProxy(port);
    }

    private static boolean isPortAvailable(int port) {
	ServerSocket ss = null;
	DatagramSocket ds = null;

	try {
	    ss = new ServerSocket(port);
	    ss.setReuseAddress(true);
	    ds = new DatagramSocket(port);
	    ds.setReuseAddress(true);
	    return true;
	} catch (IOException e) {
	} finally {
	    if (ds != null) {
		ds.close();
	    }

	    if (ss != null) {
		try {
		    ss.close();
		} catch (IOException e) {
		    /* should not be thrown */
		}
	    }
	}

	return false;
    }

    protected static void startJettyProxy(int port) throws Exception {
	RemoteControlConfiguration config = new RemoteControlConfiguration();
	config.setPort(port);
	config.setEnsureCleanSession(true);
	jettyProxyInstance = new SeleniumServer(config);
	jettyProxyInstance.start();
    }

    public static void stopJettyProxy() {
	if (jettyProxyWasStartedByATest) {
	    jettyProxyInstance.stop();
	}
    }

}
