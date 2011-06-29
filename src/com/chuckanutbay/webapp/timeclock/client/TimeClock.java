package com.chuckanutbay.webapp.timeclock.client;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.NumberFormat;
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
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.*;

public class TimeClock implements EntryPoint, ScanInOutHandler, ClockInOutErrorHandler, ClockInOutServerCommunicator, KeyDownHandler {
	
	//UI Components
	private FocusPanel mainPanel = new FocusPanel();
	private HorizontalPanel backgroundPanel = new HorizontalPanel();
	private VerticalPanel column1Panel = new VerticalPanel();
	private SimplePanel column2Panel = new SimplePanel();
	private FlexTable employeeTable1 = new FlexTable();
	private SimplePanel column3Panel = new SimplePanel();
	private FlexTable employeeTable2 = new FlexTable();
	private RootPanel rootPanel = RootPanel.get("TimeClock");
	
	//Data Objects
	private Set<EmployeeDto> clockedInEmployees = new HashSet<EmployeeDto>();
	private Set<ActivityDto> activities = new HashSet<ActivityDto>();
	
	//Other Objects
	BarcodeFormulation barcodeFormulation = new BarcodeFormulation();
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
		getClcockedInEmployeesFromDatabase();
		getActivitiesFromDatabase();
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
		Label nameLabel = new Label(employee.getFirstName() + " " + employee.getLastName());
		nameLabel.setStyleName("employeeFlexTableNameColumn");
		flexTable.setWidget(row-1, 0, nameLabel);
		
		//Set second column as Time worked this week in format (hrs:mins)
		NumberFormat numberFormat = NumberFormat.getFormat("00");
		final int hoursWorkedThisWeek = employee.minsWorkedThisWeek / 60;
		final int remainderInMinutes = employee.minsWorkedThisWeek - (60*hoursWorkedThisWeek);
		Label timeLabel = new Label("(" + numberFormat.format(hoursWorkedThisWeek) + ":" + numberFormat.format(remainderInMinutes) + ")");
		timeLabel.setStyleName("employeeFlexTableTimeColumn");
		flexTable.setWidget(row-1, 1, timeLabel);
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
		mainPanel.addKeyDownHandler(this);
		mainPanel.setFocus(true);
		
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

	@Override
	public void onScan(BarcodeDto barcode) {
		GWT.log("The following barcode just got scanned: " + barcode.getBarcodeNumber());
		for (EmployeeDto employee : clockedInEmployees) {
			GWT.log("Checking if the scanned barcode (" + barcode.getBarcodeNumber() + ") matches " + employee.getFirstName() + " " + employee.getLastName() + "'s barcode (" + barcode.getBarcodeNumber() + ")");
			if (employee.getBarcodeNumber().equals(barcode)) {
				GWT.log("Found that " + employee.getFirstName() + " " + employee.getLastName() + "'s barcode matches the scanned code");
				onClockOutScan(barcode);
				return;
			}
		}
		GWT.log("Didn't find any matching barcodes to: " + barcode.getBarcodeNumber());
		onClockInScan(barcode);
	}

	@Override
	public void onClockInScan(BarcodeDto barcode) {
		clockInOnDatabase(barcode);
		
	}

	@Override
	public void onClockOutScan(BarcodeDto barcode) {
		for (EmployeeDto employee : clockedInEmployees) {
			if (employee.getBarcodeNumber().equals(barcode)) {
				clockOutOnDatabase(employee);
				clockedInEmployees.remove(employee);
				updateEmployeeTables();
				return;
			}
		}
	}

	@Override
	public void onClockInError(BarcodeDto barcode) {
		GWT.log("Clock in error with barcode: " + barcode.getBarcodeNumber().toString());
		cancelClockInOnDatabase(barcode);
		Iterator<EmployeeDto> iterator = clockedInEmployees.iterator();
		while (iterator.hasNext()) {
			EmployeeDto employeeToCheck = iterator.next();
			if (employeeToCheck.getBarcodeNumber().equals(barcode)) {
				GWT.log("Removing " + employeeToCheck.firstName + " " + employeeToCheck.lastName + " from clocked-in employees");
				clockedInEmployees.remove(employeeToCheck);
				updateEmployeeTables();
				break;
			}
		}
		
	}

	@Override
	public void onClockOutError(EmployeeDto employee) {
		//Add the employee back to the employee tables.
		clockedInEmployees.add(employee);
		updateEmployeeTables();
	}

	@Override
	public void getClcockedInEmployeesFromDatabase() {
		GWT.log("Requesting clocked in employees from server");
		employeeClockInOutService.getClockedInEmployees(createGetClockedInEmployeesCallback(this));
	}

	@Override
	public void clockInOnDatabase(BarcodeDto barcode) {
		GWT.log("Requesting that the server clock-in employee with barcode number: " + barcode.barcodeNumber);
		employeeClockInOutService.clockIn(barcode, createClockInCallback(this));
	}

	@Override
	public void clockOutOnDatabase(EmployeeDto employee) {
		GWT.log("Requesting that the server clock-out employee: " + employee.firstName + " " + employee.lastName);
		employeeClockInOutService.clockOut(employee, createClockOutCallback(this));
	}

	@Override
	public void getActivitiesFromDatabase() {
		GWT.log("Requesting activities from server");
		employeeClockInOutService.getActivities(createGetActivitiesCallback(this));
	}

	@Override
	public void cancelClockInOnDatabase(BarcodeDto barcode) {
		GWT.log("Requesting cancellation of clock in from Server for barcode number: " + barcode.barcodeNumber);
		employeeClockInOutService.cancelClockIn(barcode, createCancelClockInCallback(this));
	}

	@Override
	public void onSuccessfulClockIn(EmployeeDto employee) {
		clockedInEmployees.add(employee);
		updateEmployeeTables();
		GWT.log("Successful Clock In from Server");
	}

	@Override
	public void onSuccessfulClockOut() {
		// Do nothing
		GWT.log("Successful Clock Out from Server");
	}

	@Override
	public void onSuccessfulCancelClockIn() {
		// Do nothing
		GWT.log("Successful Clock In cancellation from Server");
	}

	@Override
	public void onSuccessfulGetClockedInEmployees(
			Set<EmployeeDto> clockedInEmployees) {
		this.clockedInEmployees.clear();
		this.clockedInEmployees.addAll(clockedInEmployees);
		updateEmployeeTables();
		GWT.log("Successfully got clocked-in employees from Server");
	}

	@Override
	public void onSuccessfulGetActivities(Set<ActivityDto> activities) {
		this.activities.clear();
		this.activities.addAll(activities);
		GWT.log("Successfully got activities from Server");
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		GWT.log("Key entered: " + event.getNativeKeyCode());
		final int keyCode = event.getNativeKeyCode();
		if (keyCode >= ZERO_KEY_CODE && keyCode <= NINE_KEY_CODE) {
			barcodeFormulation.addCharacter(new Integer(keyCode).byteValue());
		} else if (keyCode == ENTER_KEY_CODE) {
			onScan(barcodeFormulation.getBarcode());
			barcodeFormulation = new BarcodeFormulation();
		}
	}

}
