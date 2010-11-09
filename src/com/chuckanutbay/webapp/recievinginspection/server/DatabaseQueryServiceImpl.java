package com.chuckanutbay.webapp.recievinginspection.server;

import static com.chuckanutbay.webapp.recievinginspection.client.RecievingInspectionUtil.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator; 
import java.util.List;

import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.webapp.recievinginspection.client.DatabaseQueryService;
import com.chuckanutbay.webapp.recievinginspection.client.RecievingInspectionUtil;
import com.chuckanutbay.webapp.recievinginspection.shared.InventoryItemDto;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseQueryServiceImpl extends RemoteServiceServlet implements DatabaseQueryService {

	private static final long serialVersionUID = 1L;

	@Override
	public List<InventoryItemDto> getInventoryItems() {
		log("I tried!");
		InventoryItemDao dao = new InventoryItemHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryItemDto);
	}

	
}
