package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.addSubItems;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSubItem;
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
	
	/**
	 * @see QuickbooksItemDao#findAllIds()
	 */
	@Test
	public void testFindAllIds() {
		// Empty database
		assertEmpty(dao.findAllIds());
		
		// 4 Employees
		QuickbooksItem qbItem1 = oneQuickbooksItem("1234");
		QuickbooksItem qbItem2 = oneQuickbooksItem("1234-1");
		QuickbooksItem qbItem3 = oneQuickbooksItem("1234-6");
		QuickbooksItem qbItem4 = oneQuickbooksItem("1234-21");
		QuickbooksItem qbItem5 = oneQuickbooksItem("1234-12");
		
		QuickbooksItem qbItem6 = oneQuickbooksItem("27001-6", null, "7Inch", "NewYork", new Double(1));
		QuickbooksItem qbItem7 = oneQuickbooksItem("27002-6", null, "7Inch", "VeryBerry", new Double(1));
		QuickbooksItem qbItem8 = oneQuickbooksItem("27003-6", null, "7Inch", "WhiteChocolateRasp", new Double(1));
		QuickbooksItem qbItem9 = oneQuickbooksItem("27004-6", null, "7Inch", "ChocolateTruffle", new Double(1));
		
		QuickbooksItem qbItemWithSubs = oneQuickbooksItem("27039-6", null, "7Inch", "Fab4", new Double(1));

		addSubItems(qbItemWithSubs, 
				oneSubItem(qbItemWithSubs, qbItem6, 1.5),
				oneSubItem(qbItemWithSubs, qbItem7, 1.5),
				oneSubItem(qbItemWithSubs, qbItem8, 1.5),
				oneSubItem(qbItemWithSubs, qbItem9, 1.5)
		);
		
		assertEquals(newArrayList("1234-12", "1234-21", "1234-6", "27001-6", "27002-6", "27003-6", "27004-6", "27039-6 NewYork", "27039-6 VeryBerry", "27039-6 WhiteChocolateRasp", "27039-6 ChocolateTruffle"), dao.findAllIds());
	}
}
