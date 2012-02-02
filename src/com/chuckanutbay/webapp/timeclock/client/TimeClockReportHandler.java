package com.chuckanutbay.webapp.timeclock.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.client.TimeClockService;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.IntervalDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;

public interface TimeClockReportHandler {
	
	/**
	 * See {@link TimeClockService}
	 */
	void getLastPayPeriodIntervalFromServer();
	
	void getPayPeriodReportData(Date start, Date end, Integer shift);
	
	void getPayPeriodReportData(Date start, Date end, EmployeeDto employee);
	
	void getEmployees();
	
	void onSuccessfulGetLastPayPeriodInterval(IntervalDto interval);
	
	void onSuccessfulGetPayPeriodReportData(List<PayPeriodReportData> reportData);
	
	void onSuccessfulGetEmployees(List<EmployeeDto> employees);
}
