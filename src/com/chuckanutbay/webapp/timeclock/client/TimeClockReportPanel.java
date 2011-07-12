package com.chuckanutbay.webapp.timeclock.client;



import com.chuckanutbay.webapp.common.client.IconUtil;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.chuckanutbay.webapp.common.shared.WeekReportData;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TimeClockReportPanel extends SimplePanel {
	public PayPeriodReportData reportData;
	public SimplePanel headerPanel = new SimplePanel();
	public VerticalPanel mainPanel = new VerticalPanel();
	
	//Date Formats
	//shortDate - 9/13/99
	private DateTimeFormat shortDate = DateTimeFormat.getFormat("M/d/yy");

	public TimeClockReportPanel(PayPeriodReportData reportData) {
		this.reportData = reportData;
		this.setStyleName("timeClockReportPanel");
		generateHeader();
		gernerateFlexTables();
		gernerateFooter();
		this.add(mainPanel);
	}

	private void gernerateFooter() {
		FlexTable signaturesFlexTable = new FlexTable();
		signaturesFlexTable.setWidth("799px");
		signaturesFlexTable.setStyleName("signaturesFlexTable");
		signaturesFlexTable.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_BOTTOM);
		signaturesFlexTable.getRowFormatter().setVerticalAlign(1, HasVerticalAlignment.ALIGN_TOP);
		signaturesFlexTable.setText(0, 0, "_____________________________");
		signaturesFlexTable.setText(0, 1, "_____________________________");
		signaturesFlexTable.setText(1, 0, "Supervisor");
		signaturesFlexTable.setText(1, 1, "Employee");
		signaturesFlexTable.setHeight("100px");
		mainPanel.add(signaturesFlexTable);
	}

	private void gernerateFlexTables() {
		for (WeekReportData weekIntervalsDto : reportData.getWeekReportData()) {
			if (weekIntervalsDto.getHoursNormalPay() > 0) {
				TimeClockReportFlexTable flexTable = new TimeClockReportFlexTable(weekIntervalsDto);
				mainPanel.add(flexTable);
			}
		}
		TimeClockReportFlexTable payPeriodTotalsFlexTable = new TimeClockReportFlexTable(reportData.getHoursNormalPay(), reportData.getHoursOvertime());
		mainPanel.add(payPeriodTotalsFlexTable);
	}

	private void generateHeader() {
		VerticalPanel textPanel = new VerticalPanel();
		textPanel.setSpacing(5);
		createLabelAndAddToPanel("Time Clock Report", "Title", textPanel);
		createLabelAndAddToPanel(reportData.getName(), "Name", textPanel);
		createLabelAndAddToPanel("Date: " + shortDate.format(reportData.getDate()), "Date", textPanel);
		String payPeriodString = "Pay Period: " + shortDate.format(reportData.getPayPeriodStart()) + "-" + shortDate.format(reportData.getPayPeriodEnd());
		createLabelAndAddToPanel(payPeriodString, "PayPeriod", textPanel);
		
		Image logo = new Image(IconUtil.LOGO);
		logo.setWidth("300px");
		headerPanel.setStyleName("timeClockReportPanelHeader");
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setWidth("799px");
		hPanel.add(textPanel);
		hPanel.setCellHorizontalAlignment(textPanel, HasHorizontalAlignment.ALIGN_LEFT);
		hPanel.add(logo);
		hPanel.setCellHorizontalAlignment(logo, HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(hPanel);
		
		mainPanel.add(headerPanel);
	}

	private void createLabelAndAddToPanel(String labelText, String style,
			VerticalPanel panel) {
		Label label = new Label(labelText);
		label.setStyleName("timeClockReportPanel" + style + "Label");
		panel.add(label);
	}
}
