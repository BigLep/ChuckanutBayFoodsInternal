package com.chuckanutbay.webapp.traylabelgenerator.client;

import java.util.List;

import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.client.ServiceUtils.DefaultAsyncCallback;
import com.chuckanutbay.webapp.common.client.ServiceUtils.VoidAsyncCallback;
import com.chuckanutbay.webapp.common.client.TrayLabelServiceAsync;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
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
	
	public static AsyncCallback<List<QuickbooksItemDto>> createGetQuickbooksItemsCallback(final TrayLabelServerCommunicator caller) {
		return new DefaultAsyncCallback<List<QuickbooksItemDto>>() {
			@Override
			public void onSuccess(List<QuickbooksItemDto> result) {
				caller.onSuccessfulGetQuickbooksItems(result);
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
		return new VoidAsyncCallback<Void>();
	}
	
	
}
