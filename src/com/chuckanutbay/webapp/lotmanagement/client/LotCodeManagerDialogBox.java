package com.chuckanutbay.webapp.lotmanagement.client;


import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newHorizontalPanel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newVerticalPanel;
import static com.chuckanutbay.webapp.common.client.IconUtil.CANCEL;
import static com.chuckanutbay.webapp.common.client.IconUtil.PRINT;
import static com.chuckanutbay.webapp.common.client.IconUtil.SAVE;
import static com.chuckanutbay.webapp.common.client.IconUtil.newButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.getHeaderColumns;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.getHeaderStrings;

import java.util.List;

import com.chuckanutbay.webapp.common.client.CbCellTable;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.InventoryLotHeader;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class LotCodeManagerDialogBox extends DialogBox implements ClickHandler {
	private boolean isSaveable;
	private boolean isPrintable;
	protected CellPanel bodyPanel;
	protected ScrollPanel scrollPanel;
	protected CellPanel headerPanel;
	protected CbCellTable<InventoryLotDto> cellTable;
	protected SimplePager pager;
	private final HorizontalPanel buttonPanel = newHorizontalPanel();
	private final Button cancelButton = newButtonWithIcon(CANCEL, "Cancel");
	private final Button saveButton = newButtonWithIcon(SAVE, "Save");
	private final Button printButton = newButtonWithIcon(PRINT, "Print"); 
	private VerticalPanel dialogBoxMainPanel;
	private static int PANEL_WIDTH = 950;
	private static int PANEL_HEIGHT = 700;
	private static int ROWS_PER_PAGE = 20;
	
	public LotCodeManagerDialogBox() {
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.center();
	}
	public LotCodeManagerDialogBox(final String title, Boolean isSaveable, Boolean isPrintable) {
		this.setText(title);
		this.isSaveable = isSaveable;
		this.isPrintable = isPrintable;
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
	}
	
	@Override
	public void center() {
		setupHeader(getHeaderWidgets());
		setupCellTable();
		setupBodyPanel(headerPanel, cellTable, pager);
		setupButtonPanel();
		setupDialogBoxMainPanel();
		super.center();
	}
	
	private void setupCellTable() {
		cellTable = getCellTable();
		pager = cellTable.getPager();
	}
	
	private void setupButtonPanel() {
		LotCodeUtil.addClickHandler(this, cancelButton, saveButton, printButton);
		//Add Buttons to buttonPanel
		if (isSaveable) {
			buttonPanel.add(saveButton);
		}
		if (isPrintable) {
			buttonPanel.add(printButton);
		}
		buttonPanel.add(cancelButton);
		buttonPanel.setSpacing(5);
	}
	private void printDialogBox() {
		Window.alert("Sorry but the printing feature is not active yet!");
	}

	protected static CbCellTable<InventoryLotDto> newMultiSelectionCellTable(InventoryLotHeader...headers) {
		return newCellTable(new MultiSelectionModel<InventoryLotDto>(), headers);
	}
	
	protected static CbCellTable<InventoryLotDto> newSingleSelectionCellTable(InventoryLotHeader...headers) {
		return newCellTable(new SingleSelectionModel<InventoryLotDto>(), headers);
	}
	
	private static CbCellTable<InventoryLotDto> newCellTable(SelectionModel<InventoryLotDto> selectionModel, InventoryLotHeader...headers) {
		CbCellTable<InventoryLotDto> newCellTable = new CbCellTable<InventoryLotDto>()
				.setSelectionModel(selectionModel, true)
				.addColumns(getHeaderStrings(headers), getHeaderColumns(selectionModel, headers))
				.setVisibleRows(ROWS_PER_PAGE)
				.setWidth(925);
		return newCellTable;
	}
	
	protected void addSelectionChangeHandler(SelectionChangeEvent.Handler selectionChangeHandler) {
		cellTable.addSelectionChangeHandler(selectionChangeHandler);
	}
	
	@SuppressWarnings("unchecked")
	protected SelectionModel<InventoryLotDto> getSelectionModel() {
		return (SelectionModel<InventoryLotDto>) cellTable.getSelectionModel();
	}
	
	private void setupHeader(Widget...widgets) {
		headerPanel = newHorizontalPanel(widgets);
		headerPanel.setStyleName("headerPanel");
	}
	
	private void setupBodyPanel(Widget...widgets) {
		bodyPanel = newVerticalPanel(widgets);
		bodyPanel.setSpacing(5);
		bodyPanel.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		scrollPanel = new ScrollPanel(bodyPanel);
		scrollPanel.setPixelSize(PANEL_WIDTH, PANEL_HEIGHT);
		scrollPanel.setAlwaysShowScrollBars(false);
	}
	
	private void setupDialogBoxMainPanel() {
		dialogBoxMainPanel = newVerticalPanel(scrollPanel, buttonPanel);
		dialogBoxMainPanel.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.setWidget(dialogBoxMainPanel);
	}
	
	public void populateCellTable(List<InventoryLotDto> inventoryLots) {
		cellTable.setTableData(inventoryLots);
	}
	
	protected void addCellTableRow(InventoryLotDto...lots) {
		cellTable.addTableData(lots);
	}
	
	protected void removeCellTableRow(InventoryLotDto...lots) {
		cellTable.removeTableData(lots);
	}
	
	protected List<InventoryLotDto> getCellTableData() {
		return cellTable.getTableData();
	}
	
	abstract Widget[] getHeaderWidgets();
	
	protected void onSave() {}
	
	protected void onCancel() {}
	
	abstract CbCellTable<InventoryLotDto> getCellTable();
	
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == saveButton) {
			onSave();
			clear();
			hide();
		} else if (event.getSource() == cancelButton) {
			onCancel();
			clear();
			hide();
		} else if (event.getSource() == printButton) {
			printDialogBox();
		}
	}
}

