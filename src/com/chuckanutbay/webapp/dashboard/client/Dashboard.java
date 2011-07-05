package com.chuckanutbay.webapp.dashboard.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Serves as a dashboard to access the various applications.
 */
public class Dashboard implements EntryPoint, ClickHandler {

	private final VerticalPanel mainPanel = new VerticalPanel();
	private final Label title = new Label("Chuckanut Bay Internal Web Apps");
	private final Button lotCodeManagerButton = new Button("Lot Code Manager");
	private final Button timeClockButton = new Button("Time Clock");
	private RootPanel rootPanel;


	@Override
	public void onModuleLoad() {
		title.setStyleName("titleLabel");
		lotCodeManagerButton.setStyleName("lotCodeManagerButton");
		lotCodeManagerButton.addClickHandler(this);
		timeClockButton.setStyleName("timeClockButton");
		timeClockButton.addClickHandler(this);
		mainPanel.setSpacing(10);
		mainPanel.add(title);
		mainPanel.add(lotCodeManagerButton);
		mainPanel.add(timeClockButton);
		mainPanel.setStyleName("mainPanel");
		mainPanel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHorizontalAlignment(lotCodeManagerButton, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHorizontalAlignment(timeClockButton, HasHorizontalAlignment.ALIGN_CENTER);
		//Add mainPanel to rootPanel
		rootPanel = RootPanel.get("Dashboard");
		rootPanel.add(mainPanel);
	}


	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if(sender == lotCodeManagerButton) {
			Window.open("LotCodeManager.html", "LotCodeManager", "");
		}
		if(sender == timeClockButton) {
			Window.open("TimeClock.html", "TimeClock", "");
		}

	}

}
