package com.chuckanutbay.webapp.lotmanagement.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.util.HibernateUtil;
import com.chuckanutbay.webapp.lotmanagement.client.DatabaseQueryService;
import com.chuckanutbay.webapp.lotmanagement.client.ItemInInventory;
import com.chuckanutbay.webapp.lotmanagement.client.QBItem;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseQueryServiceImpl extends RemoteServiceServlet implements DatabaseQueryService {

	private static final long serialVersionUID = 1L;

	private final List<ItemInInventory> dbItemsInInventory = new ArrayList<ItemInInventory>();
	private final List<QBItem> dbQBItems = new ArrayList<QBItem>();

	@Override
	public void setCheckedInIngredients(final List<ItemInInventory> checkedInIngredients) {
		Session s = HibernateUtil.getSession();
		Transaction t = s.beginTransaction();
		ItemInInventory itemInInventory = checkedInIngredients.get(0);
		s.save(new InventoryItem(itemInInventory.getLotCode(), "description"));
		t.commit();
		dbItemsInInventory.addAll(checkedInIngredients);
	}

	@Override
	public List<ItemInInventory> getCheckedInIngredients() {
		List<ItemInInventory> checkedInIngredients = new ArrayList<ItemInInventory>();
		for(ItemInInventory itemInInventory : dbItemsInInventory) {
			if (itemInInventory.getInUseDate() == null && itemInInventory.getUsedUpDate() == null) {
				checkedInIngredients.add(itemInInventory);
			}
		}
		return checkedInIngredients;
	}

	@Override
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

		return dbQBItems;
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
		return dbItemsInInventory;
	}

}
