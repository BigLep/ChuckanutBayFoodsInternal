package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneEmployee;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
	 * @see EmployeeWorkIntervalDao#findEmployeesByShift()
	 */
	@Test
	public void testFindEmployeesByShift() {
		// Empty database
		assertEmpty(dao.findEmployeesByShift(2));
		
		// 4 Employees
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs", 1);
		Employee employee2 = oneEmployee(1235, "Bill", "Gates", 2);
		Employee employee3 = oneEmployee(1236, "Steve", "Wozniak", 2);
		Employee employee4 = oneEmployee(1237, "Thomas", "Watson", 2);
		
		assertEquals(newArrayList(employee2, employee4, employee3), dao.findEmployeesByShift(2));
	}
	
	/**
	 * @see EmployeeWorkIntervalDao#findEmployeeWithBarcodeNumber()
	 */
	@Test
	public void testFindEmployeeWithBarcodeNumber() {
		// Empty database
		assertNull(dao.findEmployeeWithBarcodeNumber(123456789));
		
		// 1 Open and 1 Closed WorkInterval for 2 Employees
		Employee employee1 = oneEmployee(1234, "Steve", "Jobs");
		Employee employee2 = oneEmployee(1235, "Bill", "Gates");
		
		assertEquals(employee1, dao.findEmployeeWithBarcodeNumber(new Integer(1234)));
	}
	
	
}
