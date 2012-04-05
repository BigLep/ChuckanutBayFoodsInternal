package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.EmployeeDto;

/**
 * Handles scanned barcodes to clock employees in or out.
 *
 */
public interface ScanInOutHandler {
	
	/**
	 * Handles a clock in scan event.
	 * @param barcode The barcode of the scan in.
	 */
	public void onClockInScan(Integer barcode);
	
	/**
	 * Handles a clock out scan event.
	 * @param employee The {@link EmployeeDto} scanning out.
	 */
	public void onClockOutScan(EmployeeDto employee);
}
