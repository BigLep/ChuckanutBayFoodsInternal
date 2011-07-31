package com.chuckanutbay.webapp.common.client;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Since this {@link RemoteService} is defined within a base module,
 * doing {@link GWT#create(Class)} within a sub-module will yield an instance with a path relative to that base module.
 * In order to get an instances with a path that is not relative to the sub-module,
 * use {@link ServiceUtils#createEmployeeService()}.
 * This is the preferred/support method of access.
 */
public interface TimeClockService extends RemoteService {
	
	EmployeeDto clockIn(Integer barcode);
	
	void clockOut(EmployeeDto employeeDto);
	
	SortedSet<EmployeeDto> getClockedInEmployees();
	
	void cancelClockIn(Integer barcode);
	
	SortedSet<ActivityDto> getActivities();
	
	/**
	 * Finds the day that the most recently completed pay period started on.
	 */
	Date getStartOfLastPayPeriodFromServer();
	
	/**
	 * Finds the day that the most recently completed pay period ended on.
	 */
	Date getEndOfLastPayPeriodFromServer();
	
	/**
	 * @param start The first day of the pay period.
	 * @param end The last day of the pay period.
	 * @param shift The work shift of employees to generate pay period reports for. 0 = all shifts
	 * @return The data needed to generate a pay period report for each employee.
	 */
	List<PayPeriodReportData> getPayPeriodReportDataFromDatabase(Date start, Date end, Integer shift);
}
