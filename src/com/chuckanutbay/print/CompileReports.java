package com.chuckanutbay.print;

import static com.chuckanutbay.print.ReportUtil.REPORT_NAMES;
import static com.chuckanutbay.print.ReportUtil.getCompiledReportExportFilePath;
import static com.chuckanutbay.print.ReportUtil.getUncompiledReportFilePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import com.google.common.io.Closeables;

public class CompileReports {
	
	public static void main(String[] args) {
		try {
			for (String uncompiledReport : REPORT_NAMES) {
				JasperReport report = JasperCompileManager.compileReport(getUncompiledReportFilePath(uncompiledReport));
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(getCompiledReportExportFilePath(uncompiledReport))));
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
	}
}
