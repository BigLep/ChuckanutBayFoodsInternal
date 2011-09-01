package com.chuckanutbay.webapp.traylabelgenerator.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

public interface TrayLabelServerCommunicator {
	
	public void getSalesOrderLineItemsFromServer();
	
	public void getTrayLabelHistoryFromServer();
	
	public void getQuickbooksItemsFromServer();
	
	public void getCurrentLotCodeFromServer();
	
	public void printTrayLabels(Set<TrayLabelDto> trayLabels);
	
	public void updateTrayLabel(TrayLabelDto trayLabel);
	
	public void sendTrayLabelsToServer(List<TrayLabelDto> newTrayLabels);
	
	public void onSuccessfulGetSalesOrderLineItems(List<SalesOrderLineItemDto> result);
	
	public void onSuccessfulGetTrayLabelHistory(List<TrayLabelDto> trayLabels);
	
	public void onSuccessfulGetQuickbooksItems(Map<String, QuickbooksItemDto> quickbooksItems);
	
	public void onSuccessfulGetCurrentLotCode(String lotCode);
	
	public void onSuccessfulUpdateTrayLabel();
	
	public void onSuccessfulSendTrayLabelsToServer();
	
}
