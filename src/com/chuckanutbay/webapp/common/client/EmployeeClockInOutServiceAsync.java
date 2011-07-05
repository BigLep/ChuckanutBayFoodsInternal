package com.chuckanutbay.webapp.common.client;

import java.util.Set;
import java.util.SortedSet;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmployeeClockInOutServiceAsync {

	void cancelClockIn(BarcodeDto barcode, AsyncCallback<Void> callback);

	void clockIn(BarcodeDto barcode, AsyncCallback<EmployeeDto> callback);

	void clockOut(EmployeeDto employee, AsyncCallback<Void> callback);

	void getActivities(AsyncCallback<SortedSet<ActivityDto>> callback);

	void getClockedInEmployees(AsyncCallback<SortedSet<EmployeeDto>> callback);

}
