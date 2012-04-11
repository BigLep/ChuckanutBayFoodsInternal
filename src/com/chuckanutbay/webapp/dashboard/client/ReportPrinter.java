package com.chuckanutbay.webapp.dashboard.client;

import com.chuckanutbay.webapp.common.shared.ReportDto;

/**
 * Can print {@link ReportDto}s
 */
public interface ReportPrinter {
	
	/**
	 * @param report The report to be printed.
	 */
	public void print(ReportDto report);
}
