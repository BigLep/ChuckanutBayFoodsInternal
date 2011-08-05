package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneActivity;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneEmployee;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneEmployeeWorkInterval;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneEmployeeWorkIntervalActivityPercentage;
import static com.chuckanutbay.util.testing.AssertExtensions.assertDateEquals;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.chuckanutbay.util.testing.AssertExtensions.assertSize;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Activity;
import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.dao.ActivityDao;
import com.chuckanutbay.businessobjects.dao.ActivityHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeDao;
import com.chuckanutbay.businessobjects.dao.EmployeeHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalActivityPercentageHibernateDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalDao;
import com.chuckanutbay.businessobjects.dao.EmployeeWorkIntervalHibernateDao;
import com.chuckanutbay.util.testing.DatabaseResource;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.chuckanutbay.webapp.common.shared.IntervalDto;

/**
 * @see EmployeeWorkIntervalHibernateDao
 */
public class TimeClockServiceImplTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();

	/**
	 *  @see TimeClockServiceImpl#clockOut(EmployeeDto employeeDto)
	 */
	@Test
	public void testClockOut() {
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		EmployeeWorkIntervalActivityPercentageDao percentageDao = new EmployeeWorkIntervalActivityPercentageHibernateDao();
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		
		//1 Open and 1 Closed interval for two employees
		Activity activity = oneActivity("Packaging");
		
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs", 1);
		Employee employee2 = oneEmployee(1235, "Bill", "Gates", 2);
		
		EmployeeDto employee1Dto = DtoUtils.toEmployeeDtoFunction.apply(employee1);
		EmployeeWorkIntervalActivityPercentageDto percentageDto = new EmployeeWorkIntervalActivityPercentageDto();
		percentageDto.setActivity(DtoUtils.toActivityDtoFunction.apply(activity));
		percentageDto.setPercentage(30);
		List<EmployeeWorkIntervalActivityPercentageDto> percentages = newArrayList(percentageDto);
		employee1Dto.setEmployeeWorkIntervalPercentages(percentages);
		
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, new Date(), new Date());
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, new Date(), null);
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, new Date(), new Date());
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, new Date(), null);
		
		assertEquals(newArrayList(employee1WorkInterval2, employee2WorkInterval2), intervalDao.findOpenEmployeeWorkIntervals());
		
		//Cancel 1 Open interval
		server.clockOut(employee1Dto);
		System.out.println("successful sign out");
		assertSize(percentageDao.findAll(), 1);
		assertEquals(newArrayList(employee2WorkInterval2), intervalDao.findOpenEmployeeWorkIntervals());
		assertSize(intervalDao.findAll(), 4);
	}
	/**
	 * @see TimeClockServiceImpl#getLastPayPeriodIntervalFromServer()
	 */
	@Test
	public void testGetLastPayPeriodIntervalFromServer() {
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		IntervalDto interval = server.getLastPayPeriodIntervalFromServer(getDate(6,1));
		assertDateEquals(interval.getStart(), 5, 16);
		assertDateEquals(interval.getEnd(), 5, 31);
		
		interval = server.getLastPayPeriodIntervalFromServer(getDate(6,15));
		assertDateEquals(interval.getStart(), 5, 16);
		assertDateEquals(interval.getEnd(), 5, 31);
	}
	
	private Date getDate(int month, int day) {
		DateMidnight dm = new DateMidnight();
		dm = dm.withMonthOfYear(month);
		dm = dm.withDayOfMonth(day);
		return dm.toDate();
	}
	/**
	 * @see TimeClockServiceImpl#getActivities()
	 */
	@Test
	public void testGetActivites() {
		ActivityDao activityDao = new ActivityHibernateDao();
		
		//Empty Database
		assertEmpty(activityDao.findAll());
		
		//2 Activites
		Activity activity1 = oneActivity("Packaging");
		Activity activity2 = oneActivity("Production");
		assertEquals(newArrayList(activity1, activity2), activityDao.findAll());
		
		//Remove 1
		activityDao.makeTransient(activity1);
		assertEquals(newArrayList(activity2), activityDao.findAll());
		
	}
	
	/**
	 * @see TimeClockServiceImpl#cancelClockIn(Barcode barcode)
	 */
	@Test
	public void testCancelClockIn() {
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		
		//1 Open and 1 Closed interval for two employees
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs", 1);
		Employee employee2 = oneEmployee(1235, "Bill", "Gates", 2);
		
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, new Date(), new Date());
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, new Date(), null);
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, new Date(), new Date());
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, new Date(), null);
		
		assertEquals(newArrayList(employee2WorkInterval2, employee1WorkInterval2), intervalDao.findOpenEmployeeWorkIntervals());
		
		//Cancel 1 Open interval
		server.cancelClockIn(1234);
		assertEquals(newArrayList(employee2WorkInterval1), intervalDao.findOpenEmployeeWorkIntervals());
		assertSize(intervalDao.findAll(), 3);
	}
	
	
	/**
	 * Test should not be run on a Monday
	 * @see TimeClockServiceImpl#clockIn(Barcode barcode)
	 */
	@Test
	public void testClockIn() {
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		
		// Empty database
		assertEquals(null, server.clockIn(1235));
		assertEquals(newArrayList(), intervalDao.findOpenEmployeeWorkIntervals());
		
		// A 1 closed interval earlier and 1 since Sunday for each employee
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs", 1);
		Employee employee2 = oneEmployee(1235, "Bill", "Gates", 2);
		
		DateMidnight dm = new DateMidnight().minusDays(8);
		DateTime dt = new DateTime(dm).plus(500000);
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, dm.toDate(), dt.toDate());
		
		dm = new DateMidnight().minusDays(1);
		dt = new DateTime(dm).plus(200000);
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, dm.toDate(), dt.toDate());
		
		dm = new DateMidnight().minusDays(8);
		dt = new DateTime(dm).plus(500000);
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, dm.toDate(), dt.toDate());

		dm = new DateMidnight().minusDays(1);
		dt = new DateTime(dm).plus(200000);
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, dm.toDate(), dt.toDate());
		
		Activity activity = oneActivity("Packaging");
		
		employee1WorkInterval1.setEmployeeWorkIntervalActivityPercentages(oneEmployeeWorkIntervalActivityPercentage(employee1WorkInterval1, activity, 10));
		employee1WorkInterval2.setEmployeeWorkIntervalActivityPercentages(oneEmployeeWorkIntervalActivityPercentage(employee1WorkInterval2, activity, 20));
		employee2WorkInterval1.setEmployeeWorkIntervalActivityPercentages(oneEmployeeWorkIntervalActivityPercentage(employee2WorkInterval1, activity, 30));
		employee2WorkInterval2.setEmployeeWorkIntervalActivityPercentages(oneEmployeeWorkIntervalActivityPercentage(employee2WorkInterval2, activity, 40));
		
		EmployeeDto clockedInEmployee = server.clockIn(1235);
		assertEquals(3, clockedInEmployee.getMinsWorkedThisWeek());
		assertEquals("Bill", clockedInEmployee.getFirstName());
		assertSize(intervalDao.findOpenEmployeeWorkIntervals(), 1);
	}
	
	/**
	 * @see TimeClockServiceImpl#getClockedInEmployees()
	 */
	
	@Test
	public void testGetClockedInEmployees() {
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		ActivityDao activityDao = new ActivityHibernateDao();
		EmployeeWorkIntervalActivityPercentageDao percentageDao = new EmployeeWorkIntervalActivityPercentageHibernateDao();
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		
		// Empty database
		assertEquals(newArrayList(), new ArrayList<EmployeeDto>(server.getClockedInEmployees()));
		
		// A 1 closed interval earlier and 1 since Sunday for each employee
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs", 1);
		Employee employee2 = oneEmployee(1235, "Bill", "Gates", 2);
		
		EmployeeDto employee1Dto = DtoUtils.toEmployeeDtoFunction.apply(employee1);
		EmployeeDto employee2Dto = DtoUtils.toEmployeeDtoFunction.apply(employee2);
		
		DateMidnight dm = new DateMidnight().minusDays(8);
		DateTime dt = new DateTime(dm).plus(500000);
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, dm.toDate(), dt.toDate());
		
		dm = new DateMidnight().minusDays(1);
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, dm.toDate(), null);
		
		dm = new DateMidnight().minusDays(8);
		dt = new DateTime(dm).plus(500000);
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, dm.toDate(), dt.toDate());

		dm = new DateMidnight().minusDays(1);
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, dm.toDate(), null);
		
		Activity activity = oneActivity("Packaging");
		
		employee1WorkInterval1.setEmployeeWorkIntervalActivityPercentages(oneEmployeeWorkIntervalActivityPercentage(employee1WorkInterval1, activity, 10));
		employee2WorkInterval1.setEmployeeWorkIntervalActivityPercentages(oneEmployeeWorkIntervalActivityPercentage(employee2WorkInterval1, activity, 30));
		assertEquals(newArrayList(employee2Dto, employee1Dto), new ArrayList<EmployeeDto>(server.getClockedInEmployees()));
	}
	 
}
