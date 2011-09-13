package com.chuckanutbay.webapp.traylabelgenerator.client;

import java.util.List;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.InventoryTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

public interface TrayLabelServerCommunicator {
	
	public void getSalesOrderLineItemsFromServer();
	
	public void getTrayLabelHistoryFromServer();
	
	public void getQuickbooksItemIdsFromServer();
	
	public void getInventoryTrayLabelDto(String qbItemId);
	
	public void getCurrentLotCodeFromServer();
	
	public void printTrayLabels(Set<TrayLabelDto> trayLabels);
	
	public void updateTrayLabel(TrayLabelDto trayLabel);
	
	public void sendTrayLabelsToServer(List<TrayLabelDto> newTrayLabels);
	
	public void onSuccessfulGetSalesOrderLineItems(List<SalesOrderLineItemDto> result);
	
	public void onSuccessfulGetTrayLabelHistory(List<TrayLabelDto> trayLabels);
	
	public void onSuccessfulGetQuickbooksItemIds(List<String> result);
	
	public void onSuccessfulGetInventoryTrayLabelDto(InventoryTrayLabelDto result);
	
	public void onSuccessfulGetCurrentLotCode(String lotCode);
	
	public void onSuccessfulUpdateTrayLabel();
	
	public void onSuccessfulSendTrayLabelsToServer();
	
}
