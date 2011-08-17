package com.chuckanutbay.webapp.common.server;

import java.util.List;

import com.chuckanutbay.webapp.common.client.TrayLabelService;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TrayLabelServiceImpl extends RemoteServiceServlet implements TrayLabelService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<SalesOrderLineItemDto> getSalesOrderLineItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrayLabelDto> getTrayLabelHistroy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrayLabelDto> setTrayLabels(List<TrayLabelDto> newTrayLabels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuickbooksItemDto> getQuickbooksItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentLotCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTrayLabel(TrayLabelDto trayLabel) {
		// TODO Auto-generated method stub
		
	}

}
