package com.chuckanutbay.webapp.common.client;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED;

import java.util.Collection;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;

public class CbCellTable<T> extends CellTable<T> {
	
	private SimplePager pager = new SimplePager();
	private ListDataProvider<T> dataProvider = new ListDataProvider<T>();
	
	public CbCellTable() {
		super();
		pager.setDisplay(this);
		dataProvider.addDataDisplay(this);
		this.setKeyboardSelectionPolicy(ENABLED);
	}
	
	public CbCellTable(ProvidesKey<T> keyProvider) {
		super(keyProvider);
		pager.setDisplay(this);
		dataProvider.addDataDisplay(this);
		this.setKeyboardSelectionPolicy(ENABLED);
	}

	public CbCellTable<T> setSize(int width, int height) {
		super.setPixelSize(width, height);
		return this;
	}
	
	public CbCellTable<T> setWidth(int width) {
		super.setWidth(width + "px");
		return this;
	}
	
	public CbCellTable<T> addColumn(String title, Column<T,?> column) {
		super.addColumn(column, title);
		return this;
	}
	
	/**
	 * Insure that the index of each title matches the index of the respective column
	 * @param titles
	 * @param columns
	 * @return
	 */
	public CbCellTable<T> addColumns(List<String> titles, List<Column<T,?>> columns) {
		for (int i = 0; i < columns.size(); i++) {
			this.addColumn(titles.get(i), columns.get(i));
		}
		return this;
	}
	
	public CbCellTable<T> setSelectionModel(SelectionModel<T> model, boolean withCheckBoxManager) {
		if (withCheckBoxManager) {
			super.setSelectionModel(model, DefaultSelectionEventManager.<T> createCheckboxManager());
		}
		super.setSelectionModel(model);
		return this;
	}
	
	public CbCellTable<T> addSelectionChangeHandler(SelectionChangeEvent.Handler selectionChangeHandler) {
		super.getSelectionModel().addSelectionChangeHandler(selectionChangeHandler);
		return this;
	}
	
	public CbCellTable<T> setVisibleRows(int rowsPerPage) {
		super.setPageSize(rowsPerPage);
		return this;
	}
	
	public SimplePager getPager() {
		return pager;
	}
	
	public List<T> getTableData() {
		return dataProvider.getList();
	}
	
	public CbCellTable<T> setTableData(List<T> data) {
		dataProvider.setList(data);
		return this;
	}
	
	public CbCellTable<T> addTableData(T...data) {
		List<T> tableData = dataProvider.getList();
		for (T row : data) {
			tableData.add(row);
		}
		return this;
	}
	
	public CbCellTable<T> addTableData(Collection<T> data) {
		dataProvider.getList().addAll(data);
		return this;
	}
	
	public CbCellTable<T> removeTableData(T...data) {
		List<T> tableData = dataProvider.getList();
		for (T row : data) {
			if (tableData.contains(row)) {
				tableData.remove(row);
			}
		}
		return this;
	}
	
}
