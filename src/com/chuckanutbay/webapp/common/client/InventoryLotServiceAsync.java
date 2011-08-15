package com.chuckanutbay.webapp.common.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InventoryLotServiceAsync {

	void getUnused(AsyncCallback<List<InventoryLotDto>> callback);

	void getInUseOnDate(Date date,
			AsyncCallback<List<InventoryLotDto>> callback);

	void getAll(AsyncCallback<List<InventoryLotDto>> callback);

	void getInUse(AsyncCallback<List<InventoryLotDto>> callback);

	void getByLotCode(String lotCode,
			AsyncCallback<List<InventoryLotDto>> callback);

	void setAsInUse(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

	void setAsUnused(InventoryLotDto inventoryLotDto,
			AsyncCallback<InventoryLotDto> callback);

	void setAsUsedUp(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

	void removeUnused(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

}
