package com.chuckanutbay.webapp.common.client;

import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * @see TrayLabelService
 */
public interface ReportService extends RemoteService {

	void printReport(ReportDto report, String printer);
	
}
