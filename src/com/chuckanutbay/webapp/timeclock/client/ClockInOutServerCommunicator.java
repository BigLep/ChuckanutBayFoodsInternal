package com.chuckanutbay.webapp.timeclock.client;

import java.util.Set;

import com.chuckanutbay.webapp.common.shared.*;

public interface ClockInOutServerCommunicator {

	public void getClcockedInEmployeesFromDatabase();
	
	public void clockInOnDatabase(BarcodeDto barcode);
	
	public void clockOutOnDatabase(EmployeeDto employee);
	
	public void getActivitiesFromDatabse();
	
	public void cancelClockInOnDatabase(BarcodeDto barcode);
	
	public void onSuccessfulClockIn(EmployeeDto employee);
	
	public void onSuccessfulClockOut();
	
	public void onSuccessfulCancelClockIn();
	
	public void onSuccessfulGetClockedInEmployees(Set<EmployeeDto> clockedInEmployees);
	
	public void onSuccessfulGetActivities(Set<ActivityDto> activities);
}
