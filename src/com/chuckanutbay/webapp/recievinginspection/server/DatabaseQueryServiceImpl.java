package com.chuckanutbay.webapp.recievinginspection.server;

import static com.chuckanutbay.webapp.recievinginspection.client.RecievingInspectionUtil.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator; 
import java.util.List;

import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.webapp.recievinginspection.client.DatabaseQueryService;
import com.chuckanutbay.webapp.recievinginspection.client.RecievingInspectionUtil;
import com.chuckanutbay.webapp.recievinginspection.shared.InventoryItemDto;
import com.chuckanutbay.webapp.shared.RecievingInspectionDto;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DatabaseQueryServiceImpl extends RemoteServiceServlet implements DatabaseQueryService {

	private static final long serialVersionUID = 1L;
	private ArrayList<RecievingInspectionDto> recievingInspectionDtoList = newArrayList();
	
	@Override
	public List<InventoryItemDto> getInventoryItems() {
		InventoryItemDao dao = new InventoryItemHibernateDao();
		return DtoUtils.transform(dao.findAll(), DtoUtils.toInventoryItemDto);
	}

	public void setRecievingInspection(
			RecievingInspectionDto recievingInspectionDto) {
		recievingInspectionDtoList.add(recievingInspectionDto);
		
	}

	public List<RecievingInspectionDto> getRecievingInspection() {
		return recievingInspectionDtoList;
	}
	

	
}
