package com.chuckanutbay.webapp.common.server;

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
		List<InventoryLot> inventoryLots = Lists.transform(ingreditentLotDtos, DtoUtils.fromInventoryLotDtoFunction);
		InventoryLotDao dao = new InventoryLotHibernateDao();
		dao.makePersistent(inventoryLots);
	}

	@Override
	public void setInUseIngredientLots(List<InventoryLotDto> modifiedIngredientLotDtos) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		for (InventoryLotDto inventoryLotDto : modifiedIngredientLotDtos) {
			InventoryLot inventoryLot = dao.findById(inventoryLotDto.getId());
			inventoryLot.setStartUseDatetime(inventoryLotDto.getStartUseDatetime());
			for (InventoryLot inUse : dao.findInUse()) {
				if (inUse.getInventoryItem().getId().equals(inventoryLotDto.getInventoryItem().getId()) && inUse.getId() != inventoryLotDto.getId()) {
					inUse.setEndUseDatetime(new Date());
					break;
				}
			}
		}
	}

	@Override
	public void setUsedUpInventoryLots(List<InventoryLotDto> usedUpIngredients) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		for (InventoryLotDto inventoryLotDto : usedUpIngredients) {
			InventoryLot inventoryLot = dao.findById(inventoryLotDto.getId());
			inventoryLot.setEndUseDatetime(inventoryLotDto.getEndUseDatetime());
		}
	}

	@Override
	public List<InventoryLotDto> getUnusedIngredientLots() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findUnused(), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getInUseIngredientLots() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findInUse(), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getDateMatchInUseIngredients(Date date) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findInUseOnDate(date), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getLotCodeMatchIngredients(String lotCode) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findLotCodeMatch(lotCode), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getFullIngredientHistory() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryLotDtoFunction);
	}

}
