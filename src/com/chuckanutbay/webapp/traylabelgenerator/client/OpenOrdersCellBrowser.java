package com.chuckanutbay.webapp.traylabelgenerator.client;

import java.util.Collection;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.google.gwt.user.cellview.client.CellBrowser;
import com.google.gwt.view.client.SelectionChangeEvent;

public class OpenOrdersCellBrowser extends CellBrowser {
	private OpenOrdersTreeModel viewModel;
	
	public OpenOrdersCellBrowser(OpenOrdersTreeModel viewModel) {
		super(viewModel, null);
		this.viewModel = viewModel;
		super.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	}

	
	
	public OpenOrdersCellBrowser setSize(int width, int height) {
		super.setPixelSize(width, height);
		return this;
	}
	
	public OpenOrdersCellBrowser setColumnWidth(int width) {
		super.setMinimumColumnWidth(width);
		return this;
	}
	
	public OpenOrdersCellBrowser setStyle(String style) {
		super.setStyleName(style);
		return this;
	}
	
	public OpenOrdersCellBrowser setSelectionChangeHandler(SelectionChangeEvent.Handler handler) {
		viewModel.getSelectionModel().addSelectionChangeHandler(handler);
		return this;
	}
	
	public Set<SalesOrderLineItemDto> getSelectedLineItems() {
		return viewModel.getSelectionModel().getSelectedSet();
	}
	
	/**
	 * Removes any data currently in the browser and then adds the parameter data.
	 * @param data
	 * @return
	 */
	public OpenOrdersCellBrowser replaceBrowserData(Collection<SalesOrderLineItemDto> data) {
		viewModel.setData(data);
		return this;
	}
	
}
