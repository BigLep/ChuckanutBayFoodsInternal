package com.chuckanutbay.print;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class ReportUtil {
	public static final String TRAY_LABEL = "trayLabel";
	
	public static List<String> getReportStrings() {
		return newArrayList(TRAY_LABEL);
	}
}
