package com.chuckanutbay.webapp.common.server;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.dao.InventoryLotDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotHibernateDao;
import com.chuckanutbay.webapp.common.client.InventoryLotService;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class InventoryLotServiceImpl extends RemoteServiceServlet implements InventoryLotService {

	private static final long serialVersionUID = 1L;

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
		List<InventoryLot> modifiedInventoryLots = newArrayList();
		for (InventoryLot inventoryLot : inventoryLots) {
			for (InventoryLot checkedIn : dao.findUnused()) {
				if (checkedIn.getId() == inventoryLot.getId() && checkedIn.getStartUseDatetime() != inventoryLot.getStartUseDatetime()) {
					modifiedInventoryLots.add(inventoryLot);
					break;
				}
			}
		}
		for (InventoryLot modifiedInventoryLot : modifiedInventoryLots) {
			for (InventoryLot inUse : dao.findInUse()) {
				if (inUse.getInventoryItem().getId().equals(modifiedInventoryLot.getInventoryItem().getId())) {
					inUse.setEndUseDatetime(new Date());
					dao.makePersistent(inUse);
					break;
				}
			}
		}
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
