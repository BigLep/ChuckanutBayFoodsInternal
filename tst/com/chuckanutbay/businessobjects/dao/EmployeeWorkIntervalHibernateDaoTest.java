package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see EmployeeWorkIntervalHibernateDao
 */
public class EmployeeWorkIntervalHibernateDaoTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final EmployeeWorkIntervalDao dao = new EmployeeWorkIntervalHibernateDao();
	
	
	/**
	 * @see EmployeeWorkIntervalDao#findOpenEmployeeWorkIntervals()
	 */
	
	@Test
	public void testFindOpenEmployeeWorkIntervals() {
		// Empty database
		assertEquals(newArrayList(), dao.findOpenEmployeeWorkIntervals());
		
		// 1 Open and 1 Closed WorkInterval for 2 Employees
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		
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
		dao.makePersistent(employee1WorkInterval1);
		
		EmployeeWorkInterval employee1WorkInterval2 = new EmployeeWorkInterval();
		employee1WorkInterval2.setEmployee(employee1);
		employee1WorkInterval2.setStartDateTime(new Date());
		dao.makePersistent(employee1WorkInterval2);
		
		EmployeeWorkInterval employee2WorkInterval1 = new EmployeeWorkInterval();
		employee2WorkInterval1.setEmployee(employee2);
		employee2WorkInterval1.setStartDateTime(new Date());
		employee2WorkInterval1.setEndDateTime(new Date());
		dao.makePersistent(employee2WorkInterval1);
		
		EmployeeWorkInterval employee2WorkInterval2 = new EmployeeWorkInterval();
		employee2WorkInterval2.setEmployee(employee2);
		employee2WorkInterval2.setStartDateTime(new Date());
		dao.makePersistent(employee2WorkInterval2);
		
		assertEquals(newArrayList(employee2WorkInterval2, employee1WorkInterval2), dao.findOpenEmployeeWorkIntervals());
		
		// Close one interval so that there is only 1 open interval left
		employee2WorkInterval2.setEndDateTime(new Date());
		assertEquals(newArrayList(employee1WorkInterval2), dao.findOpenEmployeeWorkIntervals());
	}
	
	/**
	 * @see EmployeeWorkIntervalDao#findOpenEmployeeWorkInterval(Employee employee)
	 */

	@Test
	public void testFindOpenEmployeeWorkInterval() {
		
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		
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
		
		// Empty database
		assertEquals(null, dao.findOpenEmployeeWorkInterval(employee1));
		
		// 1 Open and 1 Closed WorkInterval for 2 Employees
		EmployeeWorkInterval employee1WorkInterval1 = new EmployeeWorkInterval();
		employee1WorkInterval1.setEmployee(employee1);
		employee1WorkInterval1.setStartDateTime(new Date());
		employee1WorkInterval1.setEndDateTime(new Date());
		dao.makePersistent(employee1WorkInterval1);
		
		EmployeeWorkInterval employee1WorkInterval2 = new EmployeeWorkInterval();
		employee1WorkInterval2.setEmployee(employee1);
		employee1WorkInterval2.setStartDateTime(new Date());
		dao.makePersistent(employee1WorkInterval2);
		
		EmployeeWorkInterval employee2WorkInterval1 = new EmployeeWorkInterval();
		employee2WorkInterval1.setEmployee(employee2);
		employee2WorkInterval1.setStartDateTime(new Date());
		employee2WorkInterval1.setEndDateTime(new Date());
		dao.makePersistent(employee2WorkInterval1);
		
		EmployeeWorkInterval employee2WorkInterval2 = new EmployeeWorkInterval();
		employee2WorkInterval2.setEmployee(employee2);
		employee2WorkInterval2.setStartDateTime(new Date());
		dao.makePersistent(employee2WorkInterval2);
		
		assertEquals(employee1WorkInterval2, dao.findOpenEmployeeWorkInterval(employee1));
		
		// Close one interval so that there is only 1 open interval left
		employee1WorkInterval2.setEndDateTime(new Date());
		assertEquals(null, dao.findOpenEmployeeWorkInterval(employee1));
	}
	
	/**
	 * @see EmployeeWorkIntervalDao#findEmployeeWorkIntervalsSinceDate(Date date, Employee employee)
	 */
	@Test
	public void testFindEmployeeWorkIntervalsSinceDate() {
		
		EmployeeDao employeeDao = new EmployeeHibernateDao();
		
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

		DateMidnight dateMidnightToCheck = new DateMidnight();
		dateMidnightToCheck = dateMidnightToCheck.minusDays(3);
		System.out.println("Day of the year to check: " + dateMidnightToCheck.getDayOfYear());
		Date dateToCheck = dateMidnightToCheck.toDate();
		
		// Empty database
		assertEquals(newArrayList(), dao.findEmployeeWorkIntervalsSinceDate(dateToCheck, employee1));
		
		// 1 in range and 1 out of range WorkInterval for 2 Employees
		EmployeeWorkInterval employee1WorkInterval1 = new EmployeeWorkInterval();
		employee1WorkInterval1.setEmployee(employee1);
		DateMidnight dm1 = new DateMidnight();
		dm1 = dm1.minusDays(5);
		System.out.println("Steve's First Interval: " + dm1.getDayOfYear());
		employee1WorkInterval1.setStartDateTime(dm1.toDate());
		dao.makePersistent(employee1WorkInterval1);
		
		EmployeeWorkInterval employee1WorkInterval2 = new EmployeeWorkInterval();
		employee1WorkInterval2.setEmployee(employee1);
		DateMidnight dm2 = new DateMidnight();
		dm2 = dm2.minusDays(1);
		System.out.println("Steve's Second Interval: " + dm2.getDayOfYear());
		employee1WorkInterval2.setStartDateTime(dm2.toDate());
		dao.makePersistent(employee1WorkInterval2);
		
		EmployeeWorkInterval employee2WorkInterval1 = new EmployeeWorkInterval();
		employee2WorkInterval1.setEmployee(employee2);
		DateMidnight dm3 = new DateMidnight();
		dm3 = dm3.minusDays(5);
		System.out.println("Bill's First Interval: " + dm3.getDayOfYear());
		employee2WorkInterval1.setStartDateTime(dm3.toDate());
		dao.makePersistent(employee2WorkInterval1);
		
		EmployeeWorkInterval employee2WorkInterval2 = new EmployeeWorkInterval();
		employee2WorkInterval2.setEmployee(employee2);
		DateMidnight dm4 = new DateMidnight();
		dm4 = dm4.minusDays(1);
		System.out.println("Bill's Second Interval: " + dm4.getDayOfYear());
		employee2WorkInterval2.setStartDateTime(dm4.toDate());
		dao.makePersistent(employee2WorkInterval2);
		
	
		assertEquals(newArrayList(employee1WorkInterval2), dao.findEmployeeWorkIntervalsSinceDate(dateToCheck, employee1));
		
	}
	
}
