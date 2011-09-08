package com.chuckanutbay.print;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.joda.time.DateTime;

import com.chuckanutbay.businessobjects.util.HibernateUtil;
import com.chuckanutbay.webapp.common.server.Timer;
import com.google.common.io.Files;

public class ReportGenerator {
	
	private static final File generatedReportsDir = Files.createTempDir();
	
	public String generateReport(String report, Map<String, Object> parameters) {
		try {
			Timer timer = new Timer(Logger.getLogger(ReportGenerator.class.getName())).start("Generating Report:");
			String pdfFilePath = new File(generatedReportsDir, report + new DateTime().getMillis() + ".pdf").getAbsolutePath();
			InputStream reportStream = getClass().getClassLoader().getResourceAsStream("reports/" + report + ".jasper");
			timer.logTime("\tFound report file:");
			
			HibernateUtil.beginTransaction();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, HibernateUtil.getSession().connection());
			HibernateUtil.commitTransaction();
			timer.logTime("\tFilled report:");
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);
			timer.logTime("\tGenerated PDF:");
			return pdfFilePath;
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
}
