package com.chuckanutbay.webapp.common.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @see ServiceUtils#createInventoryLotService()
 */
@RemoteServiceRelativePath("InventoryLotService")
public interface InventoryLotService extends RemoteService {

	/**
	 * @return {@link InventoryLotDto}s that have been entered in but not started to be used.
	 */
	List<InventoryLotDto> getUnused();

	/**
	 * @return {@link InventoryLotDto}s that are currently active.
	 */
	List<InventoryLotDto> getInUse();

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be marked as in use.
	 */
	void setAsInUse(List<InventoryLotDto> inventoryLotDtos);

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be marked as used up.
	 */
	void setAsUsedUp(List<InventoryLotDto> inventoryLotDtos);

	/**
	 * 
	 * @param date
	 * @return {@link InventoryLotDto}s with the inUseDate before the inputed date and usedUpDate after inputed date or null.
	 */
	List<InventoryLotDto> getInUseOnDate(Date date);

	/**
	 * 
	 * @param lotCode
	 * @return {@link InventoryLotDto}s with the inputed lotCode.
	 */
	List<InventoryLotDto> getByLotCode(String lotCode);

	/**
	 * 
	 * @return all {@link InventoryLotDto}s.
	 */
	List<InventoryLotDto> getAll();
	
	/**
	 * Takes an incomplete {@link InventoryLotDto}, persists it to database, and returns a completed one.
	 * @param lotCode
	 * @param selectedItem
	 * @return 
	 */
	InventoryLotDto setAsUnused(InventoryLotDto inventoryLotDto);
	
	/**
	 * Removes {@link InventoryLotDto}s from the database
	 * @param inventoryLotDto
	 */
	void removeUnused(List<InventoryLotDto> inventoryLotDtos);
}
