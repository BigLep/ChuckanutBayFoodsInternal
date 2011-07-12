package com.chuckanutbay.webapp.timeclock.client;


import static com.chuckanutbay.webapp.common.client.IconUtil.WHITE_LOGO;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createCancelClockInCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createClockInCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createClockOutCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createGetActivitiesCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.createGetClockedInEmployeesCallback;
import static com.chuckanutbay.webapp.timeclock.client.RpcHelper.timeClockService;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.ENTER_KEY_CODE;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.MIN_IN_MILLISECONDS;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.NINE_KEY_CODE;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.TEN_SECONDS_IN_MILLISECONDS;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.ZERO_KEY_CODE;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.Barcode;
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
	private SimplePanel confirmationPanelContainer = new SimplePanel();
	private ClockInConfirmationPanel clockInConfirmationPanel;
	private ClockOutDialogBox clockOutDialogBox;
	
	//Data Objects
	private SortedSet<EmployeeDto> clockedInEmployees = new TreeSet<EmployeeDto>();
	private SortedSet<ActivityDto> activities = new TreeSet<ActivityDto>();
	
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
	private Timer employeeSignInConfirmationTimer = new Timer() {
		@Override
		public void run() {
			confirmationPanelContainer.clear();
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
		for(int i=0; i<17 && iterator.hasNext(); i++) {
			addToFlexTable(employeeTable1, i+1, iterator.next());
		}
		for(int i=0; i<17 && iterator.hasNext(); i++) {
			addToFlexTable(employeeTable2, i+1, iterator.next());
		}

		mainPanel.setFocus(true);
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
		GWT.log("Mins Worked This Week: " + employee.minsWorkedThisWeek);
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
		column1Panel.add(confirmationPanelContainer);
		column1Panel.setSpacing(20);
		
		//Setup column2Panel and column3Panel
		column2Panel.setPixelSize(350, 660);
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
		
		column3Panel.setPixelSize(350, 660);
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
	 * @param barcode {@link Barcode} to check for equivalency with.
	 * @return Returns true if there is an employee with a matching barcode number.
	 */
	public boolean employeeIsClockedIn(Barcode barcode) {
		if (findMatchingClockedInEmployee(barcode) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if any of the {@link clockedInEmployees} are matching based on barcode number.
	 * @param barcode {@link Barcode} to check for equivalency with.
	 * @return Returns true if there is an employee with a matching barcode number.
	 */
	public boolean employeeIsClockedIn(EmployeeDto employee) {
		return employeeIsClockedIn(employee.getBarcodeNumber());
	}
	
	/**
	 * @param barcode The {@link Barcode} to check for a match with.
	 * @return The matching {@link EmployeeDto}, or null if no matches.
	 */
	private EmployeeDto findMatchingClockedInEmployee(Barcode barcode) {
		for (EmployeeDto employeeToCheck : clockedInEmployees) {
			GWT.log("Checking if the scanned barcode (" + barcode.getBarcodeNumber() + ") matches " + employeeToCheck.getFirstName() + " " + employeeToCheck.getLastName() + "'s barcode (" + employeeToCheck.getBarcodeNumber().getBarcodeNumber() + ")");
			if (employeeToCheck.getBarcodeNumber().equals(barcode)) {
				GWT.log("Found that " + employeeToCheck.getFirstName() + " " + employeeToCheck.getLastName() + "'s barcode matches the scanned code");
				return employeeToCheck;
			}
		}
		return null;
	}
	

	@Override
	public void onScan(Barcode barcode) {
		GWT.log("The following barcode just got scanned: " + barcode.getBarcodeNumber());
		if (employeeIsClockedIn(barcode)) {
			onClockOutScan(findMatchingClockedInEmployee(barcode));
		} else {
			onClockInScan(barcode);
		}
	}

	@Override
	public void onClockInScan(Barcode barcode) {
		clockInOnDatabase(barcode);
	}

	@Override
	public void onClockOutScan(EmployeeDto employee) {
		confirmationPanelContainer.clear();
		clockOutDialogBox = new ClockOutDialogBox(employee, activities, this, this, 40, 310, 408, 360);
		clockOutDialogBox.show();
		clockedInEmployees.remove(employee);
		updateEmployeeTables();
	}

	@Override
	public void onClockInError(Barcode barcode) {
		GWT.log("Clock in error with barcode: " + barcode.getBarcodeNumber().toString());
		confirmationPanelContainer.clear();
		clockedInEmployees.remove(findMatchingClockedInEmployee(barcode));
		updateEmployeeTables();
		cancelClockInOnDatabase(barcode);
	}

	@Override
	public void onClockOutError(EmployeeDto employee) {
		GWT.log("Clock-out error with employee: " + employee.getFirstName() + " " + employee.getLastName());
		clockOutDialogBox.hide();
		mainPanel.setFocus(true);
		//Add the employee back to the employee tables.
		clockedInEmployees.add(employee);
		updateEmployeeTables();
	}

	@Override
	public void getClcockedInEmployeesFromDatabase() {
		GWT.log("Requesting clocked in employees from server");
		timeClockService.getClockedInEmployees(createGetClockedInEmployeesCallback(this));
	}

	@Override
	public void clockInOnDatabase(Barcode barcode) {
		GWT.log("Requesting that the server clock-in employee with barcode number: " + barcode.barcodeNumber);
		timeClockService.clockIn(barcode, createClockInCallback(this));
	}

	@Override
	public void clockOutOnDatabase(EmployeeDto employee) {
		GWT.log("Requesting that the server clock-out employee: " + employee.firstName + " " + employee.lastName);
		timeClockService.clockOut(employee, createClockOutCallback(this));
		clockOutDialogBox.hide();
		mainPanel.setFocus(true);
	}

	@Override
	public void getActivitiesFromDatabase() {
		GWT.log("Requesting activities from server");
		timeClockService.getActivities(createGetActivitiesCallback(this));
	}

	@Override
	public void cancelClockInOnDatabase(Barcode barcode) {
		GWT.log("Requesting cancellation of clock in from Server for barcode number: " + barcode.barcodeNumber);
		timeClockService.cancelClockIn(barcode, createCancelClockInCallback(this));
	}

	@Override
	public void onSuccessfulClockIn(EmployeeDto employee) {
		if (employee == null) {
			GWT.log("Invalid clock-in");
		} else {
			clockInConfirmationPanel = new ClockInConfirmationPanel(this, employee, 400, 372);
			confirmationPanelContainer.clear();
			confirmationPanelContainer.add(clockInConfirmationPanel);
			employeeSignInConfirmationTimer.schedule(TEN_SECONDS_IN_MILLISECONDS);
			clockedInEmployees.add(employee);
			updateEmployeeTables();
			GWT.log("Successful Clock In from Server");
		}
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
			SortedSet<EmployeeDto> clockedInEmployees) {
		this.clockedInEmployees.clear();
		this.clockedInEmployees.addAll(clockedInEmployees);
		updateEmployeeTables();
		GWT.log("Successfully got clocked-in employees from Server");
		for (EmployeeDto employee : clockedInEmployees) {
			GWT.log("     " + employee.getFirstName() + " " + employee.getLastName());
		}
	}

	@Override
	public void onSuccessfulGetActivities(SortedSet<ActivityDto> activities) {
		this.activities.clear();
		this.activities.addAll(activities);
		GWT.log("Successfully got activities from Server:");
		for (ActivityDto activity : activities) {
			GWT.log("     " + activity.getName());
		}
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
