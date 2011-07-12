package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;
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
	 * @see EmployeeWorkIntervalDao#findEmployeesByShift()
	 */
	@Test
	public void testFindEmployeesByShift() {
		
		System.out.println("Trying empty db");
		
		// Empty database
		assertEquals(newArrayList(), dao.findEmployeesByShift(2));
		
		System.out.println("Creating the employees");
		
		// 4 Employees
		Employee employee1 = new Employee();
		employee1.setBarcodeNumber(123456789);
		employee1.setFirstName("Steve");
		employee1.setLastName("Jobs");
		employee1.setShift(1);
		dao.makePersistent(employee1);
		
		Employee employee2 = new Employee();
		employee2.setBarcodeNumber(123456789);
		employee2.setFirstName("Bill");
		employee2.setLastName("Gates");
		employee2.setShift(2);
		dao.makePersistent(employee2);
		
		Employee employee3 = new Employee();
		employee3.setBarcodeNumber(123456789);
		employee3.setFirstName("Steve");
		employee3.setLastName("Wozniak");
		employee3.setShift(2);
		dao.makePersistent(employee3);
		
		Employee employee4 = new Employee();
		employee4.setBarcodeNumber(123456789);
		employee4.setFirstName("Thomas");
		employee4.setLastName("Watson");
		employee4.setShift(2);
		dao.makePersistent(employee4);
		
		System.out.println("Persisted the employees");
		
		assertEquals(newArrayList(employee2, employee4, employee3), dao.findEmployeesByShift(2));
	}
	
	/**
	 * @see EmployeeWorkIntervalDao#findEmployeeWithBarcodeNumber()
	 */
	/**
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
	}*/
	
	
}
