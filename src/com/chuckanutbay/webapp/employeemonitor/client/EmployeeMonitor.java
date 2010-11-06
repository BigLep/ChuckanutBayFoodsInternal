package com.chuckanutbay.webapp.employeemonitor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EmployeeMonitor implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label label = new Label("hello");
	
	@Override
	public void onModuleLoad() {
		mainPanel.add(label);
		RootPanel.get("EmployeeMonitor").add(mainPanel);

	}

}
