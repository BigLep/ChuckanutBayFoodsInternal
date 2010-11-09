package com.chuckanutbay.webapp.employeemonitor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.event.dom.client.ClickHandler;

public class EmployeeMonitor implements EntryPoint {


	private VerticalPanel mainPanel = new VerticalPanel();
	private Label title = new Label("Employee Monitor");
	private RootPanel rootPanel;
	private final VerticalPanel verticalPanel = new VerticalPanel();
	private final Button button = new Button("");
	
	
	public void onModuleLoad() {
		
		mainPanel.add(verticalPanel);
		verticalPanel.add(title);
		title.setWidth("257px");
		
		title.setStyleName("dashboardTitle");
		mainPanel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_CENTER);
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				button.setStyleName("redButton");
			}
		});
		button.setStyleName("greenButton");
		
		verticalPanel.add(button);
		button.setSize("256px", "66px");
		mainPanel.setStyleName("mainPanel");
		mainPanel.setSpacing(10);
		mainPanel.setSize("1000px", "701px");
		//Add mainPanel to rootPanel
		RootPanel.get("EmployeeMonitor").add(mainPanel);
	}

}
