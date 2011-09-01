package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see QuickbooksItemHibernateDao
 */
public class QuickbooksItemHibernateDaoTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final QuickbooksItemDao dao = new QuickbooksItemHibernateDao();
	
	
	/**
	 * @see QuickbooksItemDao#findCaseItems()
	 */
	@Test
	public void testFindCaseItems() {
		// Empty database
		assertEmpty(dao.findCaseItems());
		
		// 4 Employees
		QuickbooksItem qbItem1 = oneQuickbooksItem("1234");
		QuickbooksItem qbItem2 = oneQuickbooksItem("1234-1");
		QuickbooksItem qbItem3 = oneQuickbooksItem("1234-6");
		QuickbooksItem qbItem4 = oneQuickbooksItem("1234-21");
		QuickbooksItem qbItem5 = oneQuickbooksItem("1234-12");
		
		assertEquals(newArrayList(qbItem3, qbItem4, qbItem5), dao.findCaseItems());
	}
}
