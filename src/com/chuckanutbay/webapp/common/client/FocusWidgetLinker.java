package com.chuckanutbay.webapp.common.client;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.FocusWidget;

public class FocusWidgetLinker {
	private FocusWidget nextWidget;
	private static final int TAB = 9;
	private static final int ENTER = 13;
	
	
	public FocusWidgetLinker(FocusWidget widget) {
		super();
		widget.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keyCode = event.getNativeKeyCode();
				if (keyCode == TAB || keyCode == ENTER) {
					event.stopPropagation();
					event.preventDefault();
					nextWidget.setFocus(true);
				}
			}
			
		});
	}
	
	/**
	 * When the tab key or the enter key is pushed the nextWidget will gain focus.
	 * @param nextWidget
	 * @return
	 */
	public FocusWidgetLinker setNextWidget(FocusWidget nextWidget) {
		this.nextWidget = nextWidget;
		return this;
	}
	
	
}
