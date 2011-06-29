package com.chuckanutbay.webapp.timeclock.client;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import static com.chuckanutbay.webapp.common.shared.IconUtil.*;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.*;

public class TimeClock implements EntryPoint {
	
	//UI Components
	FocusPanel mainPanel = new FocusPanel();
	HorizontalPanel backgroundPanel = new HorizontalPanel();
	VerticalPanel column1Panel = new VerticalPanel();
	SimplePanel column2Panel = new SimplePanel();
	FlexTable employeeTable1 = new FlexTable();
	SimplePanel column3Panel = new SimplePanel();
	FlexTable employeeTable2 = new FlexTable();
	RootPanel rootPanel = RootPanel.get("TimeClock");
	
	//Data Objects
	Set<EmployeeDto> clockedInEmployees;
	Set<ActivityDto> activities;
	
	//Other Objects
	private Timer timer = new Timer() {
		@Override
		public void run() {
			for(EmployeeDto employee : clockedInEmployees) {
				addOneMinuteWorked(employee);
			}
			updateEmployeeTables();
		}
	};
	
	
	
	@Override
	public void onModuleLoad() {
		initializeUI();
	}
	
	/**
	 * Clears the employee tables and re-adds every clocked-in employee
	 */
	private void updateEmployeeTables() {
		//Clear the tables.
		employeeTable1.clear();
		employeeTable2.clear();
		
		//Iterate over the set of employees adding the first 20 to the first table
		//and up to 20 more on the next table.
		Iterator<EmployeeDto> iterator = clockedInEmployees.iterator();
		for(int i=0; i<20 && iterator.hasNext(); i++) {
			addToFlexTable(employeeTable1, i+1, iterator.next());
		}
		for(int i=0; i<20 && iterator.hasNext(); i++) {
			addToFlexTable(employeeTable2, i+1, iterator.next());
		}
		
	}
	
	/**
	 * Adds an {@link EmployeeDto} to the specified table in the specified row. Formats the name to "First Last" and the time worked this week to "(hrs:mins)"
	 * @param flexTable The table to add the {@link EmployeeDto} to.
	 * @param row Which row of the table to add the {@link EmployeeDto} to. The first row is row 1.
	 * @param employee The {@link EmployeeDto} to add to the table.
	 */
	private void addToFlexTable(FlexTable flexTable, int row, EmployeeDto employee) {
		//Set first column as Employee Name
		flexTable.setWidget(row-1, 0, new Label(employee.getFirstName() + " " + employee.getLastName()));
		
		//Set second column as Time worked this week in format (hrs:mins)
		final int hoursWorkedThisWeek = employee.minsWorkedThisWeek % 60;
		final int remainderInMinutes = employee.minsWorkedThisWeek - (60*hoursWorkedThisWeek);
		flexTable.setWidget(row-1, 1, new Label("(" + hoursWorkedThisWeek + ":" + remainderInMinutes + ")"));
	}

	/**
	 * Initializes and displays the User Interface components.
	 */
	private void initializeUI() {
		GWT.log("Starting UI Initialization");
		
		//Setup column1Panel
		Image image = new Image(WHITE_LOGO);
		image.setWidth("400px");
		column1Panel.add(image);
		column1Panel.add(new Clock(new Date(), true, 400, 100));
		
		//Setup column2Panel and column3Panel
		column2Panel.setPixelSize(376, 660);
		column2Panel.setStyleName("columnPanel");
			VerticalPanel verticalPanel1 = new VerticalPanel();
			verticalPanel1.setWidth("376px");
				SimplePanel heading = new SimplePanel();
				heading.add(new Label("Clocked-In"));
				heading.setStyleName("employeeFlexTableHeader");
			verticalPanel1.add(heading);
				SimplePanel flexTablePanel1 = new SimplePanel();
				flexTablePanel1.setStyleName("employeeFlexTable");
				flexTablePanel1.add(employeeTable1);
					employeeTable1.getColumnFormatter().addStyleName(0, "employeeFlexTableNameColumn");
					employeeTable1.getColumnFormatter().addStyleName(1, "employeeFlexTableTimeColumn");
			verticalPanel1.add(flexTablePanel1);
		column2Panel.add(verticalPanel1);
		
		column3Panel.setPixelSize(376, 660);
		column3Panel.setStyleName("columnPanel");
			VerticalPanel verticalPanel2 = new VerticalPanel();
			verticalPanel2.setWidth("376px");
				SimplePanel heading2 = new SimplePanel();
				heading2.add(new Label("Clocked-In"));
				heading2.setStyleName("employeeFlexTableHeader");
			verticalPanel2.add(heading2);
				SimplePanel flexTablePanel2 = new SimplePanel();
				flexTablePanel2.setStyleName("employeeFlexTable");
				flexTablePanel2.add(employeeTable2);
					employeeTable2.getColumnFormatter().addStyleName(0, "employeeFlexTableNameColumn");
					employeeTable2.getColumnFormatter().addStyleName(1, "employeeFlexTableTimeColumn");
			verticalPanel2.add(flexTablePanel2);
		column3Panel.add(verticalPanel2);
		
		//Setup columnLayoutPanel
		backgroundPanel.setStyleName("backgroundPanel");
		backgroundPanel.setPixelSize(1280, 740);
		backgroundPanel.add(column1Panel);
		backgroundPanel.add(column2Panel);
		backgroundPanel.add(column3Panel);
		
		//Setup mainPanel
		mainPanel.add(backgroundPanel);
		
		//Setup Window
		Window.enableScrolling(false);
		
		//Setup rootPanel
		rootPanel.add(mainPanel);
		GWT.log("Finnished UI Initialization");
		
		//Setup Timer
		timer.scheduleRepeating(MIN_IN_MILLISECONDS);
	}
	
	/**
	 * Increases the time worked by and employee in the current week by 1 minute
	 * @param employee The employee to increase the time of.
	 */
	private static void addOneMinuteWorked(EmployeeDto employee) {
		int adjustedNumberOfMins = employee.getMinsWorkedThisWeek() + 1;
		employee.setMinsWorkedThisWeek(adjustedNumberOfMins);
	}
	
	/**
	 * Checks if any of the {@link clockedInEmployees} have a matching barcode number.
	 * @param barcode {@link BarcodeDto} to check for equivalency with.
	 * @return Returns true if there is an employee with a matching barcode number.
	 */
	private boolean employeeIsClockedIn(BarcodeDto barcode) {
		for (EmployeeDto employee : clockedInEmployees) {
			if (employee.barcodeNumber.equals(barcode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if any of the {@link clockedInEmployees} are matching based on barcode number.
	 * @param barcode {@link BarcodeDto} to check for equivalency with.
	 * @return Returns true if there is an employee with a matching barcode number.
	 */
	private boolean employeeIsClockedIn(EmployeeDto employee) {
		for (EmployeeDto employeeToCheck : clockedInEmployees) {
			if (employeeToCheck.barcodeNumber.equals(employee)) {
				return true;
			}
		}
		return false;
	}

}
