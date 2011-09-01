package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneSalesOrder;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see SalesOrderHibernateDao
 */
public class SalesOrderHibernateDaoTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final SalesOrderDao dao = new SalesOrderHibernateDao();
	
	
	/**
	 * @see SalesOrderDao#findAllOpen()
	 */
	@Test
	public void testFindAllOpen() {
		// Empty database
		assertEmpty(dao.findAllOpen());
		
		// 4 Employees
		SalesOrder so1 = oneSalesOrder("Haggens", false);
		SalesOrder so2 = oneSalesOrder("Freds", true);
		SalesOrder so3 = oneSalesOrder("Freds", false);
		SalesOrder so4 = oneSalesOrder("Haggens", true);
		
		assertEquals(newArrayList(so1, so3), dao.findAllOpen());
	}
}
