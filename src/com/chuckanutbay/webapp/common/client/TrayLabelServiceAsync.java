package com.chuckanutbay.webapp.common.client;

import java.util.List;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.InventoryTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TrayLabelServiceAsync {

	void getCurrentLotCode(AsyncCallback<String> callback);

	void getQuickbooksItemIds(
			AsyncCallback<List<String>> asyncCallback);

	void getSalesOrderLineItems(
			AsyncCallback<List<SalesOrderLineItemDto>> callback);

	void getTrayLabelHistroy(AsyncCallback<List<TrayLabelDto>> callback);

	void setTrayLabels(List<TrayLabelDto> newTrayLabels,
			AsyncCallback<Void> callback);

	void updateTrayLabel(TrayLabelDto trayLabel, AsyncCallback<Void> callback);

	void printTrayLabels(Set<TrayLabelDto> trayLabels,
			AsyncCallback<Void> callback);

	void getInventoryTrayLabelDto(String qbItemId, 
			AsyncCallback<InventoryTrayLabelDto> callback);

	void getLineItemsByOpenOrderAndFlavor(Integer openOrderId, String flavor,
			AsyncCallback<List<SalesOrderLineItemDto>> callback);

	void getOpenOrderFlavors(AsyncCallback<List<String>> callback);

	void getOpenOrdersByFlavor(String flavor,
			AsyncCallback<List<SalesOrderDto>> callback);

	void getTrayLabelDto(Integer id, AsyncCallback<TrayLabelDto> callback);

	void persistPackagingTransaction(PackagingTransactionDto ptDto,
			AsyncCallback<Void> callback);

}
