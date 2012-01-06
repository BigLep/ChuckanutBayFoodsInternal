package com.chuckanutbay.webapp.traylabelgenerator.client;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.google.gwt.user.client.ui.RootPanel;

public class OldTrayLabelGenerator extends TrayLabelGenerator {
	protected RootPanel getRootPanel() {
		return RootPanel.get("OldTrayLabelGenerator");
	}
	
	protected void setupOpenOrdersCellBrowser() {
		openOrdersCellBrowser = new OpenOrdersCellBrowser(new OldOpenOrdersTreeModel())
			.setColumnWidth(235)
			.setSize(700, 260)
			.setStyle("openOrdersCellBrowser")
			.setSelectionChangeHandler(this);
	}
	
}
