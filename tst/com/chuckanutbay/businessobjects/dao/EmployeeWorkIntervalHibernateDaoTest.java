package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneActivity;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneEmployee;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneEmployeeWorkInterval;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Activity;
import com.chuckanutbay.businessobjects.BusinessObjects;
import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.EmployeeWorkIntervalActivityPercentage;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see EmployeeWorkIntervalHibernateDao
 */
public class EmployeeWorkIntervalHibernateDaoTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final EmployeeWorkIntervalDao dao = new EmployeeWorkIntervalHibernateDao();
	
	/**
	 * @see EmployeeWorkIntervalDao#findEmployeeWorkIntervalsBetweenDates(Date date, Employee employee)
	 */
	@SuppressWarnings("unused")
	@Test
	public void testFindEmployeeWorkIntervalsSinceDate() {
		
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs");
		Employee employee2 = oneEmployee(1235, "Bill", "Gates");
		
		DateMidnight today = new DateMidnight();
		System.out.println("Day of the year today: " + today.getDayOfYear());
		DateMidnight dateToCheck = today.minusDays(3);
		System.out.println("Day of the year to check: " + dateToCheck.getDayOfYear());
		
		// Empty database
		assertEmpty(dao.findEmployeeWorkIntervalsBetweenDates(employee2, dateToCheck.toDateTime(), today.toDateTime()));
		
		// 1 in range and 2 out of range WorkInterval for 2 Employees
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, today.minusDays(5).toDate(), null);
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, today.minusDays(1).toDate(), null);
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, today.minusDays(5).toDate(), null);
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, today.minusDays(1).toDate(), null);
		EmployeeWorkInterval employee2WorkInterval3 = oneEmployeeWorkInterval(employee2, today.plusDays(1).toDate(), null);
		
		assertEquals(newArrayList(employee2WorkInterval2), dao.findEmployeeWorkIntervalsBetweenDates(employee2, dateToCheck.toDateTime(), today.toDateTime()));
		
	}
	
	/**
	 * @see EmployeeWorkIntervalDao#findOpenEmployeeWorkIntervals()
	 */
	
	@SuppressWarnings("unused")
	@Test
	public void testFindOpenEmployeeWorkIntervals() {
		// Empty database
		assertEmpty(dao.findOpenEmployeeWorkIntervals());
		
		// 1 Open and 1 Closed WorkInterval for 2 Employees
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs");
		Employee employee2 = oneEmployee(1235, "Bill", "Gates");
		
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, new Date(), new Date());
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, new Date(), null);
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, new Date(), new Date());
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, new Date(), null);
		
		assertEquals(newArrayList(employee1WorkInterval2, employee2WorkInterval2), dao.findOpenEmployeeWorkIntervals());
		
		// Close one interval so that there is only 1 open interval left
		employee2WorkInterval2.setEndDateTime(new Date());
		assertEquals(newArrayList(employee1WorkInterval2), dao.findOpenEmployeeWorkIntervals());
	}
	
	/**
	 * @see EmployeeWorkIntervalDao#findOpenEmployeeWorkInterval(Employee employee)
	 */

	@SuppressWarnings("unused")
	@Test
	public void testFindOpenEmployeeWorkInterval() {
		
		Employee employee1 = BusinessObjects.oneEmployee(1234, "Steve", "Jobs");
		Employee employee2 = BusinessObjects.oneEmployee(1235, "Bill", "Gates");
		
		// Empty database
		assertNull(dao.findOpenEmployeeWorkInterval(employee1));
		
		// 1 Open and 1 Closed WorkInterval for 2 Employees
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, new Date(), new Date());
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, new Date(), null);
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, new Date(), new Date());
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, new Date(), null);
		
		assertEquals(employee1WorkInterval2, dao.findOpenEmployeeWorkInterval(employee1));
		
		// Close one interval so that there is only 1 open interval left
		employee1WorkInterval2.setEndDateTime(new Date());
		assertNull(dao.findOpenEmployeeWorkInterval(employee1));
	}
	
	/**
	 * @see EmployeeWorkIntervalDao#findLastEnteredPercentages(Employee employee)
	 */
	@Test
	public void testFindLastEnteredPercentages() {
		
		Employee employee1 = BusinessObjects.oneEmployee(1234, "Steve", "Jobs");
		Employee employee2 = BusinessObjects.oneEmployee(1235, "Bill", "Gates");
		
		// Empty database
		assertNull(dao.findOpenEmployeeWorkInterval(employee1));
		
		// 1 Open and 1 Closed WorkInterval for 2 Employees
		EmployeeWorkInterval employee1WorkInterval1 = oneEmployeeWorkInterval(employee1, new Date(), new DateTime().minusDays(1).toDate());
		EmployeeWorkInterval employee1WorkInterval2 = oneEmployeeWorkInterval(employee1, new Date(), new Date());
		EmployeeWorkInterval employee2WorkInterval1 = oneEmployeeWorkInterval(employee2, new Date(), new DateTime().minusDays(1).toDate());
		EmployeeWorkInterval employee2WorkInterval2 = oneEmployeeWorkInterval(employee2, new Date(), new Date());
		
		Activity activity = oneActivity("Packaging");
		
		Set<EmployeeWorkIntervalActivityPercentage> percentage1 = BusinessObjects.oneEmployeeWorkIntervalActivityPercentage(employee1WorkInterval1, activity, 20);
		Set<EmployeeWorkIntervalActivityPercentage> percentage2 = BusinessObjects.oneEmployeeWorkIntervalActivityPercentage(employee1WorkInterval2, activity, 40);
		Set<EmployeeWorkIntervalActivityPercentage> percentage3 = BusinessObjects.oneEmployeeWorkIntervalActivityPercentage(employee2WorkInterval1, activity, 60);
		Set<EmployeeWorkIntervalActivityPercentage> percentage4 = BusinessObjects.oneEmployeeWorkIntervalActivityPercentage(employee2WorkInterval2, activity, 80);
		
		employee1WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentage1);
		employee1WorkInterval2.setEmployeeWorkIntervalActivityPercentages(percentage2);
		employee2WorkInterval1.setEmployeeWorkIntervalActivityPercentages(percentage3);
		employee2WorkInterval2.setEmployeeWorkIntervalActivityPercentages(percentage4);
		
		Assert.assertEquals(percentage2, dao.findLastEnteredPercentages(employee1));
	}
}
