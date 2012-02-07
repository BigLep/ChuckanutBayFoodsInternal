package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.print.ReportUtil.getCompiledReportImportFilePath;

import java.util.HashMap;

import com.chuckanutbay.print.Print;
import com.chuckanutbay.print.ReportGenerator;
import com.chuckanutbay.webapp.common.client.ReportService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportServiceImpl extends RemoteServiceServlet implements ReportService {

	private static final long serialVersionUID = 1L;

	@Override
	public void printReport(String name, String printer) {
		String pdfFilePath = new ReportGenerator().generateReport(getCompiledReportImportFilePath(name), new HashMap<String, Object>());
		Print.print(pdfFilePath, printer);
	}
	
	
}
