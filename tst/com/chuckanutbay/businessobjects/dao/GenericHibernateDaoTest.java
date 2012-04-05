package com.chuckanutbay.businessobjects.dao;

import static org.junit.Assert.fail;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.util.HibernateUtil;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see GenericHibernateDao
 */
public class GenericHibernateDaoTest {
	
	@Rule
	public final DatabaseResource databaseResource = new DatabaseResource();

	/**
	 * This test demonstrates the failure case of {@link GenericHibernateDao#makePersistent(Object}}.
	 * This failure occurs because of {@link Session#saveOrUpdate(Object)}'s semantics,
	 * which are outlined in the Hibernate manual section 11.7.
	 * The failure occurs when {@link Session#update(Object)} is called in the "saveOrUpdate" chain,
	 * as update expects the object with the given identifier not to already be persistent in the {@link Session}.
	 * @see GenericHibernateDao#makePersistent(Object)
	 */
	@Test
	public void testShowingMakePersistentFailure() {
		// Session 1
		EmployeeDao dao = new EmployeeHibernateDao();
		HibernateUtil.beginTransaction();
		Employee employeeForSession1 = new Employee();
		employeeForSession1.setFirstName("firstName");
		employeeForSession1.setLastName("lastName");
		dao.makePersistent(employeeForSession1);
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
		
		// UI work where the employee is modified.
		
		// Session 2 
		HibernateUtil.beginTransaction();
		// This causes the Employee to be loaded into the session.
		// As a result, the "update" portion of "saveOrUpdate" will fail.
		dao.findAll();
		Employee employeeForSession2 = new Employee();
		employeeForSession2.setId(employeeForSession1.getId());
		// Simulating changes made by the UI
		employeeForSession2.setFirstName("newFirstName");
		try {
			dao.makePersistent(employeeForSession2);
			fail("makePersistent should have failed since the employee that we're persisting was already loaded into the session.");
		} catch (NonUniqueObjectException ex) {}
	}
	
}
