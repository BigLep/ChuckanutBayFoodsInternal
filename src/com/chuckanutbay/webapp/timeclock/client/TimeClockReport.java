package com.chuckanutbay.webapp.timeclock.client;

import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createGetEndOfLastPayPeriodCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createGetStartOfLastPayPeriodCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.employeeClockInOutService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.client.IconUtil;
import com.chuckanutbay.webapp.common.shared.EmployeePayPeriodReportDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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
	
	private final List<EmployeePayPeriodReportDto> employeePayPeriodReportDtos = new ArrayList<EmployeePayPeriodReportDto>();
	
	//Date Formats
	//shortDayOfMonth - Mon, Sep 13
	private DateTimeFormat shortDayOfMonth = DateTimeFormat.getFormat("EEE, MMM d");
	
	@Override
	public void onModuleLoad() {
		
		
		EmployeePayPeriodReportDto employeePayPeriodReportDto = new EmployeePayPeriodReportDto();
		employeePayPeriodReportDto.setDate(new Date());
		employeePayPeriodReportDto.setPayPeriodStart(new Date());
		employeePayPeriodReportDto.setPayPeriodEnd(new Date());
		employeePayPeriodReportDto.setName("Steve Jobs");
		employeePayPeriodReportDtos.add(employeePayPeriodReportDto);
		
		
		
		
		
		
		
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
		getStartOfLastPayPeriodFromServer();
		getEndOfLastPayPeriodFromServer();
		
		//Setup shiftListBox
		shiftListBoxLabel.setStyleName("widgetLabel");
		shiftListBox.addItem("All");
		shiftListBox.addItem("1");
		shiftListBox.addItem("2");
		shiftListBox.addItem("3");
		
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
				rootPanel.remove(mainPanel);
				//generateReports();
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

	@Override
	public void getStartOfLastPayPeriodFromServer() {
		GWT.log("Requesting Start of Pay Period");
		employeeClockInOutService.getStartOfLastPayPeriodFromServer(createGetStartOfLastPayPeriodCallback(this));
	}

	@Override
	public void getEndOfLastPayPeriodFromServer() {
		GWT.log("Requesting End of Pay Period");
		employeeClockInOutService.getEndOfLastPayPeriodFromServer(createGetEndOfLastPayPeriodCallback(this));
	}

	@Override
	public void onSuccessfulGetStartOfLastPayPeriod(Date date) {
		GWT.log("Got Start of Pay Period");
		startDateBox.setValue(date);
	}

	@Override
	public void onSuccessfulGetEndOfLastPayPeriod(Date date) {
		GWT.log("Got End of Pay Period");
		endDateBox.setValue(date);
	}
	/**
	private void generateReports() {
		for (EmployeePayPeriodReportDto employeeReport : employeePayPeriodReportDtos) {
			TimeClockReportPanel panel = new TimeClockReportPanel(employeeReport);
		}
	}
	*/
}
