package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.Barcode;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;

public interface ClockInOutErrorHandler {
	
	/**
	 * Handles any mistakes by a user when clocking in. 
	 * @param barcode The {@link Barcode} associated with the error.
	 */
	public void onClockInError(Barcode barcode);
	
	/**
	 * Handles any mistakes by a user when clocking out. 
	 * @param employee The {@link EmployeeDto} associated with the error.
	 */
	public void onClockOutError(EmployeeDto employee);
}
