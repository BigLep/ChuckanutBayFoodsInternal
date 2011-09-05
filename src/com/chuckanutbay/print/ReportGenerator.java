package com.chuckanutbay.print;

import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.joda.time.DateTime;

import com.chuckanutbay.businessobjects.util.HibernateUtil;

public class ReportGenerator {
	@SuppressWarnings("finally")
	public static String generateReport(String report, Map<String, Object> parameters) {
		try {
			String pdfFilePath = "reports/generatedreports/" + report + new DateTime().getMillis() + ".pdf";
			JasperPrint jasperPrint = JasperFillManager.fillReport("reports/" + report + ".jasper", parameters, HibernateUtil.getSession().connection());
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);
			return pdfFilePath;
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
}
