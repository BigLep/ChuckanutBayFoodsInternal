package com.chuckanutbay.businessobjects.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see HibernateUtil
 */
public class HibernateUtilTest {

	@Rule
	public final DatabaseResource databaseResource = new DatabaseResource();

	/**
	 * @see HibernateUtil#getSession()
	 */
	@Test
	public void testGetSession() {
		Session s = HibernateUtil.getSession();
		Transaction t = s.beginTransaction();
		Employee e = new Employee();
		e.setFirstName("firstName");
		e.setLastName("lastName");
		s.save(e);
		t.commit();
	}
	
	/**
	 * @see
	 */
	public void testGetTransaction() {
		
	}

}
