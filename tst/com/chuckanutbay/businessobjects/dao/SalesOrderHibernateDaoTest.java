package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.addSubItems;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSalesOrder;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSalesOrderLineItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSubItem;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.businessobjects.SalesOrderLineItem;
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
		
		// 4 SalesOrders
		SalesOrder so1 = oneSalesOrder("Haggens", false);
		SalesOrder so2 = oneSalesOrder("Freds", true);
		SalesOrder so3 = oneSalesOrder("Freds", false);
		SalesOrder so4 = oneSalesOrder("Haggens", true);
		
		assertEquals(newArrayList(so1, so3), dao.findAllOpen());
	}
	
	/**
	 * @see SalesOrderDao#findAllOpenOrderFlavors()
	 */
	@Test
	public void testFindAllOpenOrderFlavors() {
		
		SalesOrder so1 = oneSalesOrder("Haggens", false);
		SalesOrder so2 = oneSalesOrder("Freds", true);
	
		QuickbooksItem qbItem1 = oneQuickbooksItem("27001-6", null, "7Inch", "NewYork", 1.0);
		QuickbooksItem qbItem2 = oneQuickbooksItem("27002-6", null, "7Inch", "VeryBerry", 1.0);
		QuickbooksItem qbItem3 = oneQuickbooksItem("27003-6", null, "7Inch", "WhiteChocolateRasp", 1.0);
		QuickbooksItem qbItem4 = oneQuickbooksItem("27004-6", null, "7Inch", "ChocolateTruffle", 1.0);
		
		QuickbooksItem qbItemWithSubs = oneQuickbooksItem("27039-6", null, "7Inch", "Fab4", 1.0);

		addSubItems(qbItemWithSubs, 
				oneSubItem(qbItemWithSubs, qbItem1, 1.5),
				oneSubItem(qbItemWithSubs, qbItem2, 1.5),
				oneSubItem(qbItemWithSubs, qbItem3, 1.5),
				oneSubItem(qbItemWithSubs, qbItem4, 1.5)
		);
		
		SalesOrderLineItem soli1 = oneSalesOrderLineItem(so1, qbItem1, 5);
		SalesOrderLineItem soli2 = oneSalesOrderLineItem(so1, qbItem1, 5);
		SalesOrderLineItem soli3 = oneSalesOrderLineItem(so1, qbItem2, 5);
		SalesOrderLineItem soli4 = oneSalesOrderLineItem(so1, qbItemWithSubs, 5);
		SalesOrderLineItem soli5 = oneSalesOrderLineItem(so2, oneQuickbooksItem("27027-6", "HL 7''", "7Inch", "HoneyLavender", 1.0), 5);
		
		assertEquals(newArrayList("ChocolateTruffle", "NewYork", "VeryBerry", "WhiteChocolateRasp"), dao.findAllOpenOrderFlavors());
		
	}
	
}
