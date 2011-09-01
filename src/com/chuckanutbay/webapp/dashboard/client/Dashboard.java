package com.chuckanutbay.webapp.dashboard.client;

import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_CENTER;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newLabel;
import static com.google.common.collect.Lists.newArrayList;

import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Serves as a dashboard to access the various applications.
 */
public class Dashboard implements EntryPoint, ClickHandler {

	private CbVerticalPanel mainPanel;
	private final Button lotCodeManagerButton = new Button("Lot Code Manager");
	private final Button timeClockButton = new Button("Time Clock");
	private final Button timeClockReportButton = new Button("Time Clock Report");
	private final Button trayLabelGeneratorButton = new Button("Tray Label Generator");


	@Override
	public void onModuleLoad() {
		mainPanel = new CbVerticalPanel()
			.addWidget(newLabel("Chuckanut Bay Internal Web Apps", "titleLabel"), H_ALIGN_CENTER)
			.setCellSpacing(10)
			.setStyle("mainPanel");
		
		for (Button button : newArrayList(lotCodeManagerButton, timeClockButton, timeClockReportButton, trayLabelGeneratorButton)) {
			button.setStyleName("dashboardButton");
			button.addClickHandler(this);
			mainPanel.addWidget(button, H_ALIGN_CENTER);
		}
		
		//Add mainPanel to rootPanel
		RootPanel.get("Dashboard").add(mainPanel);
	}


	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if(sender == lotCodeManagerButton) {
			Window.open("LotCodeManager.html", "LotCodeManager", "");
		} else if(sender == timeClockButton) {
			Window.open("TimeClock.html", "TimeClock", "");
		} else if(sender == timeClockReportButton) {
			Window.open("TimeClockReport.html", "TimeClockReport", "");
		} else if(sender == trayLabelGeneratorButton) {
			Window.open("TrayLabelGenerator.html", "TrayLabelGenerator", "");
		}
	}
}
