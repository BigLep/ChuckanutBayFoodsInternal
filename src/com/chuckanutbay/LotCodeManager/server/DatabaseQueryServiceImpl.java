package com.chuckanutbay.LotCodeManager.server;

import java.util.List;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

import com.chuckanutbay.LotCodeManager.client.DatabaseQueryService;
import com.chuckanutbay.LotCodeManager.client.ItemInInventory;
import com.chuckanutbay.LotCodeManager.client.QBItem;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseQueryServiceImpl extends RemoteServiceServlet implements DatabaseQueryService {
	
	private static final long serialVersionUID = 1L;
	
	private List<ItemInInventory> dbItemsInInventory = new ArrayList<ItemInInventory>();
	private List<QBItem> dbQBItems = new ArrayList<QBItem>();
	
	public void setCheckedInIngredients(final List<ItemInInventory> checkedInIngredients) {
		dbItemsInInventory.addAll(checkedInIngredients);
	}

	public List<ItemInInventory> getCheckedInIngredients() {
		List<ItemInInventory> checkedInIngredients = new ArrayList<ItemInInventory>();
		for(ItemInInventory itemInInventory : dbItemsInInventory) {
			if (itemInInventory.getInUseDate() == null && itemInInventory.getUsedUpDate() == null) {
				checkedInIngredients.add(itemInInventory);
			}
		}
		return checkedInIngredients;
	}

	public List<ItemInInventory> getInUseIngredients() {
		List<ItemInInventory> inUseIngredients = new ArrayList<ItemInInventory>();
		for(ItemInInventory itemInInventory : dbItemsInInventory) {
			if (itemInInventory.getInUseDate() != null && itemInInventory.getUsedUpDate() == null) {
				inUseIngredients.add(itemInInventory);
			}
		}
		return inUseIngredients;
	}

	@Override
	public void setInUseIngredients(List<ItemInInventory> inUseIngredients) {
		Iterator<ItemInInventory> iterator = dbItemsInInventory.iterator();
		while (iterator.hasNext()) {
			ItemInInventory itemInInventory = iterator.next();
			if (itemInInventory.getInUseDate() == null && itemInInventory.getUsedUpDate() == null) {
				iterator.remove();
			}
		}
		dbItemsInInventory.addAll(inUseIngredients);
	}

	@Override
	public void setUsedUpIngredients(List<ItemInInventory> usedUpIngredients) {
		Iterator<ItemInInventory> iterator = dbItemsInInventory.iterator();
		while (iterator.hasNext()) {
			ItemInInventory itemInInventory = iterator.next();
			if (itemInInventory.getInUseDate() != null && itemInInventory.getUsedUpDate() == null) {
				iterator.remove(); 
			}
		}
		dbItemsInInventory.addAll(usedUpIngredients);
		
	}

	@Override
	public List<QBItem> getQBItems() {
		dbQBItems.clear();
		QBItem qbItem = new QBItem();
		qbItem.setIngredientName("Flour");
		qbItem.setIngredientCode("10023");
		dbQBItems.add(qbItem);
		
		return (List<QBItem>) dbQBItems;
	}

	@Override
	public List<ItemInInventory> getDateMatchInUseIngredients(Date date) {
		List<ItemInInventory> dateMatchList = new ArrayList<ItemInInventory>();
		for (ItemInInventory possibleDateMatch : dbItemsInInventory) {
			if (possibleDateMatch.getInUseDate() != null && possibleDateMatch.getUsedUpDate() != null) {
				long searchDate = date.getTime();
				long inUseDate = possibleDateMatch.getInUseDate().getTime();
				long usedUpDate = possibleDateMatch.getUsedUpDate().getTime();	
				if (searchDate >= inUseDate && searchDate <= usedUpDate) { 
					dateMatchList.add(possibleDateMatch);
				}
			}
			else if (possibleDateMatch.getInUseDate() != null) {
				long searchDate = date.getTime();
				long inUseDate = possibleDateMatch.getInUseDate().getTime();
				if (searchDate >= inUseDate) { 
					dateMatchList.add(possibleDateMatch);
				}
			}
		}
		return dateMatchList;
	}

	@Override
	public List<ItemInInventory> getLotCodeMatchIngredients(String lotCode) {
		List<ItemInInventory> lotCodeMatchList = new ArrayList<ItemInInventory>();
		lotCode.toUpperCase().trim();
		if (lotCode.matches("")) {
		}
		// lot code must be between 1 and 20 chars that are numbers, or letters.
		else if (!lotCode.matches("[0-9A-Z\\s]{1,20}")) {
	    	Window.alert("'" + lotCode + "' is not a valid Lot Code.");
	    }
		else { 
			for (ItemInInventory possibleLotCodeMatch : dbItemsInInventory) {
				if (lotCode.equals(possibleLotCodeMatch.getLotCode())) { 
					lotCodeMatchList.add(possibleLotCodeMatch);
				}
			}
		}
		return lotCodeMatchList;
	}

	@Override
	public List<ItemInInventory> getFullIngredientHistory() {
		return (List<ItemInInventory>) dbItemsInInventory;
	}

}
