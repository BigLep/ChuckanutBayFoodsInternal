package com.chuckanutbay.util.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.dao.EmployeeDao;
import com.chuckanutbay.businessobjects.dao.EmployeeHibernateDao;

/**
 * Test that {@link DatabaseResource} creates and tears down a database for each {@link Test}.
 * @see DatabaseResource
 */
public class DatabaseResourceTest {

	@Rule
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final EmployeeDao dao = new EmployeeHibernateDao();
	
	@Test
	public void test1() {
		createAndAssertEmployeeCreation();
	}
	
	/**
	 * If we aren't tearing down the database between {@link Test}s,
	 * then this will fail.
	 */
	@Test
	public void test2() {
		createAndAssertEmployeeCreation();
	}
	
	private void createAndAssertEmployeeCreation() {
		EmployeeDao dao = new EmployeeHibernateDao();
		assertEquals(0, dao.findAll().size());
		Employee employee = new Employee();
		employee.setFirstName("firstName");
		employee.setLastName("lastName");
		dao.makePersistent(employee);
		assertEquals(1, dao.findAll().size());
	}
	
}
