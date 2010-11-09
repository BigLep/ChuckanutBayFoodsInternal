package com.chuckanutbay.webapp.recievinginspection.client;

import java.util.ArrayList;

import com.chuckanutbay.webapp.common.shared.MyIconBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class RecievingInspectionUtil {
	
    static public void log(String message) {
    	GWT.log(message);
    }
    
	static public void makeButtonWithIcon(Button button, ImageResource icon, String text) {
		HorizontalPanel buttonPanel = new HorizontalPanel();
		Image formatedIcon = new Image(icon);
		HTML formatedText = new HTML(text);
		buttonPanel.setSpacing(2);
		buttonPanel.add(formatedIcon);
		buttonPanel.add(formatedText);
		buttonPanel.setStyleName("center");
		button.setHTML(buttonPanel.getElement().getString());
	}
	
	public static MyIconBundle icons = (MyIconBundle) GWT.create(MyIconBundle.class);
	
    public static <T> ArrayList<T> newArrayList() {
    	return new ArrayList<T>();
    }
}
