package com.chuckanutbay.webapp.traylabelgenerator.client;

import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_CENTER;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_LEFT;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_RIGHT;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_BOTTOM;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_MIDDLE;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_TOP;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.largeButtonStyles;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newImage;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newLabel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newSimplePanel;
import static com.chuckanutbay.webapp.common.client.IconUtil.ADD;
import static com.chuckanutbay.webapp.common.client.IconUtil.ADD_LARGE;
import static com.chuckanutbay.webapp.common.client.IconUtil.EDIT_LARGE;
import static com.chuckanutbay.webapp.common.client.IconUtil.LOGO;
import static com.chuckanutbay.webapp.common.client.IconUtil.PRINT_LARGE;
import static com.chuckanutbay.webapp.common.client.IconUtil.REFRESH_LARGE;
import static com.chuckanutbay.webapp.traylabelgenerator.client.RpcHelper.createGetInventoryTrayLabelDtoCallback;
import static com.chuckanutbay.webapp.traylabelgenerator.client.RpcHelper.createSendTrayLabelsCallback;
import static com.chuckanutbay.webapp.traylabelgenerator.client.RpcHelper.createVoidCallback;
import static com.chuckanutbay.webapp.traylabelgenerator.client.RpcHelper.trayLabelService;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.EDIT_TRAY_LABELS;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.NEW_TRAY_LABELS;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.getColumns;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.getHeaderStrings;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.toOrderTrayLabelDto;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Set;

import com.chuckanutbay.webapp.common.client.CbCellTable;
import com.chuckanutbay.webapp.common.client.CbHorizontalPanel;
import com.chuckanutbay.webapp.common.client.CbIconButton;
import com.chuckanutbay.webapp.common.client.CbMultiWordSuggestOracle;
import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.chuckanutbay.webapp.common.shared.InventoryTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.OrderTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class TrayLabelGenerator implements EntryPoint, ClickHandler, TrayLabelTableUpdateHandler, Handler, TrayLabelServerCommunicator {
	private RootPanel rootPanel = RootPanel.get("TrayLabelGenerator");
	private CbHorizontalPanel headerPanel;
	private CbIconButton newTrayLabelsButton;
	private CbIconButton editTrayLabelsButton;
	private CbIconButton refreshButton;
	private CbIconButton printButton;
	private CbHorizontalPanel buttonsPanel;
	private CbVerticalPanel mainPanel;
	private static final String[] addInventoryButtonStyles = {"font-size: 20px", "font-family: tahoma, geneva, futura", "font-weight: bold", "padding: 1px"};
	private CbVerticalPanel newTrayLabelPanel;
	private static final int IPAD_HEIGHT_PX = 986;
	private static final int IPAD_WIDTH_PX = 788;
	private OpenOrdersCellBrowser openOrdersCellBrowser;
	private CbCellTable<TrayLabelDto> newTrayLabelsCellTable;
	private String defaultLotCode;
	private CbIconButton addInventoryButton;
	private CbHorizontalPanel inventoryTrayLabelsPanel;
	private CbHorizontalPanel defaultLotCodePanel;
	private TextBox defaultLotCodeTextBox = new TextBox();
	private CbMultiWordSuggestOracle suggestOracle;
	private SuggestBox inventorySuggestBox;
	private CbVerticalPanel editTrayLabelsPanel;
	private CbCellTable<TrayLabelDto> editTrayLabelsCellTable;

	@Override
	public void onModuleLoad() {
		getSalesOrderLineItemsFromServer();
		getCurrentLotCodeFromServer();
		getQuickbooksItemIdsFromServer();
		InitializeUi();
	}

	private void InitializeUi() {
		setupHeader();
		setupNewTrayLabelPanel();
		setupEditTrayLabelPanel();
		setupButtonsPanel();
		setupRootPanel();
	}

	private void setupRootPanel() {
		
		mainPanel = new CbVerticalPanel()
			.addWidget(headerPanel, V_ALIGN_TOP)
			.addWidget(newTrayLabelPanel, V_ALIGN_TOP)
			.addWidget(buttonsPanel, V_ALIGN_BOTTOM)
			.setHeight(IPAD_HEIGHT_PX);
		
		rootPanel.add(newSimplePanel(mainPanel, "backgroundPanel"));
	}

	private void setupButtonsPanel() {
		newTrayLabelsButton = new CbIconButton(ADD_LARGE, "New", this, largeButtonStyles);
		editTrayLabelsButton = new CbIconButton(EDIT_LARGE, "Edit", this, largeButtonStyles);
		refreshButton = new CbIconButton(REFRESH_LARGE, "Refresh", this, largeButtonStyles);
		printButton = new CbIconButton(PRINT_LARGE, "Print", this, largeButtonStyles);
		buttonsPanel = new CbHorizontalPanel()
			.addWidget(editTrayLabelsButton, H_ALIGN_LEFT)
			.addWidget(refreshButton, H_ALIGN_RIGHT)
			.addWidget(printButton, H_ALIGN_RIGHT)
			.setStyle("roundedCorners")
			.setWidth(IPAD_WIDTH_PX);
			
	}

	private void setupEditTrayLabelPanel() {
		editTrayLabelsCellTable = new CbCellTable<TrayLabelDto>()
			.setWidth(720)
			.setVisibleRows(15)
			.setSelectionModel(new MultiSelectionModel<TrayLabelDto>(), true);
			editTrayLabelsCellTable.addColumns(getHeaderStrings(EDIT_TRAY_LABELS), getColumns(editTrayLabelsCellTable, this, EDIT_TRAY_LABELS));
		editTrayLabelsPanel = new CbVerticalPanel()
			.addWidget(newLabel("Edit Tray Labels", "title"), H_ALIGN_CENTER)
			.addWidget(editTrayLabelsCellTable, H_ALIGN_CENTER)
			.addWidget(editTrayLabelsCellTable.getPager(), H_ALIGN_CENTER);
		
	}

	private void setupNewTrayLabelPanel() {
		openOrdersCellBrowser = new OpenOrdersCellBrowser(new OpenOrdersTreeModel())
			.setColumnWidth(235)
			.setSize(700, 260)
			.setStyle("openOrdersCellBrowser")
			.setSelectionChangeHandler(this);
		addInventoryButton = new CbIconButton(ADD, "Add", this, addInventoryButtonStyles);
		suggestOracle = new CbMultiWordSuggestOracle(newArrayList(""));
		inventorySuggestBox = new SuggestBox(suggestOracle);
		inventoryTrayLabelsPanel = new CbHorizontalPanel()
			.addWidget(newLabel("Inventory:", "inventoryLabel"))
			.addWidget(inventorySuggestBox, V_ALIGN_MIDDLE)
			.addWidget(addInventoryButton)
			.setCellSpacing(4)
			.setStyle("addInventoryPanel");
		defaultLotCodePanel = new CbHorizontalPanel()
			.addWidget(newLabel("Default Lot Code:", "inventoryLabel"))
			.addWidget(defaultLotCodeTextBox, V_ALIGN_MIDDLE)
			.setCellSpacing(4)
			.setStyle("addInventoryPanel");
		defaultLotCodeTextBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				defaultLotCode = defaultLotCodeTextBox.getText();
			}
			
		});
		newTrayLabelsCellTable = new CbCellTable<TrayLabelDto>()
			.setSize(700, 320)
			.setVisibleRows(10);
			newTrayLabelsCellTable.addColumns(getHeaderStrings(NEW_TRAY_LABELS), getColumns(newTrayLabelsCellTable, this, NEW_TRAY_LABELS));
		newTrayLabelPanel = new CbVerticalPanel()
			.addWidget(newLabel("Open Orders", "title"), H_ALIGN_CENTER)
			.addWidget(openOrdersCellBrowser, H_ALIGN_CENTER)
			.addWidget(new CbHorizontalPanel()
				.addWidget(defaultLotCodePanel, H_ALIGN_RIGHT)
				.addWidget(inventoryTrayLabelsPanel, H_ALIGN_RIGHT)
				.setCellSpacing(10), 
				H_ALIGN_CENTER)
			.addWidget(newLabel("Tray Labels", "title"), H_ALIGN_CENTER)
			.addWidget(newTrayLabelsCellTable, H_ALIGN_CENTER)
			.addWidget(newTrayLabelsCellTable.getPager(), H_ALIGN_CENTER)
			.setCellSpacing(5)
			.setWidth(IPAD_WIDTH_PX)
			.setHeight(IPAD_HEIGHT_PX);
	}
	


	private void setupHeader() {
		headerPanel = new CbHorizontalPanel()
			.addWidget(newImage(LOGO, 400), H_ALIGN_LEFT)
			.addWidget(newLabel("Tray Label Generator", "title"), H_ALIGN_RIGHT, V_ALIGN_MIDDLE)
			.setWidth(IPAD_WIDTH_PX);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == newTrayLabelsButton) {
			mainPanel.replaceWidget(editTrayLabelsPanel, newTrayLabelPanel, H_ALIGN_CENTER);
			buttonsPanel.remove(0);
			buttonsPanel.insert(editTrayLabelsButton, 0);
		} else if (event.getSource() == editTrayLabelsButton) {
			getTrayLabelHistoryFromServer();
			mainPanel.replaceWidget(newTrayLabelPanel, editTrayLabelsPanel, H_ALIGN_CENTER);
			buttonsPanel.remove(0);
			buttonsPanel.insert(newTrayLabelsButton, 0);
		} else if (event.getSource() == refreshButton) {
			Window.Location.reload();
		} else if (event.getSource() == printButton) {
			if (isEditMode()) {
				printTrayLabels(((MultiSelectionModel<TrayLabelDto>) editTrayLabelsCellTable.getSelectionModel()).getSelectedSet());
			} else {//It isn't edit mode
				sendTrayLabelsToServer(newArrayList(newTrayLabelsCellTable.getTableData()));
				newTrayLabelsCellTable.clearTableData();
			}
		} else if (event.getSource() == addInventoryButton) {
			getInventoryTrayLabelDto(inventorySuggestBox.getText());
		}
		
	}

	@Override
	public void onTrayLabelUpdate(TrayLabelDto updatedObject) {
		if (isEditMode()) {
			//The editTrayLabelsCellTable triggered this method call.
			updateTrayLabel(updatedObject);
		} else {
			//Do Nothing because the newTrayLabelsCellTable is what triggered this method call and its rows aren't in the database.
		}
	}
	
	private boolean isEditMode() {
		if (mainPanel.getWidgetIndex(editTrayLabelsPanel) > 0) {
			//The editTrayLabelsCellTable is visible.
			return true;
		} else {
			//The newTrayLabelsCellTable is visible.
			return false;
		}
	}

	@Override
	public void onSelectionChange(SelectionChangeEvent event) {
		List<TrayLabelDto> tableData = newTrayLabelsCellTable.getTableData();
		Set<SalesOrderLineItemDto> selectedLineItems = openOrdersCellBrowser.getSelectedLineItems();
		for (SalesOrderLineItemDto selectedLineItem : selectedLineItems) {
			OrderTrayLabelDto convertedLineItem = toOrderTrayLabelDto(selectedLineItem, defaultLotCode);
			if (!tableData.contains(convertedLineItem)) {
				newTrayLabelsCellTable.addTableData(convertedLineItem);
				return;
			}
		}
		for (TrayLabelDto trayLabel : tableData) {
			if (trayLabel instanceof OrderTrayLabelDto && !selectedLineItems.contains(trayLabel)) {
				newTrayLabelsCellTable.removeTableData(trayLabel);
				return;
			}
		}
	}

	@Override
	public void getSalesOrderLineItemsFromServer() {
		trayLabelService.getSalesOrderLineItems(RpcHelper.createGetSalesOrderLineItemsCallback(this));
	}

	@Override
	public void getTrayLabelHistoryFromServer() {
		trayLabelService.getTrayLabelHistroy(RpcHelper.createGetTrayLabelHistoryCallback(this));
	}

	@Override
	public void getQuickbooksItemIdsFromServer() {
		trayLabelService.getQuickbooksItemIds(RpcHelper.createGetQuickbooksItemIdsCallback(this));
	}

	@Override
	public void getCurrentLotCodeFromServer() {
		trayLabelService.getCurrentLotCode(RpcHelper.createGetCurrentLotCodeCallback(this));
	}

	@Override
	public void updateTrayLabel(TrayLabelDto trayLabel) {
		trayLabelService.updateTrayLabel(trayLabel, RpcHelper.createUpdateTrayLabelCallback(this));
	}

	@Override
	public void sendTrayLabelsToServer(List<TrayLabelDto> newTrayLabels) {
		for (TrayLabelDto trayLabel : newTrayLabels) {
			if (trayLabel.getCasesPerTray() == 0.0) {
				Window.alert("Tell Dave: 'The cases per tray value is not right for " + trayLabel.getQbItem().getId() + "!'");
			}
		}
		trayLabelService.setTrayLabels(newTrayLabels, createSendTrayLabelsCallback(this));
	}
	
	@Override
	public void printTrayLabels(Set<TrayLabelDto> trayLabels) {
		trayLabelService.printTrayLabels(trayLabels, createVoidCallback());
	}
	
	@Override
	public void getInventoryTrayLabelDto(String qbItemId) {
		trayLabelService.getInventoryTrayLabelDto(qbItemId, createGetInventoryTrayLabelDtoCallback(this));
	}
	
	@Override
	public void onSuccessfulGetSalesOrderLineItems(
			List<SalesOrderLineItemDto> result) {
		openOrdersCellBrowser.replaceBrowserData(result);

	}

	@Override
	public void onSuccessfulGetTrayLabelHistory(List<TrayLabelDto> trayLabels) {
		editTrayLabelsCellTable.replaceTableData(trayLabels);
	}

	@Override
	public void onSuccessfulGetQuickbooksItemIds(
			List<String> quickbooksItems) {
		suggestOracle.addAll(quickbooksItems);
	}

	@Override
	public void onSuccessfulGetCurrentLotCode(String lotCode) {
		defaultLotCode = lotCode;
		defaultLotCodeTextBox.setText(lotCode);
		Timer waitTenMin = new Timer() {

			@Override
			public void run() {
				getCurrentLotCodeFromServer();
			}
			
		};
		waitTenMin.schedule(1000 * 60 * 10); //10 min in millis = 1000 millis/sec * 60 sec/min * 10
	}

	@Override
	public void onSuccessfulUpdateTrayLabel() {
		//Do Nothing 
	}

	@Override
	public void onSuccessfulSendTrayLabelsToServer() {
		Window.Location.reload();
	}

	@Override
	public void onSuccessfulGetInventoryTrayLabelDto(
			InventoryTrayLabelDto result) {
		result.setLotCode(defaultLotCode);
		newTrayLabelsCellTable.addTableData(result);
	}

}
