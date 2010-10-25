package com.chuckanutbay.webapp.lotmanagement.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotHibernateDao;
import com.chuckanutbay.webapp.lotmanagement.client.DatabaseQueryService;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseQueryServiceImpl extends RemoteServiceServlet implements DatabaseQueryService {

	private static final long serialVersionUID = 1L;

	private final List<InventoryLotDto> dbItemsInInventory = new ArrayList<InventoryLotDto>();

	@Override
	public List<InventoryItemDto> getInventoryItems() {
		InventoryItemDao dao = new InventoryItemHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryItemDto);
	}

	@Override
	public void setCheckedInIngredients(final List<InventoryLotDto> ingreditentLotDtos) {
		List<InventoryLot> inventoryLots = Lists.transform(ingreditentLotDtos, DtoUtils.fromInventoryLotDto);
		InventoryLotDao dao = new InventoryLotHibernateDao();
		dao.makePersistent(inventoryLots);
	}

	@Override
	public List<InventoryLotDto> getCheckedInIngredients() {
		List<InventoryLotDto> checkedInIngredients = new ArrayList<InventoryLotDto>();
		for(InventoryLotDto itemInInventory : dbItemsInInventory) {
			if (itemInInventory.getStartUseDatetime() == null && itemInInventory.getEndUseDatetime() == null) {
				checkedInIngredients.add(itemInInventory);
			}
		}
		return checkedInIngredients;
	}

	@Override
	public List<InventoryLotDto> getInUseIngredients() {
		List<InventoryLotDto> inUseIngredients = new ArrayList<InventoryLotDto>();
		for(InventoryLotDto itemInInventory : dbItemsInInventory) {
			if (itemInInventory.getStartUseDatetime() != null && itemInInventory.getEndUseDatetime() == null) {
				inUseIngredients.add(itemInInventory);
			}
		}
		return inUseIngredients;
	}

	@Override
	public void setInUseIngredients(List<InventoryLotDto> inUseIngredients) {
		Iterator<InventoryLotDto> iterator = dbItemsInInventory.iterator();
		while (iterator.hasNext()) {
			InventoryLotDto itemInInventory = iterator.next();
			if (itemInInventory.getStartUseDatetime() == null && itemInInventory.getEndUseDatetime() == null) {
				iterator.remove();
			}
		}
		dbItemsInInventory.addAll(inUseIngredients);
	}

	@Override
	public void setUsedUpIngredients(List<InventoryLotDto> usedUpIngredients) {
		Iterator<InventoryLotDto> iterator = dbItemsInInventory.iterator();
		while (iterator.hasNext()) {
			InventoryLotDto itemInInventory = iterator.next();
			if (itemInInventory.getStartUseDatetime() != null && itemInInventory.getEndUseDatetime() == null) {
				iterator.remove();
			}
		}
		dbItemsInInventory.addAll(usedUpIngredients);

	}

	@Override
	public List<InventoryLotDto> getDateMatchInUseIngredients(Date date) {
		List<InventoryLotDto> dateMatchList = new ArrayList<InventoryLotDto>();
		for (InventoryLotDto possibleDateMatch : dbItemsInInventory) {
			if (possibleDateMatch.getStartUseDatetime() != null && possibleDateMatch.getEndUseDatetime() != null) {
				long searchDate = date.getTime();
				long inUseDate = possibleDateMatch.getStartUseDatetime().getTime();
				long usedUpDate = possibleDateMatch.getEndUseDatetime().getTime();
				if (searchDate >= inUseDate && searchDate <= usedUpDate) {
					dateMatchList.add(possibleDateMatch);
				}
			}
			else if (possibleDateMatch.getStartUseDatetime() != null) {
				long searchDate = date.getTime();
				long inUseDate = possibleDateMatch.getStartUseDatetime().getTime();
				if (searchDate >= inUseDate) {
					dateMatchList.add(possibleDateMatch);
				}
			}
		}
		return dateMatchList;
	}

	@Override
	public List<InventoryLotDto> getLotCodeMatchIngredients(String lotCode) {
		List<InventoryLotDto> lotCodeMatchList = new ArrayList<InventoryLotDto>();
		lotCode.toUpperCase().trim();
		if (lotCode.matches("")) {
		}
		// lot code must be between 1 and 20 chars that are numbers, or letters.
		else if (!lotCode.matches("[0-9A-Z\\s]{1,20}")) {
	    	Window.alert("'" + lotCode + "' is not a valid Lot Code.");
	    }
		else {
			for (InventoryLotDto possibleLotCodeMatch : dbItemsInInventory) {
				if (lotCode.equals(possibleLotCodeMatch.getCode())) {
					lotCodeMatchList.add(possibleLotCodeMatch);
				}
			}
		}
		return lotCodeMatchList;
	}

	@Override
	public List<InventoryLotDto> getFullIngredientHistory() {
		return dbItemsInInventory;
	}

}
