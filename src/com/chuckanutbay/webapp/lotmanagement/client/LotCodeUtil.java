package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

// FIXME: add javadocs
// TODO: it seems like much of this code can be shared across applications.
// Lets put it in the common web app.
public class LotCodeUtil {

	/**
	 * @deprecated Use {@link Logger} instead
	 * @see "http://code.google.com/webtoolkit/doc/latest/DevGuideLogging.html"
	 */
    @Deprecated
	static public void log(String message) {
    	GWT.log(message);
    }

	static public void makePopupVisible(DecoratedPopupPanel popup, Widget sender, int top, int left) {
        int adjustedLeft = sender.getAbsoluteLeft() + left;
        int adjustedTop = sender.getAbsoluteTop() + top;
        popup.setPopupPosition(adjustedLeft, adjustedTop);
        popup.setWidth("150px");
        popup.show();
	}

    public static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("MMM d, yyyy");

    
    public static void addMouseOverHandler(MouseOverHandler handler, FocusWidget... widgets) {
		for (FocusWidget widget : widgets) {
			widget.addMouseOverHandler(handler);
		}
	}
    
    public static void addClickHandler(ClickHandler handler, FocusWidget... widgets) {
		for (FocusWidget widget : widgets) {
			widget.addClickHandler(handler);
		}
	}
}
