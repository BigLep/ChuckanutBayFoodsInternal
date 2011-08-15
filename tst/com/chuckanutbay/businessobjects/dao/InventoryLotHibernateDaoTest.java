package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneInventoryItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneInventoryLot;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneInventoryLotStickerColor;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see InventoryLotHibernateDao
 */
public class InventoryLotHibernateDaoTest {
	
	@Rule
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final InventoryLotDao dao = new InventoryLotHibernateDao();
	
	/**
	 * @see InventoryLotHibernateDao#isActive(String lotCode)
	 */
	@Test
	public void testIsActive() {
		// Empty database
		assertEmpty(dao.findAll());
		
		InventoryLotStickerColor color = oneInventoryLotStickerColor("Orange");
		InventoryItem inventoryItem1 = oneInventoryItem("IC1111", "Cream Cheese");
		InventoryItem inventoryItem2 = oneInventoryItem("IC1112", "Chedar Cheese");
		
		InventoryLot inventoryLot1 = oneInventoryLot(color, inventoryItem1, "1234", 8, new Date(), null, null);
		InventoryLot inventoryLot2 = oneInventoryLot(color, inventoryItem1, "1235", 8, new Date(), new Date(), null);
		InventoryLot inventoryLot3 = oneInventoryLot(color, inventoryItem2, "1236", 8, new Date(), new Date(), null);
		InventoryLot inventoryLot4 = oneInventoryLot(color, inventoryItem1, "1237", 8, new Date(), new Date(), new Date());
		
		assertTrue(inventoryLot1.equals(dao.findActiveMatch("1234", inventoryItem1)));
		assertTrue(inventoryLot2.equals(dao.findActiveMatch("1235", inventoryItem1)));
		assertFalse(inventoryLot3.equals(dao.findActiveMatch("1235", inventoryItem2)));
		assertFalse(inventoryLot4.equals(dao.findActiveMatch("1237", inventoryItem1)));
		
	}
	
	/**
	 * @see InventoryLotHibernateDao#findByInventoryItem(InventoryItem item)
	 */
	@Test
	public void testFindByInventoryItem() {
		// Empty database
		assertEmpty(dao.findAll());
		
		InventoryLotStickerColor color = oneInventoryLotStickerColor("Orange");
		InventoryItem inventoryItem1 = oneInventoryItem("IC1111", "Cream Cheese");
		InventoryItem inventoryItem2 = oneInventoryItem("IC1112", "Chedar Cheese");
		
		InventoryLot inventoryLot1 = oneInventoryLot(color, inventoryItem1, "1234", 8, new DateTime().minusDays(1).toDate(), null, null);
		InventoryLot inventoryLot2 = oneInventoryLot(color, inventoryItem1, "1235", 8, new Date(), new Date(), null);
		InventoryLot inventoryLot3 = oneInventoryLot(color, inventoryItem2, "1236", 8, new DateTime().minusDays(1).toDate(), new Date(), null);
		InventoryLot inventoryLot4 = oneInventoryLot(color, inventoryItem2, "1237", 8, new Date(), new Date(), new Date());
		
		assertEquals(newArrayList(inventoryLot4, inventoryLot3), dao.findByInventoryItem(inventoryItem2));
		
	}
	
	/**
	 * @see InventoryLotHibernateDao#findInUse()
	 */
	@Test
	public void testFindInUse() {
		// Empty database
		assertEmpty(dao.findInUse());
		
		InventoryLotStickerColor color = oneInventoryLotStickerColor("Orange");
		InventoryItem inventoryItem1 = oneInventoryItem("IC1111", "Cream Cheese");
		
		InventoryLot inventoryLot1 = oneInventoryLot(color, inventoryItem1, "1234", 8, new Date(), null, null);
		InventoryLot inventoryLot2 = oneInventoryLot(color, inventoryItem1, "1235", 8, new Date(), new Date(), null);
		InventoryLot inventoryLot3 = oneInventoryLot(color, inventoryItem1, "1236", 8, new Date(), new Date(), new Date());

		assertEquals(newArrayList(inventoryLot2), dao.findInUse());
	}
}
