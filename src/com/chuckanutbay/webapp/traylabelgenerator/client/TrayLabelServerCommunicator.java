package com.chuckanutbay.webapp.traylabelgenerator.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;

public interface TrayLabelServerCommunicator {
	
	public void getSalesOrderLineItemsFromServer();
	
	public void getTrayLabelHistoryFromServer();
	
	public void getQuickbooksItemsFromServer();
	
	public void getCurrentLotCodeFromServer();
	
	public void updateTrayLabel(TrayLabelDto trayLabel);
	
	public void sendTrayLabelsToServer(List<TrayLabelDto> newTrayLabels);
	
	public void onSuccessfulGetSalesOrderLineItems(List<SalesOrderLineItemDto> result);
	
	public void onSuccessfulGetTrayLabelHistory(List<TrayLabelDto> trayLabels);
	
	public void onSuccessfulGetQuickbooksItems(List<QuickbooksItemDto> quickbookItems);
	
	public void onSuccessfulGetCurrentLotCode(String lotCode);
	
	public void onSuccessfulSendTrayLabels(List<TrayLabelDto> trayLabels);
	
}
