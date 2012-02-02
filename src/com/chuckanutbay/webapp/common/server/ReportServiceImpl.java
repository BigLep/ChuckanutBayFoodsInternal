package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.print.Print.HP_WIRELESS_P1102W;
import static com.chuckanutbay.print.ReportUtil.TRAY_LABEL;
import static com.chuckanutbay.print.ReportUtil.getCompiledReportImportFilePath;
import static com.chuckanutbay.webapp.common.server.DtoUtils.fromEmployeeDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toEmployeeDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.EmployeeWorkIntervalActivityPercentage;
import com.chuckanutbay.businessobjects.dao.ActivityDao;
import com.chuckanutbay.businessobjects.dao.ActivityHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeDao;
import com.chuckanutbay.businessobjects.dao.EmployeeHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalHibernateDao;
import com.chuckanutbay.print.Print;
import com.chuckanutbay.print.ReportGenerator;
import com.chuckanutbay.webapp.common.client.ReportService;
import com.chuckanutbay.webapp.common.client.TimeClockService;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.DayReportData;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalDto;
import com.chuckanutbay.webapp.common.shared.IntervalDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.chuckanutbay.webapp.common.shared.WeekReportData;
import com.google.common.collect.Maps;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportServiceImpl extends RemoteServiceServlet implements ReportService {

	private static final long serialVersionUID = 1L;

	@Override
	public void printReport(String name, String printer) {
		String pdfFilePath = new ReportGenerator().generateReport(getCompiledReportImportFilePath(TRAY_LABEL), new HashMap<String, Object>());
		Print.print(pdfFilePath, printer);
	}
	
	
}
