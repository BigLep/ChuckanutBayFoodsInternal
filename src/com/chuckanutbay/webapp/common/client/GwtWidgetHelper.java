package com.chuckanutbay.webapp.common.client;

import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtWidgetHelper {
	
	public static final VerticalAlignmentConstant V_ALIGN_TOP = HasVerticalAlignment.ALIGN_TOP;
	public static final VerticalAlignmentConstant V_ALIGN_MIDDLE = HasVerticalAlignment.ALIGN_MIDDLE;
	public static final VerticalAlignmentConstant V_ALIGN_BOTTOM = HasVerticalAlignment.ALIGN_BOTTOM;
	public static final HorizontalAlignmentConstant H_ALIGN_LEFT = HasHorizontalAlignment.ALIGN_LEFT;
	public static final HorizontalAlignmentConstant H_ALIGN_CENTER = HasHorizontalAlignment.ALIGN_CENTER;
	public static final HorizontalAlignmentConstant H_ALIGN_RIGHT = HasHorizontalAlignment.ALIGN_RIGHT;
			
	public static HorizontalPanel newHorizontalPanel(Widget...widgets) {
		HorizontalPanel hPanel = new HorizontalPanel();
		for (Widget widget : widgets) {
			hPanel.add(widget);
		}
		return hPanel;
	}
	public static VerticalPanel newVerticalPanel(Widget...widgets) {
		VerticalPanel vPanel = new VerticalPanel();
		for (Widget widget : widgets) {
			vPanel.add(widget);
		}
		return vPanel;
	}
	public static SimplePanel newSimplePanel(Widget...widgets) {
		SimplePanel simplePanel = new SimplePanel();
		for (Widget widget : widgets) {
			simplePanel.add(widget);
		}
		return simplePanel;
	}
	public static SimplePanel newSimplePanel(Widget widget, String style) {
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.add(widget);
		simplePanel.setStyleName(style);
		return simplePanel;
	}
	public static FlexTable newFlexTable(String...strings) {
		FlexTable ft = new FlexTable();
		populateFlexTableRow(ft, 0, 0, strings);
		return ft;
	}
	public static void populateFlexTableRow(FlexTable flexTable, int row, int startColumn, String...strings) {
		for (int i = 0; i < strings.length; i++) {
			flexTable.setText(row, i, strings[i]);
		}
	}
	public static void populateFlexTableRow(FlexTable flexTable, int row, int startColumn, Widget...widgets) {
		for (int i = 0; i < widgets.length; i++) {
			flexTable.setWidget(row, i, widgets[i]);
		}
	}
	public static void setStyleName(String style, Widget...widgets) {
		for (Widget widget : widgets) {
			widget.setStyleName(style);
		}
	}
	public static void setSpacing(int spacing, CellPanel...widgets) {
		for (CellPanel widget : widgets) {
			widget.setSpacing(spacing);
		}
	}
	public static Image newImage(String filePath, int width) {
		Image image = new Image(filePath);
		image.setWidth(width + "px");
		return image;
	}
	public static Label newLabel(String text, String style) {
		Label label = new Label(text);
		label.setStyleName(style);
		return label;
	}
}
