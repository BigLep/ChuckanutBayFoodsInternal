package com.chuckanutbay.webapp.common.server;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		
		//Find employee from barcode
		Employee employee = employeeDao.findEmployeeWithBarcodeNumber(barcode.getBarcodeNumber());
		if (employee == null) {
			System.out.println("No Employees had the barcode number");
			return null;
		}
		System.out.println("The employee with the barcode number is: " + employee.getFirstName());
		
		//Start a new employee work interval
		EmployeeWorkInterval newInterval = new EmployeeWorkInterval();
		newInterval.setEmployee(employee);
		newInterval.setStartDateTime(new Date());
		intervalDao.makePersistent(newInterval);
		
		System.out.println("The new interval was persisted");
		
		//Return a complete EmployeeDto
		return completeToEmployeeDto(employee);
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
	
	@SuppressWarnings("unused")
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
			if (interval.getEndDateTime() != null) {
				if (mostRecentlyClosedInterval == null) {
					mostRecentlyClosedInterval = interval;
				} else if (new DateTime(mostRecentlyClosedInterval.getStartDateTime()).getMillis() < new DateTime(interval.getStartDateTime()).getMillis()) {
					mostRecentlyClosedInterval = interval;
				}
			}
			
			//If the interval isn't closed then use the current time to determine the number of minutes worked. Otherwise use the difference between start and end time.
			if (interval.getEndDateTime() == null) {
				Period period = new Period(new DateTime(interval.getStartDateTime()), new DateTime());
				System.out.println("Period length in min: " + period.getMinutes());
				employeeDto.setMinsWorkedThisWeek(employeeDto.getMinsWorkedThisWeek() + period.getMinutes());
			} else {
				Period period = new Period(new DateTime(interval.getStartDateTime()), new DateTime(interval.getEndDateTime()));
				System.out.println("Time now in mill: " + new DateMidnight().getMillis());
				System.out.println("StartTime in mill: " + new DateTime(interval.getStartDateTime()).getMillis());
				System.out.println("EndTime in mill: " + new DateTime(interval.getEndDateTime()).getMillis());
				System.out.println("Period length in min: " + period.getMinutes());
				employeeDto.setMinsWorkedThisWeek(employeeDto.getMinsWorkedThisWeek() + period.getMinutes());
			}
		}
		
		//Set the percentages to be what they were for the most recently closed interval
		Set<EmployeeWorkIntervalActivityPercentageDto> percentageDtos;
		if (mostRecentlyClosedInterval == null) {
			percentageDtos = new HashSet<EmployeeWorkIntervalActivityPercentageDto>();
		} else {
			percentageDtos = new HashSet<EmployeeWorkIntervalActivityPercentageDto>(DtoUtils.transform(mostRecentlyClosedInterval.getEmployeeWorkIntervalActivityPercentages(), DtoUtils.toEmployeeWorkIntervalActivityPercentageDto));
		}
		employeeDto.setEmployeeWorkIntervalPercentages(percentageDtos);
		return employeeDto;
	}
}
