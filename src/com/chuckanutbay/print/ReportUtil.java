package com.chuckanutbay.print;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class ReportUtil {
	private static final String EXPORT_FILE_PATH_BASE = "resources/reports/";
	private static final String IMPORT_FILE_PATH_BASE = "reports/";
	public static final String TRAY_LABEL = "TrayLabel";
	public static final String SCHEDULE = "Schedule";
	public static final String DIGITAL_LABELS = "DigitalLabels";
	public static final List<String> REPORT_NAMES = newArrayList(TRAY_LABEL, SCHEDULE, DIGITAL_LABELS);
	private static final String UNCOMPILED_REPORT_EXTENTION = ".jrxml";
	private static final String COMPILED_REPORT_EXTENTION = ".jasper";

	
	public static String getUncompiledReportFilePath(String report) {
		return EXPORT_FILE_PATH_BASE + report + UNCOMPILED_REPORT_EXTENTION;
	}
	
	public static String getCompiledReportExportFilePath(String report) {
		return EXPORT_FILE_PATH_BASE + report + COMPILED_REPORT_EXTENTION;
	}
	
	public static String getCompiledReportImportFilePath(String report) {
		return IMPORT_FILE_PATH_BASE + report + COMPILED_REPORT_EXTENTION;
	}
	
}
