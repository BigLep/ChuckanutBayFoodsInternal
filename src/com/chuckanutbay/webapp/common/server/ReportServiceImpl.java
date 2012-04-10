package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.reportgeneration.ReportGenerator.generateReport;

import java.io.FileNotFoundException;

import javax.print.PrintException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuckanutbay.reportgeneration.Print;
import com.chuckanutbay.webapp.common.client.ReportService;
import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportServiceImpl extends RemoteServiceServlet implements ReportService {

	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class.getName());
	
	@Override
	public void printReport(ReportDto report, String printer) {
		Timer timer = new Timer(LOGGER).start("Print Report Request Received");
		try {
			Print.print(generateReport(report), printer);
		} catch (FileNotFoundException e) {
			doUnexpectedFailure(e);
		} catch (PrintException e) {
			doUnexpectedFailure(e);
		}
		timer.stop("METHOD FINISHED");
	}
	
}
