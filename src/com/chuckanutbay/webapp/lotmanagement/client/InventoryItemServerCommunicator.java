package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;

public interface InventoryItemServerCommunicator {
	
	public void onSuccessfulGetInventoryItemsFromDatabase(List<InventoryItemDto> dbQBItemList);
	
}
