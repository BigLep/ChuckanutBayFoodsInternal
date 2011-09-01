package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneQuickbooksItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneSalesOrder;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneTrayLabel;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.BusinessObjects;
import com.chuckanutbay.businessobjects.SalesOrderLineItem;
import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see TrayLabelHibernateDao
 */
public class TrayLabelHibernateDaoTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final TrayLabelDao dao = new TrayLabelHibernateDao();
	
	
	/**
	 * @see TrayLabelDao#findBySalesOrderLineItemId()
	 */
	@Test
	public void testFindBySalesOrderLineItemId() {
		// Empty database
		assertEmpty(dao.findBySalesOrderLineItemId(2));
		
		SalesOrderLineItem soli1 = BusinessObjects.oneSalesOrderLineItem(oneSalesOrder("Haggens", false), oneQuickbooksItem("1111-11"), 10);
		SalesOrderLineItem soli2 = BusinessObjects.oneSalesOrderLineItem(oneSalesOrder("Freds", true), oneQuickbooksItem("1112-12"), 20);
		
		TrayLabel tl1 = oneTrayLabel(5, "4A111A1", soli1);
		TrayLabel tl2 = oneTrayLabel(5, "4A111A1", soli1);
		TrayLabel tl3 = oneTrayLabel(5, "4A111A1", soli2);
		TrayLabel tl4 = oneTrayLabel(5, "4A111A1", soli2);
		
		
		assertEquals(newArrayList(tl3, tl4), dao.findBySalesOrderLineItemId(2));
	}
}
