package com.chuckanutbay.LotCodeManager.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.chuckanutbay.LotCodeManager.client.DatabaseQueryService;
import com.chuckanutbay.LotCodeManager.client.ItemInInventory;
import com.chuckanutbay.LotCodeManager.client.QBItem;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseQueryServiceImpl extends RemoteServiceServlet implements DatabaseQueryService {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ItemInInventory> dbItemsInInventory = new ArrayList<ItemInInventory>();
	private ArrayList<QBItem> dbQBItems = new ArrayList<QBItem>();
	
	public void setCheckedInIngredients(final ArrayList<ItemInInventory> checkedInIngredients) {
		dbItemsInInventory.addAll(checkedInIngredients);
	}

	public ArrayList<ItemInInventory> getCheckedInIngredients() {
		ArrayList<ItemInInventory> checkedInIngredients = new ArrayList<ItemInInventory>();
		for(ItemInInventory itemInInventory : dbItemsInInventory) {
			if (itemInInventory.getInUseDate() == null && itemInInventory.getUsedUpDate() == null) {
				checkedInIngredients.add(itemInInventory);
			}
		}
		return checkedInIngredients;
	}

	public ArrayList<ItemInInventory> getInUseIngredients() {
		ArrayList<ItemInInventory> inUseIngredients = new ArrayList<ItemInInventory>();
		for(ItemInInventory itemInInventory : dbItemsInInventory) {
			if (itemInInventory.getInUseDate() != null && itemInInventory.getUsedUpDate() == null) {
				inUseIngredients.add(itemInInventory);
			}
		}
		return inUseIngredients;
	}

	@Override
	public void setInUseIngredients(ArrayList<ItemInInventory> inUseIngredients) {
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
	public void setUsedUpIngredients(ArrayList<ItemInInventory> usedUpIngredients) {
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
	public ArrayList<QBItem> getQBItems() {
		dbQBItems.clear();
		QBItem qbItem = new QBItem();
		qbItem.setIngredientName("Flour");
		qbItem.setIngredientCode("10023");
		dbQBItems.add(qbItem);
		
		return (ArrayList<QBItem>) dbQBItems;
	}

	@Override
	public ArrayList<ItemInInventory> getDateMatchInUseIngredients(Date date) {
		ArrayList<ItemInInventory> dateMatchList = new ArrayList<ItemInInventory>();
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
		if (dateMatchList.isEmpty()) {
			Window.alert("No items were in use that date");
		}
		return dateMatchList;
	}

	@Override
	public ArrayList<ItemInInventory> getLotCodeMatchIngredients(String lotCode) {
		ArrayList<ItemInInventory> lotCodeMatchList = new ArrayList<ItemInInventory>();
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
			if (lotCodeMatchList.isEmpty()) {
				Window.alert("There are no items with Lot Code " + lotCode);
			}
		}
		return lotCodeMatchList;
	}

	@Override
	public ArrayList<ItemInInventory> getFullIngredientHistory() {
		return (ArrayList<ItemInInventory>) dbItemsInInventory;
	}

}
