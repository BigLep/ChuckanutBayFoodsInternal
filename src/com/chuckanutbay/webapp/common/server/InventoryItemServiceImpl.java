package com.chuckanutbay.webapp.common.server;

import java.util.List;

import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.webapp.common.client.InventoryItemService;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class InventoryItemServiceImpl extends RemoteServiceServlet implements InventoryItemService {

	private static final long serialVersionUID = 1L;

	@Override
	public List<InventoryItemDto> getInventoryItems() {
		InventoryItemDao dao = new InventoryItemHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryItemDtoFunction);
	}
}
