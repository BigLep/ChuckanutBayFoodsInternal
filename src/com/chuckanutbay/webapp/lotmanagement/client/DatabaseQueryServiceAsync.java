package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.List;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseQueryServiceAsync {
	
	void setCheckedInIngredients(
			List<ItemInInventory> checkedInIngredients, 
			AsyncCallback<Void> callback);

	void getCheckedInIngredients(
			AsyncCallback<List<ItemInInventory>> callback);

	void getInUseIngredients(
			AsyncCallback<List<ItemInInventory>> callback);

	void setInUseIngredients(
			List<ItemInInventory> checkedInIngredients,
			AsyncCallback<Void> callback);

	void setUsedUpIngredients(
			List<ItemInInventory> checkedInIngredients,
			AsyncCallback<Void> callback);

	void getQBItems(
			AsyncCallback<List<QBItem>> callback);

	void getDateMatchInUseIngredients(
			Date date,
			AsyncCallback<List<ItemInInventory>> callback);

	void getFullIngredientHistory(
			AsyncCallback<List<ItemInInventory>> callback);

	void getLotCodeMatchIngredients(
			String lotCode,
			AsyncCallback<List<ItemInInventory>> callback);

}
