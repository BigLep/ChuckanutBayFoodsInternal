package com.chuckanutbay.webapp.common.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		EmployeeWorkIntervalActivityPercentageDao percentageDao = new EmployeeWorkIntervalActivityPercentageHibernateDao();
				
		EmployeeWorkInterval interval = intervalDao.findOpenEmployeeWorkInterval(DtoUtils.fromEmployeeDto(employeeDto));
		interval.setEndDateTime(new Date());
		intervalDao.makePersistent(interval);
		System.out.println("Persisted Interval");
		
		List<EmployeeWorkIntervalActivityPercentage> percentages = DtoUtils.transform(employeeDto.getEmployeeWorkIntervalPercentages(), DtoUtils.fromEmployeeWorkIntervalActivityPercentageDto);
		for (EmployeeWorkIntervalActivityPercentage percentage : percentages) {
			percentage.setEmployeeWorkInterval(interval);
			System.out.println("Percentage: ActivityId(" + percentage.getActivity().getId() + ") IntervalId(" + percentage.getEmployeeWorkInterval().getId() + ") Percentage(" + percentage.getPercentage() + ") of " + percentages.size());
			percentageDao.makePersistent(percentage);
		}
		System.out.println("Persisted Percentages");
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
				Period period = new Period(new DateTime(interval.getStartDateTime()), new DateTime(), PeriodType.minutes());
				System.out.println("Period length in min: " + period.getMinutes());
				System.out.println("Minutes worked before update: " + employeeDto.getMinsWorkedThisWeek());
				employeeDto.setMinsWorkedThisWeek(employeeDto.getMinsWorkedThisWeek() + period.getMinutes());
				System.out.println("Minutes worked after update: " + employeeDto.getMinsWorkedThisWeek());
			} else {
				Period period = new Period(new DateTime(interval.getStartDateTime()), new DateTime(interval.getEndDateTime()), PeriodType.minutes());
				System.out.println("Time now in mill: " + new DateMidnight().getMillis());
				System.out.println("StartTime in mill: " + new DateTime(interval.getStartDateTime()).getMillis());
				System.out.println("EndTime in mill: " + new DateTime(interval.getEndDateTime()).getMillis());
				System.out.println("Period length in sec: " + period.getSeconds());
				System.out.println("Minutes worked before update: " + employeeDto.getMinsWorkedThisWeek());
				employeeDto.setMinsWorkedThisWeek(employeeDto.getMinsWorkedThisWeek() + period.getMinutes());
				System.out.println("Minutes worked after update: " + employeeDto.getMinsWorkedThisWeek());
			}
		}
		
		//Set the percentages to be what they were for the most recently closed interval
		List<EmployeeWorkIntervalActivityPercentageDto> percentageDtos;
		if (mostRecentlyClosedInterval == null) {
			percentageDtos = new ArrayList<EmployeeWorkIntervalActivityPercentageDto>();
		} else {
			percentageDtos = DtoUtils.transform(mostRecentlyClosedInterval.getEmployeeWorkIntervalActivityPercentages(), DtoUtils.toEmployeeWorkIntervalActivityPercentageDto);
		}
		employeeDto.setEmployeeWorkIntervalPercentages(percentageDtos);
		return employeeDto;
	}

	@Override
	public Date getStartOfLastPayPeriodFromServer() {
		DateTime startOfPayPeriod = new DateTime();
		DateTime today = new DateTime();
		if(today.getDayOfMonth() > 15) {
			startOfPayPeriod = startOfPayPeriod.withDayOfMonth(1);
		} else {
			startOfPayPeriod = startOfPayPeriod.minusMonths(1);
			startOfPayPeriod = startOfPayPeriod.withDayOfMonth(16);
		}
		return startOfPayPeriod.toDate();
	}

	@Override
	public Date getEndOfLastPayPeriodFromServer() {
		DateTime endOfPayPeriod = new DateTime();
		DateTime today = new DateTime();
		if(today.getDayOfMonth() > 15) {
			endOfPayPeriod = endOfPayPeriod.withDayOfMonth(15);
		} else {
			endOfPayPeriod = endOfPayPeriod.minusMonths(1);
			endOfPayPeriod = endOfPayPeriod.dayOfMonth().withMaximumValue();
		}
		return endOfPayPeriod.toDate();
	}
}
