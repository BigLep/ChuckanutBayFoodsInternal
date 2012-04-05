package com.chuckanutbay.webapp.traylabelgenerator.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;

public interface SalesOrderLineItemServerCommunicator {
	
	public void getOpenOrderFlavorsFromServer();
	
	public void getOpenOrdersFromServerByFlavor(String flavor);
	
	public void getLineItemsFromServerByOpenOrderAndFlavor(Integer openOrderId, String flavor);
	
	public void onSuccessfulGetOpenOrderFlavors(List<String> result);
	
	public void onSuccessfulGetOpenOrders(List<SalesOrderDto> result);
	
	public void onSuccessfulGetLineItems(List<SalesOrderLineItemDto> result);
	
}
