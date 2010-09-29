package com.chuckanutbay.LotCodeManager.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseQueryServiceAsync {
	
	void setCheckedInIngredients(
			ArrayList<ItemInInventory> checkedInIngredients, 
			AsyncCallback<Void> callback);

	void getCheckedInIngredients(
			AsyncCallback<ArrayList<ItemInInventory>> callback);

	void getInUseIngredients(
			AsyncCallback<ArrayList<ItemInInventory>> callback);

	void setInUseIngredients(
			ArrayList<ItemInInventory> checkedInIngredients,
			AsyncCallback<Void> callback);

	void setUsedUpIngredients(
			ArrayList<ItemInInventory> checkedInIngredients,
			AsyncCallback<Void> callback);

	void getQBItems(
			AsyncCallback<ArrayList<QBItem>> callback);

	void getDateMatchInUseIngredients(
			Date date,
			AsyncCallback<ArrayList<ItemInInventory>> callback);

	void getFullIngredientHistory(
			AsyncCallback<ArrayList<ItemInInventory>> callback);

	void getLotCodeMatchIngredients(
			String lotCode,
			AsyncCallback<ArrayList<ItemInInventory>> callback);

}
