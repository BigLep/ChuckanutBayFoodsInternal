package com.chuckanutbay.webapp.common.server;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Activity;
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
import com.chuckanutbay.util.testing.DatabaseResource;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;

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
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		ActivityDao activityDao = new ActivityHibernateDao();
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		EmployeeWorkIntervalActivityPercentageDao percentageDao = new EmployeeWorkIntervalActivityPercentageHibernateDao();
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		
		//1 Open and 1 Closed interval for two employees
		Employee employee1 = new Employee();
		employee1.setBarcodeNumber(123456789);
		employee1.setFirstName("Steve");
		employee1.setLastName("Jobs");
		employeeDao.makePersistent(employee1);
		
		Activity activity = new Activity();
		activity.setName("Packaging");
		activityDao.makePersistent(activity);
		
		Employee employee2 = new Employee();
		employee2.setBarcodeNumber(234567890);
		employee2.setFirstName("Bill");
		employee2.setLastName("Gates");
		employeeDao.makePersistent(employee2);
		
		EmployeeDto employee1Dto = DtoUtils.toEmployeeDtoFunction.apply(employee1);
		EmployeeWorkIntervalActivityPercentageDto percentageDto = new EmployeeWorkIntervalActivityPercentageDto();
		percentageDto.setActivity(DtoUtils.toActivityDtoFunction.apply(activity));
		percentageDto.setPercentage(30);
		List<EmployeeWorkIntervalActivityPercentageDto> percentages = newArrayList(percentageDto);
		employee1Dto.setEmployeeWorkIntervalPercentages(percentages);
		
		EmployeeWorkInterval employee1WorkInterval1 = new EmployeeWorkInterval();
		employee1WorkInterval1.setEmployee(employee1);
		employee1WorkInterval1.setStartDateTime(new Date());
		employee1WorkInterval1.setEndDateTime(new Date());
		intervalDao.makePersistent(employee1WorkInterval1);
		
		EmployeeWorkInterval employee1WorkInterval2 = new EmployeeWorkInterval();
		employee1WorkInterval2.setEmployee(employee1);
		employee1WorkInterval2.setStartDateTime(new Date());
		intervalDao.makePersistent(employee1WorkInterval2);
		
		EmployeeWorkInterval employee2WorkInterval1 = new EmployeeWorkInterval();
		employee2WorkInterval1.setEmployee(employee2);
		employee2WorkInterval1.setStartDateTime(new Date());
		employee2WorkInterval1.setEndDateTime(new Date());
		intervalDao.makePersistent(employee2WorkInterval1);
		
		EmployeeWorkInterval employee2WorkInterval2 = new EmployeeWorkInterval();
		employee2WorkInterval2.setEmployee(employee2);
		employee2WorkInterval2.setStartDateTime(new Date());
		intervalDao.makePersistent(employee2WorkInterval2);
		
		/**
		EmployeeWorkIntervalActivityPercentage percentage1 = new EmployeeWorkIntervalActivityPercentage();
		percentage1.setActivity(activity);
		percentage1.setPercentage(10);
		percentage1.setEmployeeWorkInterval(employee1WorkInterval1);
		percentageDao.makePersistent(percentage1);
		Set<EmployeeWorkIntervalActivityPercentage> percentages1 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages1.add(percentage1);
		employee1WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentages1);
		
		EmployeeWorkIntervalActivityPercentage percentage3 = new EmployeeWorkIntervalActivityPercentage();
		percentage3.setActivity(activity);
		percentage3.setPercentage(30);
		percentage3.setEmployeeWorkInterval(employee2WorkInterval1);
		percentageDao.makePersistent(percentage3);
		Set<EmployeeWorkIntervalActivityPercentage> percentages3 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages3.add(percentage3);
		employee2WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentages3);
		*/
		
		assertEquals(newArrayList(employee1WorkInterval2, employee2WorkInterval2), intervalDao.findOpenEmployeeWorkIntervals());
		
		//Cancel 1 Open interval
		server.clockOut(employee1Dto);
		System.out.println("successful sign out");
		assertEquals(1, percentageDao.findAll().size());
		assertEquals(newArrayList(employee2WorkInterval2), intervalDao.findOpenEmployeeWorkIntervals());
		assertEquals(4, intervalDao.findAll().size());
	}
	/**
	 * @see TimeClockServiceImpl#getStartOfLastPayPeriod()
	 */
	@Test
	public void testGetStartOfLastPayPeriod() {
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		DateTime dt = new DateTime(server.getStartOfLastPayPeriodFromServer());
		assertEquals(dt.getMonthOfYear(), 6);
		assertEquals(dt.getDayOfMonth(), 16);
	}
	/**
	 * @see TimeClockServiceImpl#getEndOfLastPayPeriod()
	 */
	@Test
	public void testGetEndOfLastPayPeriod() {
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		DateTime dt = new DateTime(server.getEndOfLastPayPeriodFromServer());
		assertEquals(dt.getMonthOfYear(), 6);
		assertEquals(dt.getDayOfMonth(), 30);
	}
	/**
	 * @see TimeClockServiceImpl#getActivities()
	 */
	@Test
	public void testGetActivites() {
		ActivityDao activityDao = new ActivityHibernateDao();
		
		//Empty Database
		assertEquals(newArrayList(), activityDao.findAll());
		
		//2 Activites
		Activity activity1 = new Activity();
		activity1.setName("Packaging");
		activityDao.makePersistent(activity1);
		
		Activity activity2 = new Activity();
		activity2.setName("Production");
		activityDao.makePersistent(activity2);
		
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
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		
		//1 Open and 1 Closed interval for two employees
		Employee employee1 = new Employee();
		employee1.setBarcodeNumber(123456789);
		employee1.setFirstName("Steve");
		employee1.setLastName("Jobs");
		employeeDao.makePersistent(employee1);
		
		Employee employee2 = new Employee();
		employee2.setBarcodeNumber(234567890);
		employee2.setFirstName("Bill");
		employee2.setLastName("Gates");
		employeeDao.makePersistent(employee2);
		
		EmployeeWorkInterval employee1WorkInterval1 = new EmployeeWorkInterval();
		employee1WorkInterval1.setEmployee(employee1);
		employee1WorkInterval1.setStartDateTime(new Date());
		employee1WorkInterval1.setEndDateTime(new Date());
		intervalDao.makePersistent(employee1WorkInterval1);
		
		EmployeeWorkInterval employee1WorkInterval2 = new EmployeeWorkInterval();
		employee1WorkInterval2.setEmployee(employee1);
		employee1WorkInterval2.setStartDateTime(new Date());
		intervalDao.makePersistent(employee1WorkInterval2);
		
		EmployeeWorkInterval employee2WorkInterval1 = new EmployeeWorkInterval();
		employee2WorkInterval1.setEmployee(employee2);
		employee2WorkInterval1.setStartDateTime(new Date());
		employee2WorkInterval1.setEndDateTime(new Date());
		intervalDao.makePersistent(employee2WorkInterval1);
		
		EmployeeWorkInterval employee2WorkInterval2 = new EmployeeWorkInterval();
		employee2WorkInterval2.setEmployee(employee2);
		employee2WorkInterval2.setStartDateTime(new Date());
		intervalDao.makePersistent(employee2WorkInterval2);
		assertEquals(newArrayList(employee2WorkInterval2, employee1WorkInterval2), intervalDao.findOpenEmployeeWorkIntervals());
		
		//Cancel 1 Open interval
		server.cancelClockIn(123456789);
		System.out.println("successful clock in cancel");
		assertEquals(newArrayList(employee2WorkInterval1), intervalDao.findOpenEmployeeWorkIntervals());
		assertEquals(3, intervalDao.findAll().size());
	}
	
	
	/**
	 * Test should not be run on a Monday
	 * @see TimeClockServiceImpl#clockIn(Barcode barcode)
	 */
	@Test
	public void testClockIn() {
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		EmployeeWorkIntervalDao intervalDao = new EmployeeWorkIntervalHibernateDao();
		ActivityDao activityDao = new ActivityHibernateDao();
		EmployeeWorkIntervalActivityPercentageDao percentageDao = new EmployeeWorkIntervalActivityPercentageHibernateDao();
		TimeClockServiceImpl server = new TimeClockServiceImpl();
		
		// Empty database
		assertEquals(null, server.clockIn(234567890));
		assertEquals(newArrayList(), intervalDao.findOpenEmployeeWorkIntervals());
		
		// A 1 closed interval earlier and 1 since Sunday for each employee
		Employee employee1 = new Employee();
		employee1.setBarcodeNumber(123456789);
		employee1.setFirstName("Steve");
		employee1.setLastName("Jobs");
		employeeDao.makePersistent(employee1);
		
		Employee employee2 = new Employee();
		employee2.setBarcodeNumber(234567890);
		employee2.setFirstName("Bill");
		employee2.setLastName("Gates");
		employeeDao.makePersistent(employee2);
		
		EmployeeWorkInterval employee1WorkInterval1 = new EmployeeWorkInterval();
		employee1WorkInterval1.setEmployee(employee1);
		DateMidnight dm1 = new DateMidnight();
		dm1 = dm1.minusDays(8);
		System.out.println("Steve's First Interval: " + dm1.getDayOfYear());
		employee1WorkInterval1.setStartDateTime(dm1.toDate());
		DateTime dt1 = new DateTime(dm1);
		dt1 = dt1.plus(500000);
		employee1WorkInterval1.setEndDateTime(dt1.toDate());
		intervalDao.makePersistent(employee1WorkInterval1);
		
		EmployeeWorkInterval employee1WorkInterval2 = new EmployeeWorkInterval();
		employee1WorkInterval2.setEmployee(employee1);
		DateMidnight dm2 = new DateMidnight();
		dm2 = dm2.minusDays(1);
		System.out.println("Steve's Second Interval: " + dm2.getDayOfYear());
		employee1WorkInterval2.setStartDateTime(dm2.toDate());
		DateTime dt2 = new DateTime(dm2);
		dt2 = dt2.plus(200000);
		employee1WorkInterval2.setEndDateTime(dt2.toDate());
		intervalDao.makePersistent(employee1WorkInterval2);
		
		EmployeeWorkInterval employee2WorkInterval1 = new EmployeeWorkInterval();
		employee2WorkInterval1.setEmployee(employee2);
		DateMidnight dm3 = new DateMidnight();
		dm3 = dm3.minusDays(8);
		System.out.println("Bill's First Interval: " + dm3.getDayOfYear());
		employee2WorkInterval1.setStartDateTime(dm3.toDate());
		DateTime dt3 = new DateTime(dm3);
		dt3 = dt3.plus(500000);
		employee2WorkInterval1.setEndDateTime(dt3.toDate());
		intervalDao.makePersistent(employee2WorkInterval1);
		
		EmployeeWorkInterval employee2WorkInterval2 = new EmployeeWorkInterval();
		employee2WorkInterval2.setEmployee(employee2);
		DateMidnight dm4 = new DateMidnight();
		dm4 = dm4.minusDays(1);
		System.out.println("Bill's Second Interval: " + dm4.getDayOfYear());
		employee2WorkInterval2.setStartDateTime(dm4.toDate());
		DateTime dt4 = new DateTime(dm4);
		dt4 = dt4.plus(200000);
		employee2WorkInterval2.setEndDateTime(dt4.toDate());
		intervalDao.makePersistent(employee2WorkInterval2);
		
		Activity activity = new Activity();
		activity.setName("Packaging");
		activityDao.makePersistent(activity);
		
		EmployeeWorkIntervalActivityPercentage percentage1 = new EmployeeWorkIntervalActivityPercentage();
		percentage1.setActivity(activity);
		percentage1.setPercentage(10);
		percentage1.setEmployeeWorkInterval(employee1WorkInterval1);
		percentageDao.makePersistent(percentage1);
		Set<EmployeeWorkIntervalActivityPercentage> percentages1 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages1.add(percentage1);
		employee1WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentages1);
		
		EmployeeWorkIntervalActivityPercentage percentage2 = new EmployeeWorkIntervalActivityPercentage();
		percentage2.setActivity(activity);
		percentage2.setPercentage(20);
		percentage2.setEmployeeWorkInterval(employee1WorkInterval2);
		percentageDao.makePersistent(percentage2);
		Set<EmployeeWorkIntervalActivityPercentage> percentages2 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages2.add(percentage2);
		employee1WorkInterval2.setEmployeeWorkIntervalActivityPercentages(percentages2);
		
		EmployeeWorkIntervalActivityPercentage percentage3 = new EmployeeWorkIntervalActivityPercentage();
		percentage3.setActivity(activity);
		percentage3.setPercentage(30);
		percentage3.setEmployeeWorkInterval(employee2WorkInterval1);
		percentageDao.makePersistent(percentage3);
		Set<EmployeeWorkIntervalActivityPercentage> percentages3 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages3.add(percentage3);
		employee2WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentages3);
		
		EmployeeWorkIntervalActivityPercentage percentage4 = new EmployeeWorkIntervalActivityPercentage();
		percentage4.setActivity(activity);
		percentage4.setPercentage(40);
		percentage4.setEmployeeWorkInterval(employee2WorkInterval2);
		percentageDao.makePersistent(percentage4);
		Set<EmployeeWorkIntervalActivityPercentage> percentages4 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages4.add(percentage4);
		employee2WorkInterval2.setEmployeeWorkIntervalActivityPercentages(percentages4);
		
		System.out.println("There are this many intervals: " + intervalDao.findAll().size());
		
		for (EmployeeWorkInterval interval : intervalDao.findAll()) {
			System.out.println("There is an interval on the database with " + interval.getEmployeeWorkIntervalActivityPercentages().size() + " percentages");
		}
		
		EmployeeDto clockedInEmployee = server.clockIn(234567890);
		assertEquals(3, clockedInEmployee.getMinsWorkedThisWeek());
		assertEquals("Bill", clockedInEmployee.getFirstName());
		assertEquals(1, intervalDao.findOpenEmployeeWorkIntervals().size());
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
		Employee employee1 = new Employee();
		employee1.setBarcodeNumber(123456789);
		employee1.setFirstName("Steve");
		employee1.setLastName("Jobs");
		employeeDao.makePersistent(employee1);
		EmployeeDto employee1Dto = DtoUtils.toEmployeeDtoFunction.apply(employee1);
		
		Employee employee2 = new Employee();
		employee2.setBarcodeNumber(234567890);
		employee2.setFirstName("Bill");
		employee2.setLastName("Gates");
		employeeDao.makePersistent(employee2);
		EmployeeDto employee2Dto = DtoUtils.toEmployeeDtoFunction.apply(employee2);
		
		EmployeeWorkInterval employee1WorkInterval1 = new EmployeeWorkInterval();
		employee1WorkInterval1.setEmployee(employee1);
		DateMidnight dm1 = new DateMidnight();
		dm1 = dm1.minusDays(8);
		System.out.println("Steve's First Interval: " + dm1.getDayOfYear());
		employee1WorkInterval1.setStartDateTime(dm1.toDate());
		DateTime dt1 = new DateTime(dm1);
		dt1 = dt1.plus(500000);
		employee1WorkInterval1.setEndDateTime(dt1.toDate());
		intervalDao.makePersistent(employee1WorkInterval1);
		
		EmployeeWorkInterval employee1WorkInterval2 = new EmployeeWorkInterval();
		employee1WorkInterval2.setEmployee(employee1);
		DateMidnight dm2 = new DateMidnight();
		dm2 = dm2.minusDays(1);
		System.out.println("Steve's Second Interval: " + dm2.getDayOfYear());
		employee1WorkInterval2.setStartDateTime(dm2.toDate());
		intervalDao.makePersistent(employee1WorkInterval2);
		
		EmployeeWorkInterval employee2WorkInterval1 = new EmployeeWorkInterval();
		employee2WorkInterval1.setEmployee(employee2);
		DateMidnight dm3 = new DateMidnight();
		dm3 = dm3.minusDays(8);
		System.out.println("Bill's First Interval: " + dm3.getDayOfYear());
		employee2WorkInterval1.setStartDateTime(dm3.toDate());
		DateTime dt3 = new DateTime(dm3);
		dt3 = dt3.plus(500000);
		employee2WorkInterval1.setEndDateTime(dt3.toDate());
		intervalDao.makePersistent(employee2WorkInterval1);
		
		EmployeeWorkInterval employee2WorkInterval2 = new EmployeeWorkInterval();
		employee2WorkInterval2.setEmployee(employee2);
		DateMidnight dm4 = new DateMidnight();
		dm4 = dm4.minusDays(1);
		System.out.println("Bill's Second Interval: " + dm4.getDayOfYear());
		employee2WorkInterval2.setStartDateTime(dm4.toDate());
		intervalDao.makePersistent(employee2WorkInterval2);
		
		Activity activity = new Activity();
		activity.setName("Packaging");
		activityDao.makePersistent(activity);
		
		EmployeeWorkIntervalActivityPercentage percentage1 = new EmployeeWorkIntervalActivityPercentage();
		percentage1.setActivity(activity);
		percentage1.setPercentage(10);
		percentage1.setEmployeeWorkInterval(employee1WorkInterval1);
		percentageDao.makePersistent(percentage1);
		Set<EmployeeWorkIntervalActivityPercentage> percentages1 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages1.add(percentage1);
		employee1WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentages1);
		
		EmployeeWorkIntervalActivityPercentage percentage3 = new EmployeeWorkIntervalActivityPercentage();
		percentage3.setActivity(activity);
		percentage3.setPercentage(30);
		percentage3.setEmployeeWorkInterval(employee2WorkInterval1);
		percentageDao.makePersistent(percentage3);
		Set<EmployeeWorkIntervalActivityPercentage> percentages3 = new HashSet<EmployeeWorkIntervalActivityPercentage>();
		percentages3.add(percentage3);
		employee2WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentages3);
		
		assertEquals(newArrayList(employee2Dto, employee1Dto), new ArrayList<EmployeeDto>(server.getClockedInEmployees()));
	}
	 
}
