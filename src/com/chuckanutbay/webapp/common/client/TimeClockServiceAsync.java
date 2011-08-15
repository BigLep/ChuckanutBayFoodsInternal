package com.chuckanutbay.webapp.common.client;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.IntervalDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TimeClockServiceAsync {

	void cancelClockIn(Integer barcode, AsyncCallback<Void> callback);

	void clockIn(Integer barcode, AsyncCallback<EmployeeDto> callback);

	void clockOut(EmployeeDto employeeDto, AsyncCallback<Void> callback);

	void getActivities(AsyncCallback<SortedSet<ActivityDto>> callback);

	void getClockedInEmployees(AsyncCallback<SortedSet<EmployeeDto>> callback);

	void getLastPayPeriodIntervalFromServer(Date date, AsyncCallback<IntervalDto> callback);

	void getPayPeriodReportDataFromDatabase(Date start, Date end,
			Integer shift, AsyncCallback<List<PayPeriodReportData>> callback);

	void updateMinutesWorkedInCurrentWeek(SortedSet<EmployeeDto> employees,
			AsyncCallback<SortedSet<EmployeeDto>> callback);

}
