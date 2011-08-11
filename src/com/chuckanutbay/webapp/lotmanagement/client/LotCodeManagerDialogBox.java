package com.chuckanutbay.webapp.lotmanagement.client;


import static com.chuckanutbay.webapp.common.client.CollectionsUtils.isEmpty;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newHorizontalPanel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newVerticalPanel;
import static com.chuckanutbay.webapp.common.client.IconUtil.CANCEL;
import static com.chuckanutbay.webapp.common.client.IconUtil.PRINT;
import static com.chuckanutbay.webapp.common.client.IconUtil.SAVE;
import static com.chuckanutbay.webapp.common.client.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.getColumn;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.getHeaderString;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.InventoryLotHeader;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public abstract class LotCodeManagerDialogBox extends DialogBox implements ClickHandler {
	private boolean isSaveable;
	private boolean isPrintable;
	protected CellPanel bodyPanel;
	protected CellPanel headerPanel;
	protected CellTable<InventoryLotDto> cellTable;
	private ScrollPanel scrollPanel;
	private final HorizontalPanel buttonPanel = newHorizontalPanel();
	private final Button cancelButton = createButtonWithIcon(CANCEL, "Cancel");
	private final Button saveButton = createButtonWithIcon(SAVE, "Save");
	private final Button printButton = createButtonWithIcon(PRINT, "Print"); 
	private VerticalPanel dialogBoxMainPanel;
	private static int SCROLL_PANEL_WIDTH = 950;
	private static int SCROLL_PANEL_HEIGHT = 400;
	protected List<InventoryLotDto> cellTableData;
	
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
		cellTable = getCellTable();
		setupBodyPanel(headerPanel, cellTable);
		setupScrollPanel();
		setupButtonPanel();
		setupDialogBoxMainPanel();
		super.center();
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
		this.clear();
		this.setWidget(bodyPanel);
		Window.print();
		this.clear();
		setupDialogBoxMainPanel();
	}

	protected static CellTable<InventoryLotDto> newMultiSelectionCellTable(InventoryLotHeader...headers) {
		return newCellTable(new MultiSelectionModel<InventoryLotDto>(), headers);
	}
	
	protected static CellTable<InventoryLotDto> newSingleSelectionCellTable(InventoryLotHeader...headers) {
		return newCellTable(new SingleSelectionModel<InventoryLotDto>(), headers);
	}
	
	private static CellTable<InventoryLotDto> newCellTable(SelectionModel<InventoryLotDto> selectionModel, InventoryLotHeader...headers) {
		CellTable<InventoryLotDto> newCellTable = new CellTable<InventoryLotDto>();
		newCellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<InventoryLotDto> createCheckboxManager());
		newCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		for (InventoryLotHeader header : headers) {
			newCellTable.addColumn(getColumn(header, selectionModel), getHeaderString(header));
		}
		newCellTable.setWidth("925px");
		return newCellTable;
	}
	
	protected void addSelectionChangeHandler(SelectionChangeEvent.Handler selectionChangeHandler) {
		cellTable.getSelectionModel().addSelectionChangeHandler(selectionChangeHandler);
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
	}
	
	private void setupScrollPanel() {
		scrollPanel = new ScrollPanel(bodyPanel);
		scrollPanel.setPixelSize(SCROLL_PANEL_WIDTH, SCROLL_PANEL_HEIGHT);
		scrollPanel.setAlwaysShowScrollBars(true);
	}
	
	private void setupDialogBoxMainPanel() {
		dialogBoxMainPanel = newVerticalPanel(scrollPanel, buttonPanel);
		dialogBoxMainPanel.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.setWidget(dialogBoxMainPanel);
	}
	
	public void populateCellTable(List<InventoryLotDto> inventoryLots) {
		cellTableData = inventoryLots;
		cellTable.setRowData(inventoryLots);
	}
	
	protected void addCellTableRow(InventoryLotDto...lots) {
		if(isEmpty(cellTableData)) {
			cellTableData = newArrayList(lots);
		} else {
			cellTableData.addAll(newArrayList(lots));
		}
		populateCellTable(cellTableData);
	}
	
	protected void removeCellTableRow(InventoryLotDto...lots) {
		if(!isEmpty(cellTableData)) {
			cellTableData.removeAll(newArrayList(lots));
		}
		populateCellTable(cellTableData);
	}
	
	abstract Widget[] getHeaderWidgets();
	
	abstract void updateDB();
	
	abstract CellTable<InventoryLotDto> getCellTable();
	
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == saveButton) {
			this.updateDB();
			this.clear();
			this.hide();
		} else if (event.getSource() == cancelButton) {
			this.clear();
			this.hide();
		} else if (event.getSource() == printButton) {
			printDialogBox();
		}
	}
}
