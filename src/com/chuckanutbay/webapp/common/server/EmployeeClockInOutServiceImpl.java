package com.chuckanutbay.webapp.common.server;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.chuckanutbay.businessobjects.dao.ActivityDao;
import com.chuckanutbay.businessobjects.dao.ActivityHibernateDao;
import com.chuckanutbay.webapp.common.client.EmployeeClockInOutService;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalPercentageDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EmployeeClockInOutServiceImpl extends RemoteServiceServlet implements EmployeeClockInOutService {

	private static final long serialVersionUID = 1L;
	
	private SortedSet<EmployeeDto> employees;
	private Set<EmployeeWorkIntervalPercentageDto> activitiyPercentages;
	private SortedSet<ActivityDto> activities;
	
	public EmployeeClockInOutServiceImpl() {
		activities = new TreeSet<ActivityDto>();
		activities.add(new ActivityDto(1, "Production"));
		activities.add(new ActivityDto(2, "Packaging"));
		activities.add(new ActivityDto(3, "RIE"));
		activities.add(new ActivityDto(4, "DropShip"));
		activities.add(new ActivityDto(5, "Other"));
		activitiyPercentages = new HashSet<EmployeeWorkIntervalPercentageDto>();
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(1, new ActivityDto(1, "Production"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(2, new ActivityDto(2, "Packaging"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(3, new ActivityDto(3, "RIE"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(4, new ActivityDto(4, "Drop Ship"), 20));
		activitiyPercentages.add(new EmployeeWorkIntervalPercentageDto(5, new ActivityDto(5, "Other"), 20));
		employees = new TreeSet<EmployeeDto>();
		employees.add(new EmployeeDto(1, "Steve", "Jobs", 300, activitiyPercentages, new BarcodeDto(123456789)));
		employees.add(new EmployeeDto(2, "Bill", "Gates", 222, activitiyPercentages, new BarcodeDto(234567890)));
		employees.add(new EmployeeDto(3, "Tom", "Brady", 121, activitiyPercentages, new BarcodeDto(345678901)));
	}
	
	@Override
	public EmployeeDto clockIn(BarcodeDto barcode) {
		if (barcode.equals(new BarcodeDto(456789012))) {
			return new EmployeeDto(4, "Mitchell", "Loeppky", 1220, activitiyPercentages, new BarcodeDto(456789012));
		}
		return null;
	}

	@Override
	public void clockOut(EmployeeDto employee) {
		//Do nothing right now
	}

	@Override
	public SortedSet<EmployeeDto> getClockedInEmployees() {
		/**
		String today = new String(DateTimeFormat.getFormat("EEEE, MMMM d, y h:mm:ss a zzzz").format(new Date()));
		GWT.log("Today: " + today);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.roll(cal.WEEK_OF_YEAR, false);
		String oneWeekAgo = new String(DateTimeFormat.getFormat("EEEE, MMMM d, y h:mm:ss a zzzz").format(cal.getTime()));
		GWT.log("One Week Ago: " + oneWeekAgo);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String lastSunday = new String(DateTimeFormat.getFormat("EEEE, MMMM d, y h:mm:ss a zzzz").format(cal.getTime()));
		GWT.log("One Week Ago: " + lastSunday);
		*/
		return employees;
	}

	@Override
	public void cancelClockIn(BarcodeDto barcode) {
		//Do nothing right now
	}

	@Override
	public SortedSet<ActivityDto> getActivities() {
		ActivityDao dao = new ActivityHibernateDao();
		return new TreeSet<ActivityDto>(DtoUtils.transform(dao.findAll(), DtoUtils.toActivityDto));
	}
}
