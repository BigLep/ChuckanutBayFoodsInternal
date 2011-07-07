package com.chuckanutbay.businessobjects.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see EmployeeWorkIntervalHibernateDao
 */
public class EmployeeHibernateDaoTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final EmployeeDao dao = new EmployeeHibernateDao();
	
	
	/**
	 * @see EmployeeWorkIntervalDao#findOpenEmployeeWorkIntervals()
	 */
	@Test
	public void testFindEmployeeWithBarcodeNumber() {
		
		System.out.println("Trying empty db");
		
		// Empty database
		assertEquals(null, dao.findEmployeeWithBarcodeNumber(123456789));
		
		System.out.println("Creating the employees");
		
		// 1 Open and 1 Closed WorkInterval for 2 Employees
		Employee employee1 = new Employee();
		employee1.setBarcodeNumber(123456789);
		employee1.setFirstName("Steve");
		employee1.setLastName("Jobs");
		dao.makePersistent(employee1);
		
		Employee employee2 = new Employee();
		employee2.setBarcodeNumber(234567890);
		employee2.setFirstName("Bill");
		employee2.setLastName("Gates");
		dao.makePersistent(employee2);
		
		System.out.println("Persisted the employees");
		
		assertEquals(employee1, dao.findEmployeeWithBarcodeNumber(new Integer(123456789)));
	}
	
	
}
