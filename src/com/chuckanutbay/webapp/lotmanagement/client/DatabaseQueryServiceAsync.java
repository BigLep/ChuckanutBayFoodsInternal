package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseQueryServiceAsync {
	
	void setUnusedIngredientLots(
			List<InventoryLotDto> checkedInIngredients, 
			AsyncCallback<Void> callback);

	void getUnusedIngredientLots(
			AsyncCallback<List<InventoryLotDto>> callback);

	void getInUseIngredientLots(
			AsyncCallback<List<InventoryLotDto>> callback);

	void setInUseIngredientLots(
			List<InventoryLotDto> checkedInIngredients,
			AsyncCallback<Void> callback);

	void setUsedUpInventoryLots(
			List<InventoryLotDto> checkedInIngredients,
			AsyncCallback<Void> callback);

	void getInventoryItems(
			AsyncCallback<List<InventoryItemDto>> callback);

	void getDateMatchInUseIngredients(
			Date date,
			AsyncCallback<List<InventoryLotDto>> callback);

	void getFullIngredientHistory(
			AsyncCallback<List<InventoryLotDto>> callback);

	void getLotCodeMatchIngredients(
			String lotCode,
			AsyncCallback<List<InventoryLotDto>> callback);

}
