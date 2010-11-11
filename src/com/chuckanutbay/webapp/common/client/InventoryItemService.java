package com.chuckanutbay.webapp.common.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.google.gwt.user.client.rpc.RemoteService;

public interface InventoryItemService extends RemoteService {

	/**
	 * @return {@link InventoryItemDto}s for every item in inventory.
	 */
	List<InventoryItemDto> getInventoryItems();

}
