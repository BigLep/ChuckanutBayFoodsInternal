package com.chuckanutbay.webapp.common.client;

import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @see TrayLabelServiceAsync
 */
public interface ReportServiceAsync {
	
	void printReport(ReportDto report, String printer, AsyncCallback<Void> callback);
	
}
