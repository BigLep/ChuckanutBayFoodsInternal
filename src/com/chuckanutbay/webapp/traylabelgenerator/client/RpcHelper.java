package com.chuckanutbay.webapp.traylabelgenerator.client;

import java.util.List;

import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.client.ServiceUtils.VoidAsyncCallback;
import com.chuckanutbay.webapp.common.client.TrayLabelServiceAsync;
import com.chuckanutbay.webapp.common.shared.InventoryTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	public static final TrayLabelServiceAsync trayLabelService = ServiceUtils.createTrayLabelService();
	
	public static AsyncCallback<List<SalesOrderLineItemDto>> createGetSalesOrderLineItemsCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<List<SalesOrderLineItemDto>>() {
			@Override
			public void onSuccess(List<SalesOrderLineItemDto> result) {
				caller.onSuccessfulGetSalesOrderLineItems(result);
			}
		};
	}
	
	public static AsyncCallback<List<TrayLabelDto>> createGetTrayLabelHistoryCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<List<TrayLabelDto>>() {
			@Override
			public void onSuccess(List<TrayLabelDto> result) {
				caller.onSuccessfulGetTrayLabelHistory(result);
			}
		};
	}
	
	public static AsyncCallback<List<String>> createGetQuickbooksItemIdsCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> result) {
				caller.onSuccessfulGetQuickbooksItemIds(result);
			}
		};
	}
	
	public static AsyncCallback<String> createGetCurrentLotCodeCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				caller.onSuccessfulGetCurrentLotCode(result);
			}
		};
	}
	
	public static AsyncCallback<Void> createUpdateTrayLabelCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				caller.onSuccessfulUpdateTrayLabel();
			}
		};
	}
	
	public static AsyncCallback<Void> createSendTrayLabelsCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				caller.onSuccessfulSendTrayLabelsToServer();
			}
		};
	}
	
	public static AsyncCallback<InventoryTrayLabelDto> createGetInventoryTrayLabelDtoCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<InventoryTrayLabelDto>() {
			@Override
			public void onSuccess(InventoryTrayLabelDto result) {
				caller.onSuccessfulGetInventoryTrayLabelDto(result);
			}
		};
	}
	
	public static AsyncCallback<Void> createVoidCallback() {
		return new VoidAsyncCallback<Void>();
	}
	
}
