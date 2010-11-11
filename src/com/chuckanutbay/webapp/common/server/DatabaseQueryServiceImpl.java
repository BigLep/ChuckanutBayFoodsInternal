package com.chuckanutbay.webapp.common.server;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotHibernateDao;
import com.chuckanutbay.webapp.common.client.InventoryItemService;
import com.chuckanutbay.webapp.common.client.InventoryLotService;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseQueryServiceImpl extends RemoteServiceServlet implements InventoryItemService, InventoryLotService {

	private static final long serialVersionUID = 1L;

	@Override
	public List<InventoryItemDto> getInventoryItems() {
		InventoryItemDao dao = new InventoryItemHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryItemDto);
	}

	@Override
	public void setUnusedIngredientLots(final List<InventoryLotDto> ingreditentLotDtos) {
		List<InventoryLot> inventoryLots = Lists.transform(ingreditentLotDtos, DtoUtils.fromInventoryLotDto);
		InventoryLotDao dao = new InventoryLotHibernateDao();
		dao.makePersistent(inventoryLots);
	}

	@Override
	public void setInUseIngredientLots(List<InventoryLotDto> ingredientLotDtos) {
		List<InventoryLot> inventoryLots = Lists.transform(ingredientLotDtos, DtoUtils.fromInventoryLotDto);
		InventoryLotDao dao = new InventoryLotHibernateDao();
		dao.makePersistent(inventoryLots);
	}

	@Override
	public void setUsedUpInventoryLots(List<InventoryLotDto> usedUpIngredients) {
		List<InventoryLot> inventoryLots = Lists.transform(usedUpIngredients, DtoUtils.fromInventoryLotDto);
		InventoryLotDao dao = new InventoryLotHibernateDao();
		dao.makePersistent(inventoryLots);
	}

	@Override
	public List<InventoryLotDto> getUnusedIngredientLots() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findUnused(), DtoUtils.toInventoryLotDto);
	}

	@Override
	public List<InventoryLotDto> getInUseIngredientLots() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findInUse(), DtoUtils.toInventoryLotDto);
	}

	@Override
	public List<InventoryLotDto> getDateMatchInUseIngredients(Date date) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findInUseOnDate(date), DtoUtils.toInventoryLotDto);
	}

	@Override
	public List<InventoryLotDto> getLotCodeMatchIngredients(String lotCode) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findLotCodeMatch(lotCode), DtoUtils.toInventoryLotDto);
	}

	@Override
	public List<InventoryLotDto> getFullIngredientHistory() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryLotDto);
	}

}
