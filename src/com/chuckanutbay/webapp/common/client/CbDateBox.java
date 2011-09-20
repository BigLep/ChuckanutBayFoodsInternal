package com.chuckanutbay.webapp.common.client;


import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class CbDateBox extends DateBox {
	private FocusWidgetLinker linker;
	
	public CbDateBox() {
		super();
		this.setValue(new Date());
		super.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("M/d/yy")));
		linker = new FocusWidgetLinker(this.getTextBox());
	}
	
	public CbDateBox(int width) {
		this();
		this.setWidth(width);
	}
	
	public TextBox setNextWidget(FocusWidget nextWidget) {
		linker.setNextWidget(nextWidget);
		return this.getTextBox();
	}
	
	public CbDateBox setWidth(int width) {
		this.setWidth(width + "px");
		return this;
	}
}
