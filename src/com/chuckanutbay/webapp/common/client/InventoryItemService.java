package com.chuckanutbay.webapp.common.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Since this {@link RemoteService} is defined within a base module,
 * doing {@link GWT#create(Class)} within a sub-module will yield an instance with a path relative to that base module.
 * In order to get an instances with a path that is not relative to the sub-module,
 * use {@link ServiceUtils#createInventoryItemService()}.
 * This is the preferred/support method of access.
 */
@RemoteServiceRelativePath("InventoryItemService")
public interface InventoryItemService extends RemoteService {

	/**
	 * @return {@link InventoryItemDto}s for every item in inventory.
	 */
	List<InventoryItemDto> getInventoryItems();

}
