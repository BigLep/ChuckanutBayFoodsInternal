package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;

public interface ClockInOutErrorHandler {
	
	/**
	 * Handles any mistakes by a user when clocking in. 
	 * @param barcode The {@link BarcodeDto} associated with the error.
	 */
	public void onClockInError(BarcodeDto barcode);
	
	/**
	 * Handles any mistakes by a user when clocking out. 
	 * @param employee The {@link EmployeeDto} associated with the error.
	 */
	public void onClockOutError(EmployeeDto employee);
}
