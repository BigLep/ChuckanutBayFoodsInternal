package com.chuckanutbay.webapp.common.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CbHorizontalPanel extends HorizontalPanel {
	
	public CbHorizontalPanel(Widget...widgets) {
		super();
		this.addWidget(widgets);
	}
	
	public CbHorizontalPanel addWidget(Widget widget, HorizontalAlignmentConstant alignment) {
		super.add(widget);
		super.setCellHorizontalAlignment(widget, alignment);
		return this;
	}
	
	public CbHorizontalPanel addWidget(Widget widget, VerticalAlignmentConstant alignment) {
		super.add(widget);
		super.setCellVerticalAlignment(widget, alignment);
		return this;
	}
	
	public CbHorizontalPanel addWidget(Widget widget, HorizontalAlignmentConstant hAlignment, VerticalAlignmentConstant vAlignment) {
		super.add(widget);
		super.setCellHorizontalAlignment(widget, hAlignment);
		super.setCellVerticalAlignment(widget, vAlignment);
		return this;
	}
	
	public CbHorizontalPanel addWidget(Widget...widgets) {
		for(Widget widget : widgets) {
			super.add(widget);
		}
		return this;
	}
	
	public CbHorizontalPanel setStyle(String style) {
		super.setStyleName(style);
		return this;
	}
	
	public CbHorizontalPanel setWidth(int width) {
		super.setWidth(width + "px");
		return this;
	}
	
	public CbHorizontalPanel setHeight(int height) {
		super.setHeight(height + "px");
		return this;
	}
	
	public CbHorizontalPanel setSize(int width, int height) {
		super.setSize(width + "px", height + "px");
		return this;
	}
	
	public CbHorizontalPanel setCellSpacing(int spacing) {
		super.setSpacing(spacing);
		return this;
	}
	
	/**
	 * Replaces the first child widget parameter with the second one.
	 * @param replaceThis To be removed
	 * @param withThis To be added
	 * @param vAlignment
	 * @return
	 */
	public CbHorizontalPanel replaceWidget(Widget replaceThis, Widget withThis, VerticalAlignmentConstant vAlignment) {
		int index = super.getWidgetIndex(replaceThis);
		super.remove(index);
		super.insert(withThis, index);
		super.setCellVerticalAlignment(withThis, vAlignment);
		return this;
	}

}
