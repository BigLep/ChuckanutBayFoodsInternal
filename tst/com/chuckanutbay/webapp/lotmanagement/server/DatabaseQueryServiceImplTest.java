package com.chuckanutbay.webapp.lotmanagement.server;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotHibernateDao;
import com.chuckanutbay.util.testing.DatabaseResource;
import com.chuckanutbay.webapp.common.server.DatabaseQueryServiceImpl;


/**
 * @see DatabaseQueryServiceImpl
 */
public class DatabaseQueryServiceImplTest {
	
	@Rule
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	/**
	 * @see DatabaseQueryServiceImpl#getUnusedIngredientLots()
	 */
	@Test
	public void testGetCheckedInIngredientLots() {
		DatabaseQueryService service = new DatabaseQueryServiceImpl();
		Assert.assertTrue(service.getUnusedIngredientLots().isEmpty());
		
		InventoryItem item = createInventoryItem("id", "descrition");
		InventoryLot lot = createInventoryLot(item, "code");
		
		Assert.assertFalse(service.getUnusedIngredientLots().isEmpty());
	}
	
	private InventoryItem createInventoryItem(String id, String description) {
		InventoryItem item = new InventoryItem(id, description);
		new InventoryItemHibernateDao().makePersistent(item);
		return item;
	}
	
	private InventoryLot createInventoryLot(InventoryItem item, String code) {
		InventoryLot lot = new InventoryLot();
		lot.setInventoryItem(item);
		lot.setCode(code);
		lot.setReceivedDatetime(new Date());
		new InventoryLotHibernateDao().makePersistent(lot);
		return lot;
	}
	
}
