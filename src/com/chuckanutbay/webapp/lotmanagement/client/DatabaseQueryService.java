package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.List;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("databaseQuery")
public interface DatabaseQueryService extends RemoteService{

	List<ItemInInventory> getCheckedInIngredients();
	
	void setCheckedInIngredients(List<ItemInInventory> checkedInIngredients);
	
	List<ItemInInventory> getInUseIngredients();
	
	List<QBItem> getQBItems();

	List<ItemInInventory> getDateMatchInUseIngredients(Date date);

	List<ItemInInventory> getLotCodeMatchIngredients(String lotCode);

	List<ItemInInventory> getFullIngredientHistory();
	
	void setInUseIngredients(List<ItemInInventory> checkedInIngredients);
	
	void setUsedUpIngredients(List<ItemInInventory> checkedInIngredients);
}
