package com.chuckanutbay.webapp.common.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;

public class CbFlexTable extends FlexTable {
	private int row = 0;
	private int column = 0;
	
	public CbFlexTable() {
		super();
	}
	
	public CbFlexTable setColumnStyle(int column, String style) {
		super.getColumnFormatter().setStyleName(column, style);
		return this;
	}
	
	public CbFlexTable setRowStyle(int row, String style) {
		super.getRowFormatter().setStyleName(row, style);
		return this;
	}
	
	public CbFlexTable setColumnStyle(String style) {
		super.getColumnFormatter().setStyleName(column, style);
		return this;
	}
	
	public CbFlexTable setRowStyle(String style) {
		super.getRowFormatter().setStyleName(row, style);
		return this;
	}
	
	public CbFlexTable setText(String text, int row, int column) {
		super.setText(row, column, text);
		return this;
	}
	
	public CbFlexTable setWidget(Widget widget, int row, int column) {
		super.setWidget(row, column, widget);
		return this;
	}
	
	public CbFlexTable setCellStyle(String style, int row, int column) {
		super.getCellFormatter().addStyleName(row, column, style);
		return this;
	}
	
	public CbFlexTable setHorizontalAlignement(int row, int column, HorizontalAlignmentConstant align) {
		super.getCellFormatter().setHorizontalAlignment(row, column, align);
		return this;
	}
	
	/* This was a better idea in my head I think!
	public CbFlexTable setText(String text)	{
		super.setText(row, column, text);
		return this;
	}
	
	public CbFlexTable setText(Widget widget)	{
		super.setWidget(row, column, widget);
		return this;
	}
	

	public CbFlexTable toFristRow() {
		row = 0;
		return this;
	}
	
	public CbFlexTable toFirstColumn() {
		column = 0;
		return this;
	}
	
	public CbFlexTable nextRow() {
		row++;
		return this;
	}
	
	public CbFlexTable nextColumn() {
		column++;
		return this;
	}
	*/
	
}
