package com.chuckanutbay.webapp.common.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	public static TimeClockServiceAsync createTimeClockService() {
		TimeClockServiceAsync service = GWT.create(TimeClockService.class);
		((ServiceDefTarget)service).setServiceEntryPoint("/common/TimeClockService");
		return service;
	}
	public static TrayLabelServiceAsync createTrayLabelService() {
		TrayLabelServiceAsync service = GWT.create(TrayLabelService.class);
		((ServiceDefTarget)service).setServiceEntryPoint("/common/TrayLabelService");
		return service;
	}
	public static PackagingTransactionServiceAsync createPackagingTransactionService() {
		PackagingTransactionServiceAsync service = GWT.create(PackagingTransactionService.class);
		((ServiceDefTarget)service).setServiceEntryPoint("/common/PackagingTransactionService");
		return service;
	}
	
	/**
	 * {@link AsyncCallback} that provides a default {@link AsyncCallback#onFailure(Throwable)} handler.
	 * @param <T>
	 */
	public static abstract class DefaultAsyncCallback<T> implements AsyncCallback<T> {
		@Override
		public void onFailure(Throwable caught) {
			if (!GWT.isProdMode()) {
				Window.alert("System failed: " + caught);
			}
		}
	}
	
	public static class VoidAsyncCallback<Void> implements AsyncCallback<Void> {
		@Override
		public void onFailure(Throwable caught) {
			if (!GWT.isProdMode()) {
				Window.alert("System failed: " + caught);
			}
		}

		@Override
		public void onSuccess(Void result) {
			//Do Nothing
		}
	}
	
	
}
