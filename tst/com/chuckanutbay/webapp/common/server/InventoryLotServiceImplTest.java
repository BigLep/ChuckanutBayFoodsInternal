package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.businessobjects.BusinessObjects.oneInventoryItem;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneInventoryLot;
import static com.chuckanutbay.businessobjects.BusinessObjects.oneInventoryLotStickerColor;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toInventoryItemDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toInventoryLotStickerColorDtoFunction;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.util.testing.DatabaseResource;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;

/**
 * @see InventoryLotServiceImpl
 */
public class InventoryLotServiceImplTest {
	
	@Rule 
	public final DatabaseResource databaseResource = new DatabaseResource();
	
	private final InventoryLotServiceImpl server = new InventoryLotServiceImpl();
	
	/**
	 *  @see InventoryLotServiceImpl#getStickerColor(String lotCode,
			InventoryItem inventoryItem)
	 */
	@SuppressWarnings("unused")
	@Test
	public void testGetStickerColor() {
		InventoryLotStickerColor orange = oneInventoryLotStickerColor("Orange");
		InventoryLotStickerColor green = oneInventoryLotStickerColor("Green");
		InventoryLotStickerColor purple = oneInventoryLotStickerColor("Purple");
		
		InventoryItem inventoryItem1 = oneInventoryItem("IC1111", "Cream Cheese");
		InventoryItem inventoryItem2 = oneInventoryItem("IC1112", "Chedar Cheese");
		InventoryItem inventoryItem3 = oneInventoryItem("IC1114", "Blue Cheese");
		
		InventoryLot inventoryLot1 = oneInventoryLot(green, inventoryItem1, "1234", 8, new DateTime().minusDays(1).toDate(), null, null);
		InventoryLot inventoryLot2 = oneInventoryLot(purple, inventoryItem1, "1235", 8, new Date(), new Date(), null);
		InventoryLot inventoryLot3 = oneInventoryLot(orange, inventoryItem2, "1236", 8, new DateTime().minusDays(1).toDate(), new Date(), null);
		InventoryLot inventoryLot4 = oneInventoryLot(green, inventoryItem2, "1237", 8, new Date(), new Date(), null);
		
		InventoryLotDto inventoryLotDto1 = new InventoryLotDto("1234", toInventoryItemDtoFunction.apply(inventoryItem1), 5, new Date());
		InventoryLotDto inventoryLotDto2 = new InventoryLotDto("1234", toInventoryItemDtoFunction.apply(inventoryItem2), 5, new Date());
		
		Assert.assertTrue(toInventoryLotStickerColorDtoFunction.apply(green).equals(server.setAsUnused(inventoryLotDto1).getInventoryLotStickerColor()));
		Assert.assertTrue(toInventoryLotStickerColorDtoFunction.apply(purple).equals(server.setAsUnused(inventoryLotDto2).getInventoryLotStickerColor()));
	}
}
