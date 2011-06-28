package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;

public interface ClockInOutErrorHandler {

	public void onClockInError(BarcodeDto barcode);
	
	public void onClockOutError(EmployeeDto employee);
}
