package com.chuckanutbay.LotCodeManager.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("databaseQuery")
public interface DatabaseQueryService extends RemoteService{

	ArrayList<ItemInInventory> getCheckedInIngredients();
	
	void setCheckedInIngredients(ArrayList<ItemInInventory> checkedInIngredients);
	
	ArrayList<ItemInInventory> getInUseIngredients();
	
	ArrayList<QBItem> getQBItems();

	ArrayList<ItemInInventory> getDateMatchInUseIngredients(Date date);

	ArrayList<ItemInInventory> getLotCodeMatchIngredients(String lotCode);

	ArrayList<ItemInInventory> getFullIngredientHistory();
	
	void setInUseIngredients(ArrayList<ItemInInventory> checkedInIngredients);
	
	void setUsedUpIngredients(ArrayList<ItemInInventory> checkedInIngredients);
}
