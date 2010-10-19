package com.chuckanutbay.businessobjects.util;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.Images;
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
		s.save(new Images("name", new byte[0], 0, 0, "mimeType", new Date()));
		t.commit();
	}

}
