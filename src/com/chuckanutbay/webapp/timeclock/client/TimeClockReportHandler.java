package com.chuckanutbay.webapp.timeclock.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.client.TimeClockService;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;

public interface TimeClockReportHandler {
	
	/**
	 * See {@link TimeClockService}
	 */
	void getStartOfLastPayPeriodFromServer();
	
	void getEndOfLastPayPeriodFromServer();
	
	void getPayPeriodReportData(Date start, Date end, Integer shift);
	
	void onSuccessfulGetStartOfLastPayPeriod(Date date);
	
	void onSuccessfulGetEndOfLastPayPeriod(Date date);
	
	void onSuccessfulGetPayPeriodReportData(List<PayPeriodReportData> reportData);
}
