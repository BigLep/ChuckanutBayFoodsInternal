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
	private final Button lotCodeManagerButton = new Button("LotCodeManager");
	private RootPanel rootPanel;


	@Override
	public void onModuleLoad() {
		title.setStyleName("title");
		lotCodeManagerButton.setStyleName("buttons");
		lotCodeManagerButton.addClickHandler(this);
		mainPanel.add(title);
		mainPanel.add(lotCodeManagerButton);
		mainPanel.setStyleName("mainPanel");
		// FIXME: set these through CSS
		mainPanel.setSpacing(10);
		mainPanel.setWidth("500px");
		mainPanel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHorizontalAlignment(lotCodeManagerButton, HasHorizontalAlignment.ALIGN_CENTER);
		//Add mainPanel to rootPanel
		rootPanel = RootPanel.get("dashboard");
		rootPanel.add(mainPanel);
	}


	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if(sender == lotCodeManagerButton) {
			Window.open("LotCodeManager.html", "LotCodeManager", "");
		}

	}

}
