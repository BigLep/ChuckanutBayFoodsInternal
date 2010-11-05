package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("databaseQuery")
public interface DatabaseQueryService extends RemoteService {

	/**
	 * @return {@link InventoryItemDto}s for every item in inventory.
	 */
	List<InventoryItemDto> getInventoryItems();

	/**
	 * @return {@link InventoryLotDto}s that have been entered in but not started to be used query.
	 */
	List<InventoryLotDto> getUnusedIngredientLots();

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be checked in.
	 */
	void setUnusedIngredientLots(List<InventoryLotDto> inventoryLotDtos);

	/**
	 * @return {@link InventoryLotDto}s that are currently active.
	 */
	List<InventoryLotDto> getInUseIngredientLots();

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be marked as in use.
	 */
	void setInUseIngredientLots(List<InventoryLotDto> inventoryLotDtos);

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be marked as used up.
	 */
	void setUsedUpInventoryLots(List<InventoryLotDto> inventoryLotDtos);

	List<InventoryLotDto> getDateMatchInUseIngredients(Date date);

	List<InventoryLotDto> getLotCodeMatchIngredients(String lotCode);

	List<InventoryLotDto> getFullIngredientHistory();
}
