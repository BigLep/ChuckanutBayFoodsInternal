package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;

/**
 * Handles scanned barcodes to clock employees in or out.
 *
 */
public interface ScanInOutHandler {
	
	/**
	 * Handles a scan event.
	 * @param barcode The {@link BarcodeDto} of the scan.
	 */
	public void onScan(BarcodeDto barcode);
	
	/**
	 * Handles a clock in scan event.
	 * @param barcode The {@link BarcodeDto} of the scan in.
	 */
	public void onClockInScan(BarcodeDto barcode);
	
	/**
	 * Handles a clock out scan event.
	 * @param employee The {@link EmployeeDto} scanning out.
	 */
	public void onClockOutScan(EmployeeDto employee);
}
