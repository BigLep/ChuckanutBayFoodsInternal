package com.chuckanutbay.webapp.lotmanagement.client;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public abstract class LotCodeManagerPanel extends Widget {
	
	abstract void setUpPanel();
	
	abstract void populateFlexTable();
	
	abstract void updateDB();
	
	abstract Panel getPanel();
	
}
