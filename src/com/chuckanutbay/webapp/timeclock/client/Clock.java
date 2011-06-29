package com.chuckanutbay.webapp.timeclock.client;

import java.text.DateFormat;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A styled clock
 * @author mloeppky
 *
 */
public class Clock extends SimplePanel {
	public static final int MIN_IN_MILLISECONDS = 60000;
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
		setTime(time);
		this.add(timeLabel);
		if (isAutoIncrement) {
			timer.scheduleRepeating(MIN_IN_MILLISECONDS);
		}
		this.setPixelSize(width, height);
		this.setStyleName("clockBackground");
	}
	
	/**
	 * 
	 * @param time The time to set the {@link Clock} to
	 */
	public void setTime(Date time) {
		timeLabel.setText(dateFormat.format(time));
	}
}
