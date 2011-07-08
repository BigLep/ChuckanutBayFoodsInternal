package com.chuckanutbay.webapp.timeclock.client;

import java.util.Date;

import com.chuckanutbay.webapp.common.client.EmployeeClockInOutService;

public interface TimeClockReportHandler {
	
	/**
	 * See {@link EmployeeClockInOutService}
	 */
	void getStartOfLastPayPeriodFromServer();
	
	void getEndOfLastPayPeriodFromServer();
	
	void onSuccessfulGetStartOfLastPayPeriod(Date date);
	
	void onSuccessfulGetEndOfLastPayPeriod(Date date);
}
