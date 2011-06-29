package com.chuckanutbay.webapp.timeclock.client;

import java.util.Set;

import com.chuckanutbay.webapp.common.shared.*;

public interface ClockInOutServerCommunicator {
	
	/**
	 * Request all of the clocked in employees from the database.
	 */
	public void getClcockedInEmployeesFromDatabase();
	
	/**
	 * Request that the server clock in the employee with the matching barcode number.
	 * @param barcode The {@link BarcodeDto} of the employee requesting a clock in.
	 */
	public void clockInOnDatabase(BarcodeDto barcode);
	
	/**
	 * Request that the server clock out an employee.
	 * @param employee The {@link EmployeeDto} requesting clock out.
	 */
	public void clockOutOnDatabase(EmployeeDto employee);
	
	/**
	 * Request all of the activities from the server.
	 */
	public void getActivitiesFromDatabse();
	
	/**
	 * Request that the server cancel the clock in of an an {@link employeeDto} with matching {@link BarcodeDto}.
	 * @param barcode The {@link BarcodeDto} of the employee requesting cancelation.
	 */
	public void cancelClockInOnDatabase(BarcodeDto barcode);
	
	/**
	 * When the server returns a successful clock in.
	 * @param employee The {@link EmployeeDto} sent from the server.
	 */
	public void onSuccessfulClockIn(EmployeeDto employee);
	
	/**
	 * When the server returns a successful clock out.
	 */
	public void onSuccessfulClockOut();
	
	/**
	 * When the server returns a successful clock in.
	 */
	public void onSuccessfulCancelClockIn();
	
	/**
	 * When the server successfully returns the {@link clockedInEmployees}.
	 * @param clockedInEmployees The {@link EmployeeDto}s sent from the server.
	 */
	public void onSuccessfulGetClockedInEmployees(Set<EmployeeDto> clockedInEmployees);
	
	/**
	 * When the server successfully returns the {@link activities}.
	 * @param activities The {@link activities} sent from the server.
	 */
	public void onSuccessfulGetActivities(Set<ActivityDto> activities);
}
