package com.chuckanutbay.webapp.common.client;

public interface ScanHandler {
	/**
	 * Handles a scan event.
	 * @param barcode The barcode value of the scan.
	 */
	public void onScan(String barcode);
}
