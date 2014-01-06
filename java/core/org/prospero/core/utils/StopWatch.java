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
		}
		else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}
	
	// elaspsed time in seconds
	public long getElapsedTimeSecs() {
		long elapsed;
		if (running) {
			elapsed = ((System.currentTimeMillis() - startTime) / 1000);
		}
		else {
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
		}
		catch (InterruptedException e) {
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
