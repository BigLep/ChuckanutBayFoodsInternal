package com.chuckanutbay.webapp.common.client;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.TextBox;

public class CbTextBox extends TextBox {
	private FocusWidgetLinker linker; 
	
	public CbTextBox() {
		super();
		linker = new FocusWidgetLinker(this);
	}
	
	public CbTextBox(int width) {
		this();
		this.setWidth(width);
	}
	
	public CbTextBox setNextWidget(FocusWidget nextWidget) {
		linker.setNextWidget(nextWidget);
		return this;
	}
	
	public CbTextBox setWidth(int width) {
		this.setWidth(width + "px");
		return this;
	}
}
