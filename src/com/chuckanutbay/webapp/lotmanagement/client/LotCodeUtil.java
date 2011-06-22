package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.chuckanutbay.documentation.ReferenceSource.EffectiveJava;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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

	static public void makeButtonWithIcon(Button button, String icon, String text) {
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

    public static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("MMM d, yyyy");


    /**
     * @see EffectiveJava#Static_factories_instead_of_constructors
     * @param <T>
     * @return new {@link ArrayList}.
     */
    public static <T> ArrayList<T> newArrayList() {
    	return new ArrayList<T>();
    }
    
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
