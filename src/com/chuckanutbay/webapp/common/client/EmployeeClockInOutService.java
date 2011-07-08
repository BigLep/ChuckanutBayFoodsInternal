package com.chuckanutbay.webapp.common.client;

import java.util.SortedSet;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Since this {@link RemoteService} is defined within a base module,
 * doing {@link GWT#create(Class)} within a sub-module will yield an instance with a path relative to that base module.
 * In order to get an instances with a path that is not relative to the sub-module,
 * use {@link ServiceUtils#createEmployeeService()}.
 * This is the preferred/support method of access.
 */
public interface EmployeeClockInOutService extends RemoteService {
	
	EmployeeDto clockIn(BarcodeDto barcode);
	
	void clockOut(EmployeeDto employeeDto);
	
	SortedSet<EmployeeDto> getClockedInEmployees();
	
	void cancelClockIn(BarcodeDto barcode);
	
	SortedSet<ActivityDto> getActivities();
	
}
