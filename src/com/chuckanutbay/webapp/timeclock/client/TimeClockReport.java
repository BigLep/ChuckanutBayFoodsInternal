package com.chuckanutbay.webapp.timeclock.client;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createGetLastPayPeriodIntervalCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createGetPayPeriodReportDataCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.timeClockService;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.client.IconUtil;
import com.chuckanutbay.webapp.common.shared.DayReportData;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalDto;
import com.chuckanutbay.webapp.common.shared.IntervalDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.chuckanutbay.webapp.common.shared.WeekReportData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class TimeClockReport implements EntryPoint, TimeClockReportHandler {
	
	//UI components
	private RootPanel rootPanel;
	private final VerticalPanel mainPanel = new VerticalPanel();
	private final Image logo = new Image(IconUtil.LOGO);
	private final Label header = new Label("Pay Period Report");
	private final HorizontalPanel dateBoxAndShiftListBoxPanel = new HorizontalPanel();
	private final Label startDateBoxLabel = new Label("Start Date:");
	private final DateBox startDateBox = new DateBox();
	private final Label endDateBoxLabel = new Label("End Date:");
	private final DateBox endDateBox = new DateBox();
	private final HorizontalPanel gernateButtonPanel = new HorizontalPanel();
	private final Label shiftListBoxLabel = new Label("Shift:");
	private final ListBox shiftListBox = new ListBox();
	private final Button generateButton = new Button("Generate");
	
	private List<PayPeriodReportData> payPeriodReportData;
	
	//Date Formats
	//shortDayOfMonth - Mon, Sep 13
	private DateTimeFormat shortDayOfMonth = DateTimeFormat.getFormat("EEE, MMM d");
	
	@Override
	public void onModuleLoad() {
		
		//generateSampleData();
		
		//Setup logo
		logo.setWidth("300px");
		
		//Setup header
		header.setStyleName("header");
		
		//Setup dateBoxes
		startDateBoxLabel.setStyleName("widgetLabel");
		endDateBoxLabel.setStyleName("widgetLabel");
		startDateBox.setFormat(new DateBox.DefaultFormat(shortDayOfMonth));
		endDateBox.setFormat(new DateBox.DefaultFormat(shortDayOfMonth));
		startDateBox.setWidth("75px");
		endDateBox.setWidth("75px");
		getLastPayPeriodIntervalFromServer();
		
		//Setup shiftListBox
		shiftListBoxLabel.setStyleName("widgetLabel");
		shiftListBox.addItem("All");
		shiftListBox.addItem("1");
		shiftListBox.addItem("2");
		shiftListBox.addItem("3");
		shiftListBox.addItem("Other");
		
		//Setup dateBoxAndShiftListBoxPanel
		dateBoxAndShiftListBoxPanel.setWidth("550px");
		dateBoxAndShiftListBoxPanel.setSpacing(5);
		dateBoxAndShiftListBoxPanel.setStyleName("dateBoxPanel");
		dateBoxAndShiftListBoxPanel.add(startDateBoxLabel);
		dateBoxAndShiftListBoxPanel.setCellHorizontalAlignment(startDateBoxLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		dateBoxAndShiftListBoxPanel.add(startDateBox);
		dateBoxAndShiftListBoxPanel.setCellHorizontalAlignment(startDateBox, HasHorizontalAlignment.ALIGN_LEFT);
		dateBoxAndShiftListBoxPanel.add(endDateBoxLabel);
		dateBoxAndShiftListBoxPanel.setCellHorizontalAlignment(endDateBoxLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		dateBoxAndShiftListBoxPanel.add(endDateBox);
		dateBoxAndShiftListBoxPanel.setCellHorizontalAlignment(endDateBox, HasHorizontalAlignment.ALIGN_LEFT);
		dateBoxAndShiftListBoxPanel.add(shiftListBoxLabel);
		dateBoxAndShiftListBoxPanel.setCellHorizontalAlignment(shiftListBoxLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		dateBoxAndShiftListBoxPanel.add(shiftListBox);
		dateBoxAndShiftListBoxPanel.setCellHorizontalAlignment(shiftListBox, HasHorizontalAlignment.ALIGN_LEFT);
		
		
		
		//Setup gernateButtonPanel
		generateButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
				int shift;
				if (startDateBox.getValue() == null) {
					Window.alert("Please enter a start date.");
				} else if (endDateBox.getValue() == null) {
					Window.alert("Please enter an end date.");
				} else {
					if (shiftListBox.getItemText(shiftListBox.getSelectedIndex()).equals("All")){
						shift = 0;
					} else if (shiftListBox.getItemText(shiftListBox.getSelectedIndex()).equals("Other")){
						shift = 4;
					} else {
						shift = Integer.parseInt(shiftListBox.getItemText(shiftListBox.getSelectedIndex()));
					}
					getPayPeriodReportData(startDateBox.getValue(), endDateBox.getValue(), shift);
				}
			}
		});
		gernateButtonPanel.setWidth("550px");
		gernateButtonPanel.setSpacing(5);
		gernateButtonPanel.add(generateButton);
		gernateButtonPanel.setCellHorizontalAlignment(generateButton, HasHorizontalAlignment.ALIGN_CENTER);

		
		//Setup mainPanel
		mainPanel.setSpacing(5);
		mainPanel.add(logo);
		mainPanel.add(header);
		mainPanel.setCellHorizontalAlignment(header, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.add(dateBoxAndShiftListBoxPanel);
		mainPanel.add(gernateButtonPanel);
		
		//Setup rootPanel
		rootPanel = RootPanel.get("TimeClockReport");
		rootPanel.add(mainPanel);
	}

	@SuppressWarnings("unused")
	private void generateSampleData() {
		EmployeeWorkIntervalDto interval1 = new EmployeeWorkIntervalDto();
		interval1.setStartDateTime(new Date());
		interval1.setEndDateTime(new Date());
		interval1.setHoursWorked(4.42);
		
		EmployeeWorkIntervalDto interval2 = new EmployeeWorkIntervalDto();
		interval2.setStartDateTime(new Date());
		interval2.setEndDateTime(new Date());
		interval2.setHoursWorked(4.40);
		
		List<EmployeeWorkIntervalDto> intervals1 = newArrayList(interval1, interval2);
		List<EmployeeWorkIntervalDto> intervals2 = newArrayList(interval2, interval1);
		
		DayReportData dayReportData1 = new DayReportData();
		dayReportData1.setTotalHoursWorked(8.42);
		dayReportData1.setEmployeeWorkIntervals(intervals1);
		
		DayReportData dayReportData2 = new DayReportData();
		dayReportData2.setTotalHoursWorked(7.42);
		dayReportData2.setEmployeeWorkIntervals(intervals2);
		
		List<DayReportData> DayIntervalsDtos = newArrayList(dayReportData1, dayReportData2);
		
		WeekReportData weekReportData1 = new WeekReportData();
		weekReportData1.setHoursNormalPay(22.3643);
		weekReportData1.setHoursOvertime(0);
		weekReportData1.setDayReportData(DayIntervalsDtos);
		
		WeekReportData weekReportData2 = new WeekReportData();
		weekReportData2.setHoursNormalPay(40.00);
		weekReportData2.setHoursOvertime(6.32);
		weekReportData2.setDayReportData(DayIntervalsDtos);
		
		PayPeriodReportData payPeriodReportData1 = new PayPeriodReportData();
		payPeriodReportData1.setDate(new Date());
		payPeriodReportData1.setPayPeriodStart(new Date());
		payPeriodReportData1.setPayPeriodEnd(new Date());
		payPeriodReportData1.setName("Steve Jobs");
		payPeriodReportData1.setWeekReportData(newArrayList(weekReportData1, weekReportData2));
		payPeriodReportData = newArrayList(payPeriodReportData1);
	}

	@Override
	public void getLastPayPeriodIntervalFromServer() {
		GWT.log("Requesting Start of Pay Period");
		timeClockService.getLastPayPeriodIntervalFromServer(new Date(), createGetLastPayPeriodIntervalCallback(this));
	}

	@Override
	public void onSuccessfulGetLastPayPeriodInterval(IntervalDto interval) {
		GWT.log("Got Pay Period Interval");
		startDateBox.setValue(interval.getStart());
		endDateBox.setValue(interval.getEnd());
	}
	
	@Override
	public void getPayPeriodReportData(Date start, Date end, Integer shift) {
		timeClockService.getPayPeriodReportDataFromDatabase(start, end, shift, createGetPayPeriodReportDataCallback(this)); 
		
	}

	@Override
	public void onSuccessfulGetPayPeriodReportData(
			List<PayPeriodReportData> reportData) {
		payPeriodReportData = reportData;
		generateReports();
	}

	private void generateReports() {
		for (PayPeriodReportData employeeReport : payPeriodReportData) {
			TimeClockReportPanel panel = new TimeClockReportPanel(employeeReport);
			mainPanel.add(panel);
		}
	}
}
