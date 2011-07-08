package com.chuckanutbay.webapp.timeclock.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TimeClockReport implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		//Add mainPanel to rootPanel
		RootPanel.get("TimeClockReport").add(new Label("Hello World"));
	}

}
