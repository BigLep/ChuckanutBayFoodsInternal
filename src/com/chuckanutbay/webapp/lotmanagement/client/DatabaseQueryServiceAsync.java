package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.List;
import java.util.Date;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseQueryServiceAsync {
	
	void setCheckedInIngredients(
			List<InventoryLotDto> checkedInIngredients, 
			AsyncCallback<Void> callback);

	void getCheckedInIngredients(
			AsyncCallback<List<InventoryLotDto>> callback);

	void getInUseIngredients(
			AsyncCallback<List<InventoryLotDto>> callback);

	void setInUseIngredients(
			List<InventoryLotDto> checkedInIngredients,
			AsyncCallback<Void> callback);

	void setUsedUpIngredients(
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
