package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.BarcodeDto;

/**
 * Handles scanned barcodes to clock employees in or out
 * @author mloeppky
 *
 */
public interface ScanInOutHandler {
	
	public void onScan(BarcodeDto barcode);
	
	public void onClockInScan(BarcodeDto barcode);
	
	public void onClockOutScan(BarcodeDto barcode);
}
