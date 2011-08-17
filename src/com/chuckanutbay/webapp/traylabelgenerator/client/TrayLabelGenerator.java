package com.chuckanutbay.webapp.traylabelgenerator.client;

import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newHorizontalPanel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newSimplePanel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newVerticalPanel;
import static com.chuckanutbay.webapp.common.client.IconUtil.LOGO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TrayLabelGenerator implements EntryPoint {
	private RootPanel rootPanel = RootPanel.get("TrayLabelGenerator");

	@Override
	public void onModuleLoad() {
		Image logo = new Image(LOGO);
		logo.setWidth("400px");
		Label title = new Label("Tray Label Generator");
		title.setStyleName("title");
		HorizontalPanel headerPanel = newHorizontalPanel(logo, title);
		headerPanel.setCellHorizontalAlignment(logo, HasHorizontalAlignment.ALIGN_LEFT);
		headerPanel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.setCellVerticalAlignment(title, HasVerticalAlignment.ALIGN_MIDDLE);
		headerPanel.setWidth("748px");
		VerticalPanel mainPanel = newVerticalPanel(headerPanel);
		SimplePanel backgroundPanel = newSimplePanel(mainPanel);
		backgroundPanel.setStyleName("backgroundPanel");
		
		rootPanel.add(backgroundPanel);
		
	}

}
