package com.chuckanutbay.webapp.common.server;

import static com.google.common.collect.Lists.newArrayList;

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
import com.chuckanutbay.webapp.common.client.TimeClockService;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.Barcode;
import com.chuckanutbay.webapp.common.shared.DayReportData;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalDto;
import com.chuckanutbay.webapp.common.shared.PayPeriodReportData;
import com.chuckanutbay.webapp.common.shared.WeekReportData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TimeClockServiceImpl extends RemoteServiceServlet implements TimeClockService {

	private static final long serialVersionUID = 1L;
	
	@Override
	public EmployeeDto clockIn(Barcode barcode) {
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
	public void cancelClockIn(Barcode barcode) {
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
		DateMidnight lastSunday = new DateMidnight();
		lastSunday = lastSunday.minusDays(lastSunday.getDayOfWeek());
		
		//Do an incomplete conversion form Employee to EmployeeDto
		EmployeeDto employeeDto = DtoUtils.toEmployeeDto(employee);
		 
		employeeDto.setMinsWorkedThisWeek(0);
		EmployeeWorkInterval mostRecentlyClosedInterval = null;
		List<EmployeeWorkInterval> employeeWorkIntervals = dao.findEmployeeWorkIntervalsBetweenDates(employee, lastSunday.toDateTime(), new DateTime());
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

	@Override
	public List<PayPeriodReportData> getPayPeriodReportDataFromDatabase(
			Date start, Date end, Integer shift) {
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		
		List<PayPeriodReportData> reportData = newArrayList();

		DateMidnight payPeriodStart = new DateMidnight(start);
		System.out.println("Pay Period Start: " + payPeriodStart.toString("MMMM hh:mm a"));
		DateMidnight payPeriodEnd = new DateMidnight(end);
		System.out.println("Pay Period End: " + payPeriodStart.toString("MMMM hh:mm a"));
		payPeriodEnd = payPeriodEnd.plusDays(1);
		DateMidnight sundayBeforePayPeriodStart;
		
		if (payPeriodStart.getDayOfWeek() == 7) {
			sundayBeforePayPeriodStart = payPeriodStart;
		} else {
			sundayBeforePayPeriodStart = payPeriodStart.minusDays(payPeriodStart.getDayOfWeek());
		}
		System.out.println("Sunday Before Period Start: " + payPeriodStart.toString("MMMM hh:mm a"));
		
		List<Employee> employees;
		if (shift == 0) {
			employees = employeeDao.findAll();
		} else {
			employees = employeeDao.findEmployeesByShift(shift);
		}
		for (Employee employee : employees) {
			if (intervalDao.findEmployeeWorkIntervalsBetweenDates(employee, payPeriodStart.toDateTime(), payPeriodEnd.toDateTime()).size() != 0) {
				PayPeriodReportData payPeriod = new PayPeriodReportData();
				payPeriod.setName(employee.getFirstName() + " " + employee.getLastName());
				payPeriod.setDate(new Date());
				payPeriod.setPayPeriodStart(payPeriodStart.toDate());
				payPeriod.setPayPeriodEnd(payPeriodEnd.minusDays(1).toDate());
				
				WeekReportData week = new WeekReportData();
				payPeriod.addInterval(week);
				DayReportData day = new DayReportData();
				week.addInterval(day);
				
				int d = 0;
				
				while (d < new Period(sundayBeforePayPeriodStart, payPeriodStart, PeriodType.days()).getDays()) {
					for (EmployeeWorkInterval interval : findIntervalsHelper(employee, sundayBeforePayPeriodStart, d)) {
						double intervalHours = getDifference(interval.getStartDateTime(), interval.getEndDateTime());
						week.addPrePayPeriodHours(intervalHours);
					}
					d++;
				}
				
				while (d < new Period(sundayBeforePayPeriodStart, payPeriodEnd, PeriodType.days()).getDays()) {
					if (d == 7 || d == 14 || d == 21) {
						week = new WeekReportData();
						payPeriod.addInterval(week);
					} 
					for (EmployeeWorkInterval interval : findIntervalsHelper(employee, sundayBeforePayPeriodStart, d)) {
						if (interval.getEndDateTime() != null) {
							double intervalHours = getDifference(interval.getStartDateTime(), interval.getEndDateTime());
							week.addHours(intervalHours);
							day.addHours(intervalHours);
							EmployeeWorkIntervalDto intervalDto = DtoUtils.toEmployeeWorkIntervalDto(interval);
							intervalDto.addHours(intervalHours);
							day.addInterval(intervalDto);
						}
					}
					day = new DayReportData();
					week.addInterval(day);
					d++;
				}
				for (WeekReportData weekData : payPeriod.getWeekReportData()) {
					payPeriod.addNormalPayHours(weekData.getHoursNormalPay());
					payPeriod.addOvertimeHours(weekData.getHoursOvertime());
				}
				reportData.add(payPeriod);
			}
		}
		return reportData;
	}
	
	private List<EmployeeWorkInterval> findIntervalsHelper(Employee employee, DateMidnight sundayBeforePayPeriodStart, int day) {
		return new EmployeeWorkIntervalHibernateDao().findEmployeeWorkIntervalsBetweenDates(employee, sundayBeforePayPeriodStart.plusDays(day).toDateTime(), sundayBeforePayPeriodStart.plusDays(day + 1).toDateTime());
	}
	
	private double getDifference(Date start, Date end) {
		Period period = new Period(new DateTime(start), new DateTime(end));
		return new Double(period.getHours()) + new Double(period.getMinutes())/60;
	}
}
