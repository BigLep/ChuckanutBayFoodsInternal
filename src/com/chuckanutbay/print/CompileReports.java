package com.chuckanutbay.print;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileReports {
	
	public static void main(String[] args) {
		try {
			for (String reportName : ReportUtil.getReportStrings()) {
				JasperCompileManager.compileReport("war/reports/" + reportName + ".jrxml");
			}
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
}
