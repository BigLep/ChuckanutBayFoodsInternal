package com.chuckanutbay.businessobjects.dao;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.util.testing.DatabaseResource;

/**
 * @see InventoryLotHibernateDao
 */
public class InventoryLotHibernateDaoTest {
	
	@Rule
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final InventoryLotDao dao = new InventoryLotHibernateDao();
	
	@Before
	@After
	@BeforeClass
	@AfterClass
	public void setUp() {
		
	}
	
	/**
	 * @see InventoryLotHibernateDao#findInUse()
	 */
	@Test
	public void testFindInUse() {
		// Empty database
		assertEquals(newArrayList(), dao.findUnused());

		// 1 received lot.
		InventoryItemDao inventoryItemDao = new InventoryItemHibernateDao();
		InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setDescription("description");
		inventoryItemDao.makePersistent(inventoryItem);
		
		InventoryLot inventoryLot = new InventoryLot();
		inventoryLot.setCode("code");
		inventoryLot.setInventoryItem(inventoryItem);
		inventoryLot.setReceivedDatetime(new Date());
		dao.makePersistent(inventoryLot);
		
		assertEquals(newArrayList(inventoryLot), dao.findUnused());
		
		// 1 used lot.
		inventoryLot.setStartUseDatetime(new Date());
		assertEquals(newArrayList(), dao.findUnused());
		
		
	}
	
	@Test
	public void testTest() {
		assertFalse(true);
	}

}
