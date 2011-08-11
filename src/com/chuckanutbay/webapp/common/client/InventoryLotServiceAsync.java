package com.chuckanutbay.webapp.common.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InventoryLotServiceAsync {

	void getUnusedInventoryLots(AsyncCallback<List<InventoryLotDto>> callback);

	void getDateMatchInUseInventory(Date date,
			AsyncCallback<List<InventoryLotDto>> callback);

	void getFullInventoryHistory(AsyncCallback<List<InventoryLotDto>> callback);

	void getInUseInventoryLots(AsyncCallback<List<InventoryLotDto>> callback);

	void getLotCodeMatchInventory(String lotCode,
			AsyncCallback<List<InventoryLotDto>> callback);

	void setInUseInventoryLots(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

	void setUnusedInventoryLots(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

	void setUsedUpInventoryLots(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

}
