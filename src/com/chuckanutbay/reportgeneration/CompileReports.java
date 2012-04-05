package com.chuckanutbay.reportgeneration;
/*
import static com.chuckanutbay.webapp.common.shared.ReportDto.REPORT_NAMES;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.common.io.Closeables;
*/

/**
 * You should never need to use this class. {@link ReportGenerator} automatically compiles the report.
 */
public class CompileReports {
	
	public static void main(String[] args) {
		/*
		try {
			for (String uncompiledReportName : REPORT_NAMES) {
				ReportDto uncompiledReport = new ReportDto().setName(uncompiledReportName);
				JasperReport report = JasperCompileManager.compileReport(uncompiledReport.getUncompiledFilePath());
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(uncompiledReport.getCompiledDestinationFilePath())));
				outputStream.writeObject(report);
				Closeables.closeQuietly(outputStream);
			}
		} catch (JRException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		*/
	}
}
