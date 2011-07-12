package com.chuckanutbay.webapp.common.client;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.Barcode;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TimeClockServiceAsync {

	void cancelClockIn(Barcode barcode, AsyncCallback<Void> callback);

	void clockIn(Barcode barcode, AsyncCallback<EmployeeDto> callback);

	void clockOut(EmployeeDto employeeDto, AsyncCallback<Void> callback);

	void getActivities(AsyncCallback<SortedSet<ActivityDto>> callback);

	void getClockedInEmployees(AsyncCallback<SortedSet<EmployeeDto>> callback);

	void getEndOfLastPayPeriodFromServer(AsyncCallback<Date> callback);

	void getStartOfLastPayPeriodFromServer(AsyncCallback<Date> callback);

	void getPayPeriodReportDataFromDatabase(Date start, Date end,
			Integer shift, AsyncCallback<List<PayPeriodReportData>> callback);

}
