package com.chuckanutbay.webapp.dashboard.client;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

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
	private final Button timeClockReportButton = new Button("Time Clock Report");
	private RootPanel rootPanel;
	private List<Button> buttons;


	@Override
	public void onModuleLoad() {
		
		title.setStyleName("titleLabel");
		mainPanel.setSpacing(10);
		mainPanel.add(title);
		mainPanel.setStyleName("mainPanel");
		mainPanel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_CENTER);
		
		buttons = newArrayList(lotCodeManagerButton, timeClockButton, timeClockReportButton);
		for (Button button : buttons) {
			button.setStyleName("dashboardButton");
			button.addClickHandler(this);
			mainPanel.add(button);
			mainPanel.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);
		}
		
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
		if(sender == timeClockReportButton) {
			Window.open("TimeClockReport.html", "TimeClockReport", "");
		}
	}
}
