package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.common.client.CollectionsUtils.isEmpty;

import java.util.List;

import com.chuckanutbay.webapp.common.client.InventoryItemServiceAsync;
import com.chuckanutbay.webapp.common.client.InventoryLotServiceAsync;
import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	private final InventoryLotServiceAsync inventoryLotService;
	private final InventoryItemServiceAsync inventoryItemService;

	public RpcHelper() {
		inventoryLotService = ServiceUtils.createInventoryLotService();
		inventoryItemService = ServiceUtils.createInventoryItemService();
	}

	/**
	 * {@link AsyncCallback} for when nothing needs to be done with the response.
	 */
	public static final AsyncCallback<Void> VOID_CALLBACK = new DefaultAsyncCallback<Void>() {
		@Override
		public void onSuccess(Void result) {
		}
	};

	public static AsyncCallback<List<InventoryItemDto>> createInventoryItemServiceCallback(final InventoryItemServerCommunicator senderObject) {
		return new DefaultAsyncCallback<List<InventoryItemDto>>() {
			@Override
			public void onSuccess(List<InventoryItemDto> dbQBItemList) {
				GWT.log("success on server");
				if(!isEmpty(dbQBItemList)) {
					GWT.log("A good return is coming");
				}
				senderObject.onSuccessfulGetInventoryItemsFromDatabase(dbQBItemList);
			}
		};
	}

	public static AsyncCallback<List<InventoryLotDto>> createInventoryLotServiceCallback(final LotCodeManagerDialogBox senderObject) {
		return new DefaultAsyncCallback<List<InventoryLotDto>>() {
			@Override
			public void onSuccess(List<InventoryLotDto> dbItemInInventoryList) {
				GWT.log("success on server");
				if(!isEmpty(dbItemInInventoryList)) {
					GWT.log("A good return is coming");
				}
				senderObject.populateCellTable(dbItemInInventoryList);
			}
		};
	}
}
