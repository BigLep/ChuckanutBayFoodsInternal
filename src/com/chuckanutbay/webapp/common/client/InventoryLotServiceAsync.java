package com.chuckanutbay.webapp.common.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InventoryLotServiceAsync {

	void getUnusedIngredientLots(AsyncCallback<List<InventoryLotDto>> callback);

	void getDateMatchInUseIngredients(Date date,
			AsyncCallback<List<InventoryLotDto>> callback);

	void getFullIngredientHistory(AsyncCallback<List<InventoryLotDto>> callback);

	void getInUseIngredientLots(AsyncCallback<List<InventoryLotDto>> callback);

	void getLotCodeMatchIngredients(String lotCode,
			AsyncCallback<List<InventoryLotDto>> callback);

	void setInUseIngredientLots(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

	void setUnusedIngredientLots(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

	void setUsedUpInventoryLots(List<InventoryLotDto> inventoryLotDtos,
			AsyncCallback<Void> callback);

}
