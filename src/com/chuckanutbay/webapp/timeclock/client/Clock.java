package com.chuckanutbay.webapp.timeclock.client;

import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.MIN_IN_MILLISECONDS;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A styled clock that can be used like any other GWT Widget. The default style 
 * is "clockBackground" in the TimeClock.css file. The {@link DateTimeFormat}
 * is "h:mm a" which would look like "11:30 AM" or "3:00 PM"
 */
public class Clock extends SimplePanel {
	private final Label timeLabel = new Label();
	private static final DateTimeFormat TIME_FORMAT = DateTimeFormat.getFormat("h:mm a");
	
	private Timer timer = new Timer() {
		@Override
		public void run() {
			refresh();
		}
	};
	
	/**
	 * Constructs a new clock with the given dimensions and default style.
	 */
	public Clock(int width, int height) {
		refresh();
		add(timeLabel);
		setStyleName("clockBackground");
		setPixelSize(width, height);
		timer.scheduleRepeating(MIN_IN_MILLISECONDS);
		
	}
	
	/**
	 * Sets the time on the clock to be the current computer time.
	 */
	public void refresh() {
		timeLabel.setText(TIME_FORMAT.format(new Date()));
	}
}
