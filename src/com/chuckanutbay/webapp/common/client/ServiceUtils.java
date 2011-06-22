package com.chuckanutbay.webapp.common.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Utilities for working with {@link RemoteService} types in this base module.
 * These create* methods are preferred over {@link GWT#create(Class)} within a sub-module
 * as they will will yield an instance with a path that is not relative to the sub-module.
 * This is the preferred/support method of access.
 */
public class ServiceUtils {

	public static InventoryItemServiceAsync createInventoryItemService() {
		InventoryItemServiceAsync service = GWT.create(InventoryItemService.class);
		((ServiceDefTarget)service).setServiceEntryPoint("/common/InventoryItemService");
		return service;
	}
	public static InventoryLotServiceAsync createInventoryLotService() {
		InventoryLotServiceAsync service = GWT.create(InventoryLotService.class);
		((ServiceDefTarget)service).setServiceEntryPoint("/common/InventoryLotService");
		return service;
	}
}
