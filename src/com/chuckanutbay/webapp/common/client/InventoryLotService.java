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
	// FIXME: can we rename to getUnused
	List<InventoryLotDto> getUnusedInventoryLots();

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be checked in.
	 */
	// FIXME: can we rename to setAsUnused?
	void setUnusedInventoryLots(List<InventoryLotDto> inventoryLotDtos);

	/**
	 * @return {@link InventoryLotDto}s that are currently active.
	 */
	// FIXME: rename to getInUse
	List<InventoryLotDto> getInUseInventoryLots();

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be marked as in use.
	 */
	// FIXME: can we rename to setAsInUse
	void setInUseInventoryLots(List<InventoryLotDto> inventoryLotDtos);

	/**
	 * @param inventoryLotDtos {@link InventoryLotDto}s that should be marked as used up.
	 */
	// FIXME: can we rename to setAsUsedUp
	void setUsedUpInventoryLots(List<InventoryLotDto> inventoryLotDtos);

	// FIXME: rename to getInUseOnDate ?
	// TODO: add javadocs
	List<InventoryLotDto> getDateMatchInUseInventory(Date date);

	// FIXME: rename to getByLotCode?
	// TODO: add javadocs
	List<InventoryLotDto> getLotCodeMatchInventory(String lotCode);

	// FIXME: rename to getAll ?
	// TODO: add javadocs
	List<InventoryLotDto> getFullInventoryHistory();
}
