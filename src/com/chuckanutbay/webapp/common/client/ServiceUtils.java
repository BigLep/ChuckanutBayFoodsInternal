package com.chuckanutbay.webapp.common.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ServiceUtils {
	
	public static InventoryItemServiceAsync createInventoryItemService() {
		return createServiceHelper(InventoryItemService.class, "InventoryItemService");
	}
	public static InventoryLotServiceAsync createInventoryLotService() {
		return createServiceHelper(InventoryLotService.class, "InventoryLotService");
	}
	
	private static <T> T createServiceHelper(Class<?> classLiteral, String relativePath) {
		T service = GWT.create(classLiteral);
		((ServiceDefTarget)service).setServiceEntryPoint("/common/" + relativePath);
		return service;
	}

}
