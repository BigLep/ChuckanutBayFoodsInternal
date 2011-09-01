package com.chuckanutbay.print;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.joda.time.DateTime;

public class ReportGenerator {
	@SuppressWarnings("finally")
	public static String generateReport(String report, Map<String, Object> parameters) {
		String pdfFilePath = "reports/generatedreports/" + report + new DateTime().getMillis() + ".pdf";
		try {	
			JasperPrint jasperPrint = JasperFillManager.fillReport("reports/" + report + ".jasper", parameters, DriverManager.getConnection("jdbc:mysql://192.168.0.100:8889/chuckanut_bay_internal", "root", "root"));
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return pdfFilePath;
		}
	}
}
