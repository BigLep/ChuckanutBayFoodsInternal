package com.chuckanutbay.webapp.common.shared;

import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDto implements Serializable {
	//File Path Bases
	private static final String EXPORT_FILE_PATH_BASE = "resources/reports/";
	private static final String IMPORT_FILE_PATH_BASE = "reports/";
	private static final String IMAGES_FILE_PATH_BASE = "images/";
	
	//Report Names
	public static final String TRAY_LABEL = "TrayLabel";
	public static final String SCHEDULE = "Schedule";
	public static final String END_OF_SHIFT = "EndOfShift";
	public static final String DIGITAL_LABELS = "DigitalLabels";
	public static final String DIGITAL_LABEL_SUBREPORT = "DigitalLabelSubreport";
	public static final List<String> REPORT_NAMES = newArrayList(TRAY_LABEL, SCHEDULE, DIGITAL_LABELS, DIGITAL_LABEL_SUBREPORT, END_OF_SHIFT);
	
	//Image Names
	public static final String LOGO = "Logo";
	
	//Extensions
	private static final String UNCOMPILED_REPORT_EXTENTION = ".jrxml";
	private static final String COMPILED_REPORT_EXTENTION = ".jasper";
	private static final String JPEG_EXTENTION = ".jpg";
	
	//Fields
	private String name;
	private Map<String, Object> parameters;
	
	public ReportDto() {
		parameters = new HashMap<String, Object>();
		parameters.put("LOGO", getImageFilePath(LOGO));
	}
	
	public ReportDto setName(String reportName) {
		this.name = reportName;
		return this;
	}
	
	public ReportDto setSubreport(String subreportName) {
		parameters.put("SUBREPORT", new ReportDto().setName(subreportName));
		return this;
	}
	
	public ReportDto addParameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public String getUncompiledFilePath() {
		return IMPORT_FILE_PATH_BASE + name + UNCOMPILED_REPORT_EXTENTION;
	}
	
	public String getCompiledFilePath() {
		return IMPORT_FILE_PATH_BASE + name + COMPILED_REPORT_EXTENTION;
	}
	
	/**
	 * Once a report is compiled it has to be saved to a different file path than it is accessed at.
	 */
	public String getCompiledDestinationFilePath() {
		return EXPORT_FILE_PATH_BASE + name + COMPILED_REPORT_EXTENTION;
	}
	
	private static String getImageFilePath(String image) {
		return IMAGES_FILE_PATH_BASE + image + JPEG_EXTENTION;
	}
		
}


