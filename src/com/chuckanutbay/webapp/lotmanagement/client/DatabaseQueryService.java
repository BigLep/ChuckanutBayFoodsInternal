package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.List;
import java.util.Date;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("databaseQuery")
public interface DatabaseQueryService extends RemoteService{

	List<InventoryLotDto> getCheckedInIngredients();
	
	void setCheckedInIngredients(List<InventoryLotDto> checkedInIngredients);
	
	List<InventoryLotDto> getInUseIngredients();
	
	List<InventoryItemDto> getInventoryItems();

	List<InventoryLotDto> getDateMatchInUseIngredients(Date date);

	List<InventoryLotDto> getLotCodeMatchIngredients(String lotCode);

	List<InventoryLotDto> getFullIngredientHistory();
	
	void setInUseIngredients(List<InventoryLotDto> checkedInIngredients);
	
	void setUsedUpIngredients(List<InventoryLotDto> checkedInIngredients);
}
