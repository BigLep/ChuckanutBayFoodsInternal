package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.ArrayList;

import com.chuckanutbay.webapp.shared.MyIconBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class LotCodeUtil {
	
	static public final String FLEX_TABLE_WIDTH = "825px";
	static public final String DATE_BOX_WIDTH = "80px";
	
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
	
	static public void makePopupVisible(DecoratedPopupPanel popup, Widget sender, int top, int left) {
        int adjustedLeft = sender.getAbsoluteLeft() + left;
        int adjustedTop = sender.getAbsoluteTop() + top;
        popup.setPopupPosition(adjustedLeft, adjustedTop);
        popup.setWidth("150px");
        popup.show();
	}
	
	public static MyIconBundle icons = (MyIconBundle) GWT.create(MyIconBundle.class);
	
	public static final String LOT_MANAGEMENT_SHORT_DATE_FORMAT = "MMM d, yyyy";
    public static DateTimeFormat dateFormat = DateTimeFormat.getFormat(LOT_MANAGEMENT_SHORT_DATE_FORMAT);
    
    public static <T> ArrayList<T> newArrayList() {
    	return new ArrayList<T>();
    }
}
