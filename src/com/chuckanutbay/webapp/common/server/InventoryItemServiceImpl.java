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

public class InventoryItemServiceImpl extends RemoteServiceServlet implements InventoryItemService {

	private static final long serialVersionUID = 1L;

	@Override
	public List<InventoryItemDto> getInventoryItems() {
		InventoryItemDao dao = new InventoryItemHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryItemDto);
	}
}
