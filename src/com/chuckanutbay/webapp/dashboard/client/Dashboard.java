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

public class Dashboard implements EntryPoint, ClickHandler {

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label title = new Label("Chuckanut Bay Internal Web Apps");
	private Button lotCodeManagerButton = new Button("LotCodeManager");
	private RootPanel rootPanel;
	
	
	public void onModuleLoad() {
		
		title.setStyleName("dashboardTitle");
		lotCodeManagerButton.setStyleName("buttons");
		lotCodeManagerButton.addClickHandler(this);
		mainPanel.add(title);
		mainPanel.add(lotCodeManagerButton);
		mainPanel.setStyleName("mainPanel");
		mainPanel.setSpacing(10);
		mainPanel.setWidth("500px");
		mainPanel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHorizontalAlignment(lotCodeManagerButton, HasHorizontalAlignment.ALIGN_CENTER);
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
		
	}

}
