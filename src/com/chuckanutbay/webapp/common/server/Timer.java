package com.chuckanutbay.webapp.common.server;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;

public class Timer {
	private DateTime startTime;
	private Logger logger;
	
	public Timer(Logger logger2) {
		this.logger = logger2;
	}

	public Timer start() {
		startTime = new DateTime();
		return this;
	}
	
	public Timer start(String message) {
		start();
		logger.info("\t" + getCallerMethodName() + " " + message);
		return this;
	}
	
	public void logTime(String message) {
		String messageBase = "\t" + getCallerMethodName() + " " + message + " "; 
		if (startTime != null) {
			Duration duration = new Duration(startTime, new DateTime());
			logger.info(messageBase + duration.getMillis() + " milliseconds");
		} else {
			logger.info(messageBase + " TIMER NOT STARTED");
		}
	}
	
	public void stop(String message) {
		logTime(message);
		startTime = null;
	}
	
	/**
	 * Finds the name of the method that called a method within the Timer class.
	 * @return A blank string if no method is found.
	 */
	private static String getCallerMethodName() {
		for (StackTraceElement element : new Throwable().getStackTrace()) {
			if (!element.getClassName().equals("com.chuckanutbay.webapp.common.server.Timer")) {
				return element.getMethodName();
			}
		}
		return "";
	}
}
