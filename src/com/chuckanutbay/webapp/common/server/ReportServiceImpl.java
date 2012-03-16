package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.reportgeneration.ReportGenerator.generateReport;

import com.chuckanutbay.reportgeneration.Print;
import com.chuckanutbay.webapp.common.client.ReportService;
import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportServiceImpl extends RemoteServiceServlet implements ReportService {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void printReport(ReportDto report, String printer) {
		Print.print(generateReport(report), printer);
	}
	
}
