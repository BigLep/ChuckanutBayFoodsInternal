package com.chuckanutbay.webapp.recievinginspection.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.recievinginspection.shared.InventoryItemDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("databaseQuery")
public interface DatabaseQueryService extends RemoteService {

	/**
	 * @return {@link InventoryItemDto}s for every item in inventory.
	 */
	List<InventoryItemDto> getInventoryItems();

}
