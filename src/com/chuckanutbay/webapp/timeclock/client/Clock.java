package com.chuckanutbay.webapp.timeclock.client;

import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.MIN_IN_MILLISECONDS;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A styled clock
 *
 */
public class Clock extends SimplePanel {
	private Label timeLabel = new Label();
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("h:mm a");
	private Timer timer = new Timer() {
		@Override
		public void run() {
			setTime(new Date());
		}
	};
	
	/**
	 * Sets up the clock
	 * @param time The starting time that the clock will read.
	 * @param isAutoIncrement If true, {@link Clock} will automatically keep time.
	 */
	public Clock(Date time, boolean isAutoIncrement) {
		GWT.log("Initializing Clock");
		setTime(time);
		this.add(timeLabel);
		if (isAutoIncrement) {
			timer.scheduleRepeating(MIN_IN_MILLISECONDS);
		}
		this.setStyleName("clockBackground");
	}
	
	/**
	 * Sets up the clock and sets the size
	 * @param time The starting time that the clock will read.
	 * @param isAutoIncrement If true, {@link Clock} will automatically keep time.
	 */
	public Clock(Date time, boolean isAutoIncrement, int width, int height) {
		this(time, isAutoIncrement);
		this.setPixelSize(width, height);
		
	}
	
	/**
	 * 
	 * @param time The time to set the {@link Clock} to
	 */
	public void setTime(Date time) {
		timeLabel.setText(dateFormat.format(time));
		GWT.log(dateFormat.format(time));
	}
}
