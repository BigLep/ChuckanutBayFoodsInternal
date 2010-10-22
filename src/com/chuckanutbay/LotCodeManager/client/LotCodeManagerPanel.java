package com.chuckanutbay.LotCodeManager.client;

import com.google.gwt.user.client.ui.*;

public abstract class LotCodeManagerPanel extends Widget {
	
	abstract void setUpPanel();
	
	abstract void populateFlexTable();
	
	abstract void updateDB();
	
	abstract Panel getPanel();
	
}
