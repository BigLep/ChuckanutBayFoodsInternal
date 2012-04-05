package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.DayReportData;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalDto;
import com.chuckanutbay.webapp.common.shared.WeekReportData;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class TimeClockReportFlexTable extends FlexTable {
	private WeekReportData weekIntervals;
	private String[] headerLabels = {"Date", "In", "Out", "Interval", "Normal", "OT"};
	private int row = 0;
	
	//Date + Number Formats
	//mediumDate - Sep 13, 1999
	private DateTimeFormat mediumDate = DateTimeFormat.getFormat("MMM d, y");
	//shortTime - 12:34 PM
	private DateTimeFormat shortTime = DateTimeFormat.getFormat("h:mm a");
	//hourFormat
	private NumberFormat hourFormat = NumberFormat.getFormat("0.00h");
	
	public TimeClockReportFlexTable(double normalHours, double overTimeHours) {
		int column = 0;
		
		while (column < 6) {
			this.setText(row, column, "");
			column++;
		}
		
		row++;
		column = 0;
		
		this.getRowFormatter().addStyleName(row, "timeClockReportFlexTablePayPeriodTotals");
		
		this.setText(row, column, "Pay Period Total:");
		this.getCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_LEFT);
		this.getFlexCellFormatter().setColSpan(row, column, 4);
		column++;
		
		this.setText(row, column, hourFormat.format(normalHours));
		this.getCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_RIGHT);
		column++;
		
		this.setText(row, column, hourFormat.format(overTimeHours));
		this.getCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_RIGHT);
		column++;
		
		setupStyles();
	}
	
	public TimeClockReportFlexTable(WeekReportData weekIntervals) {
		this.weekIntervals = weekIntervals;
		generateHeader();
		populate();
		generateFooter();
		setupStyles();
	}

	private void setupStyles() {
		this.setStyleName("timeClockReportFlexTable");
		this.setCellPadding(2);
		this.setCellSpacing(1);
		for (int column = 0; column < 6; column++) {
			this.getColumnFormatter().setWidth(column, "133px");
		}
	}
	private void generateFooter() {
		int column = 0;
		this.getRowFormatter().addStyleName(row, "timeClockReportFlexTableFooterRow");
		
		this.setText(row, column, "Week Total:");
		this.getCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_LEFT);
		this.getFlexCellFormatter().setColSpan(row, column, 4);
		column++;
		
		this.setText(row, column, hourFormat.format(weekIntervals.getHoursNormalPay()));
		this.getCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_RIGHT);
		column++;
		
		this.setText(row, column, hourFormat.format(weekIntervals.getHoursOvertime()));
		this.getCellFormatter().setHorizontalAlignment(row, column, HasHorizontalAlignment.ALIGN_RIGHT);
		column++;
		
		row++;
	}

	private void populate() {
		int column;
		
		if (weekIntervals.getDayReportData() != null) {
			for (DayReportData dayIntervals : weekIntervals.getDayReportData()) {
				if (dayIntervals.getEmployeeWorkIntervals() != null) {
					for (EmployeeWorkIntervalDto interval : dayIntervals.getEmployeeWorkIntervals()) {
						column = 0;
						this.setText(row, column, mediumDate.format(interval.getStartDateTime()));
						column++;
						this.setText(row, column, shortTime.format(interval.getStartDateTime()));
						column++;
						this.setText(row, column, shortTime.format(interval.getEndDateTime()));
						column++;
						this.setText(row, column, hourFormat.format(interval.getHoursWorked()));
						column++;
						this.setText(row, column, "");
						column++;
						this.setText(row, column, "");
						column++;
						this.getRowFormatter().addStyleName(row, "timeClockReportFlexTableDataRow");
						row++;
						String comment = interval.getComment();
						if (comment != null && !comment.equals("")) {
							this.getFlexCellFormatter().setColSpan(row, 0, 6);
							this.setText(row, 0, comment);
							row++;
						}
					}
					row--;
					int extraRowsMoved = 0;
					while (this.getFlexCellFormatter().getColSpan(row, 0) > 1) {
						row--;
						extraRowsMoved++;
						
					}
					this.setText(row, 4, hourFormat.format(dayIntervals.getTotalHoursWorked()));
					row++;
					row = row + extraRowsMoved;
				}
			}
		}
	}

	private void generateHeader() {
		int column = 0;
		for (String label : headerLabels) {
			this.setText(row, column, label);
			column++;
		}
		this.getRowFormatter().addStyleName(row, "timeClockReportFlexTableHeaderRow");
		row++;
	}
}
