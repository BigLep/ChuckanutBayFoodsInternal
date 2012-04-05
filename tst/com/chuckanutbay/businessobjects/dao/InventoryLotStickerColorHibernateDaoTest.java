package com.chuckanutbay.businessobjects.dao;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneInventoryLotStickerColor;
import static com.chuckanutbay.util.testing.AssertExtensions.assertEmpty;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.util.testing.DatabaseResource;
/**
 * @see InventoryLotStickerColorHibernateDao
 */
public class InventoryLotStickerColorHibernateDaoTest {
	@Rule
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final InventoryLotStickerColorDao dao = new InventoryLotStickerColorHibernateDao();
	
	/**
	 * @see InventoryLotHibernateDao#findNextColor(InventoryLotStickerColor inputColor)
	 */
	@Test
	public void testFindNextColor() {
		// Empty database
		assertEmpty(dao.findAll());
		
		InventoryLotStickerColor orange = oneInventoryLotStickerColor("Orange");
		InventoryLotStickerColor green = oneInventoryLotStickerColor("Green");
		InventoryLotStickerColor purple = oneInventoryLotStickerColor("Purple");
		
		assertTrue(green.equals(dao.findNextColor(orange)));
		assertTrue(orange.equals(dao.findNextColor(purple)));
		
	}
	
	/**
	 * @see InventoryLotHibernateDao#findFirstColor()
	 */
	@Test
	public void testFindFirstColor() {
		// Empty database
		assertEmpty(dao.findAll());
		
		InventoryLotStickerColor orange = oneInventoryLotStickerColor("Orange");
		InventoryLotStickerColor green = oneInventoryLotStickerColor("Green");
		InventoryLotStickerColor purple = oneInventoryLotStickerColor("Purple");
		
		assertTrue(orange.equals(dao.findFirstColor()));
	}
}
