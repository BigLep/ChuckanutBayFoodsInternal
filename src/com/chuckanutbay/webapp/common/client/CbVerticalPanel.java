package com.chuckanutbay.webapp.common.client;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CbVerticalPanel extends VerticalPanel {
	
	public CbVerticalPanel(Widget...widgets) {
		super();
		this.addWidget(widgets);
	}
	
	public CbVerticalPanel addWidget(Widget widget, HorizontalAlignmentConstant alignment) {
		super.add(widget);
		super.setCellHorizontalAlignment(widget, alignment);
		return this;
	}
	
	public CbVerticalPanel addWidget(Widget widget, VerticalAlignmentConstant alignment) {
		super.add(widget);
		super.setCellVerticalAlignment(widget, alignment);
		return this;
	}
	
	public CbVerticalPanel addWidget(Widget widget, HorizontalAlignmentConstant hAlignment, VerticalAlignmentConstant vAlignment) {
		super.add(widget);
		super.setCellHorizontalAlignment(widget, hAlignment);
		super.setCellVerticalAlignment(widget, vAlignment);
		return this;
	}
	
	public CbVerticalPanel addWidget(Widget...widgets) {
		for(Widget widget : widgets) {
			super.add(widget);
		}
		return this;
	}
	
	public CbVerticalPanel setStyle(String style) {
		super.setStyleName(style);
		return this;
	}
	
	public CbVerticalPanel setWidth(int width) {
		super.setWidth(width + "px");
		return this;
	}
	
	public CbVerticalPanel setHeight(int height) {
		super.setHeight(height + "px");
		return this;
	}
	
	public CbVerticalPanel setSize(int width, int height) {
		super.setSize(width + "px", height + "px");
		return this;
	}
	
	public CbVerticalPanel setCellSpacing(int spacing) {
		super.setSpacing(spacing);
		return this;
	}
	
	/**
	 * Replaces the first child widget parameter with the second one.
	 * @param replaceThis To be removed
	 * @param withThis To be added
	 * @param hAlignment
	 * @return
	 */
	public CbVerticalPanel replaceWidget(Widget replaceThis, Widget withThis, HorizontalAlignmentConstant hAlignment) {
		int index = super.getWidgetIndex(replaceThis);
		super.remove(index);
		super.insert(withThis, index);
		super.setCellHorizontalAlignment(withThis, hAlignment);
		return this;
	}

}
