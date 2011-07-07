package com.chuckanutbay.webapp.common.server;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Period;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.EmployeeWorkIntervalActivityPercentage;
import com.chuckanutbay.businessobjects.dao.ActivityDao;
import com.chuckanutbay.businessobjects.dao.ActivityHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeDao;
import com.chuckanutbay.businessobjects.dao.EmployeeHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalHibernateDao;
import com.chuckanutbay.webapp.common.client.EmployeeClockInOutService;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.BarcodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EmployeeClockInOutServiceImpl extends RemoteServiceServlet implements EmployeeClockInOutService {

	private static final long serialVersionUID = 1L;
	
	@Override
	public EmployeeDto clockIn(BarcodeDto barcode) {
		EmployeeDao dao = new EmployeeHibernateDao();
		return completeToEmployeeDto(dao.findEmployeeWithBarcodeNumber(barcode.getBarcodeNumber()));
	}

	@Override
	public void clockOut(EmployeeDto employeeDto) {
		EmployeeWorkIntervalDao dao = new EmployeeWorkIntervalHibernateDao();
		EmployeeWorkInterval interval = dao.findOpenEmployeeWorkInterval(DtoUtils.fromEmployeeDto(employeeDto));
		interval.setEndDateTime(new Date());
		interval.setEmployeeWorkIntervalActivityPercentages(new HashSet<EmployeeWorkIntervalActivityPercentage>(DtoUtils.transform(employeeDto.getEmployeeWorkIntervalPercentages(), DtoUtils.fromEmployeeWorkIntervalActivityPercentageDto)));
		dao.makePersistent(interval);
	}

	@Override
	public SortedSet<EmployeeDto> getClockedInEmployees() {
		EmployeeWorkIntervalDao dao = new EmployeeWorkIntervalHibernateDao();
		SortedSet<EmployeeDto> employees = new TreeSet<EmployeeDto>();
		for (EmployeeWorkInterval interval : dao.findOpenEmployeeWorkIntervals()) {
			employees.add(completeToEmployeeDto(interval.getEmployee()));
		}
		return employees;
	}

	@Override
	public void cancelClockIn(BarcodeDto barcode) {
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		Employee employee = employeeDao.findEmployeeWithBarcodeNumber(barcode.getBarcodeNumber());
		
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		EmployeeWorkInterval interval = intervalDao.findOpenEmployeeWorkInterval(employee);
		intervalDao.makeTransient(interval);
		
	}

	@Override
	public SortedSet<ActivityDto> getActivities() {
		ActivityDao dao = new ActivityHibernateDao();
		return new TreeSet<ActivityDto>(DtoUtils.transform(dao.findAll(), DtoUtils.toActivityDto));
	}
	
	private static EmployeeDto completeToEmployeeDto(Employee employee) {
		EmployeeWorkIntervalDao dao = new EmployeeWorkIntervalHibernateDao();
		
		//Find last Sunday at the start of the day
		DateMidnight dm = new DateMidnight();
		Date lastSunday = dm.minusDays(dm.dayOfWeek().get()).toDate();
		
		//Do an incomplete conversion form Employee to EmployeeDto
		EmployeeDto employeeDto = DtoUtils.toEmployeeDto(employee);
		 
		employeeDto.setMinsWorkedThisWeek(0);
		EmployeeWorkInterval mostRecentlyClosedInterval = null;
		List<EmployeeWorkInterval> employeeWorkIntervals = dao.findEmployeeWorkIntervalsSinceDate(lastSunday, employee);
		for (EmployeeWorkInterval interval : employeeWorkIntervals) {
			
			//Determine if the interval is the most recently closed
			if (mostRecentlyClosedInterval == null) {
				mostRecentlyClosedInterval = interval;
			} else if (interval.getEndDateTime() == null && new DateTime(mostRecentlyClosedInterval.getStartDateTime()).getMillis() < new DateTime(interval.getStartDateTime()).getMillis()) {
				mostRecentlyClosedInterval = interval;
			}
			//If the interval is the most recently closed interval then use those percentages 
			if (interval.getId() == employeeWorkIntervals.get(1).getId()) {
				
			}
			
			//If the interval isn't closed then use the current time to determine the number of minutes worked. Otherwise use the difference between start and end time.
			if (interval.getEndDateTime() == null) {
				Period period = new Period(new DateTime(interval.getStartDateTime()), new DateTime());
				employeeDto.setMinsWorkedThisWeek(employeeDto.getMinsWorkedThisWeek() + period.getMinutes());
			} else {
				Period period = new Period(new DateTime(interval.getStartDateTime()), new DateTime(interval.getEndDateTime()));
				employeeDto.setMinsWorkedThisWeek(employeeDto.getMinsWorkedThisWeek() + period.getMinutes());
			}
		}
		
		//Set the percentages to be what they were for the most recently closed interval
		employeeDto.setEmployeeWorkIntervalPercentages(new HashSet<EmployeeWorkIntervalActivityPercentageDto>(DtoUtils.transform(mostRecentlyClosedInterval.getEmployeeWorkIntervalActivityPercentages(), DtoUtils.toEmployeeWorkIntervalActivityPercentageDto)));
		return employeeDto;
	}
}
