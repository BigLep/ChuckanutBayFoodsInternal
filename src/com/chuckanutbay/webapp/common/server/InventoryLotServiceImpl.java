package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.webapp.common.server.DtoUtils.fromInventoryLotDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.toInventoryLotDtoFunction;
import static com.chuckanutbay.webapp.common.server.DtoUtils.transform;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.businessobjects.dao.InventoryLotDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotHibernateDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotStickerColorDao;
import com.chuckanutbay.businessobjects.dao.InventoryLotStickerColorHibernateDao;
import com.chuckanutbay.webapp.common.client.InventoryLotService;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class InventoryLotServiceImpl extends RemoteServiceServlet implements InventoryLotService {

	private static final long serialVersionUID = 1L;


	@Override
	public void setAsInUse(List<InventoryLotDto> modifiedIngredientLotDtos) {
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
	public void setAsUsedUp(List<InventoryLotDto> usedUpIngredients) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		for (InventoryLotDto inventoryLotDto : usedUpIngredients) {
			InventoryLot inventoryLot = dao.findById(inventoryLotDto.getId());
			inventoryLot.setEndUseDatetime(inventoryLotDto.getEndUseDatetime());
		}
	}

	@Override
	public List<InventoryLotDto> getUnused() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return transform(dao.findUnused(), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getInUse() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return transform(dao.findInUse(), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getInUseOnDate(Date date) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return transform(dao.findInUseOnDate(date), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getByLotCode(String lotCode) {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return transform(dao.findLotCodeMatch(lotCode), DtoUtils.toInventoryLotDtoFunction);
	}

	@Override
	public List<InventoryLotDto> getAll() {
		InventoryLotDao dao = new InventoryLotHibernateDao();
		return transform(dao.findAll(), DtoUtils.toInventoryLotDtoFunction);
	}
	
	private static InventoryLotStickerColor getStickerColor(InventoryLot inventoryLot) {
		InventoryLotDao inventoryLotDao = new InventoryLotHibernateDao();
		InventoryLotStickerColorDao stickerColorDao = new InventoryLotStickerColorHibernateDao();
		InventoryLot match = inventoryLotDao.findActiveMatch(inventoryLot.getCode(), inventoryLot.getInventoryItem());
		if(match != null) {
			return match.getInventoryLotStickerColor();
		} else {
			List<InventoryLot> matches = inventoryLotDao.findByInventoryItem(inventoryLot.getInventoryItem());
			if (matches.isEmpty()) {
				return stickerColorDao.findFirstColor();
			} else {
				InventoryLot lot = matches.get(0);
				InventoryLotStickerColor color = lot.getInventoryLotStickerColor();
				stickerColorDao.refresh(color);
				return stickerColorDao.findNextColor(color);
			}
		}
	}

	@Override
	public InventoryLotDto setAsUnused(InventoryLotDto inventoryLotDto) {
		InventoryLotDao inventoryLotDao = new InventoryLotHibernateDao();
		InventoryLot inventoryLot = DtoUtils.fromInventoryLotDtoFunction.apply(inventoryLotDto);
		inventoryLot.setInventoryLotStickerColor(getStickerColor(inventoryLot));
		inventoryLotDao.makePersistent(inventoryLot);
		return toInventoryLotDtoFunction.apply(inventoryLot);
	}

	@Override
	public void removeUnused(List<InventoryLotDto> inventoryLotDtos) {
		InventoryLotDao inventoryLotDao = new InventoryLotHibernateDao();
		inventoryLotDao.makeTransient(transform(inventoryLotDtos, fromInventoryLotDtoFunction));
	}

}
