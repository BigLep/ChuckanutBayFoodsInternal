package com.chuckanutbay.webapp.traylabelgenerator.client;

import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_CENTER;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_LEFT;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_RIGHT;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_BOTTOM;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_MIDDLE;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_TOP;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newImage;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newLabel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newSimplePanel;
import static com.chuckanutbay.webapp.common.client.IconUtil.ADD;
import static com.chuckanutbay.webapp.common.client.IconUtil.ADD_LARGE;
import static com.chuckanutbay.webapp.common.client.IconUtil.EDIT_LARGE;
import static com.chuckanutbay.webapp.common.client.IconUtil.LOGO;
import static com.chuckanutbay.webapp.common.client.IconUtil.PRINT_LARGE;
import static com.chuckanutbay.webapp.common.client.IconUtil.REFRESH_LARGE;
import static com.chuckanutbay.webapp.traylabelgenerator.client.RpcHelper.trayLabelService;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.EDIT_TRAY_LABELS;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.NEW_TRAY_LABELS;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.getColumns;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.getHeaderStrings;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.newInventoryTrayLabel;
import static com.chuckanutbay.webapp.traylabelgenerator.client.TrayLabelGeneratorUtil.toTrayLabelDto;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.chuckanutbay.webapp.common.client.CbCellTable;
import com.chuckanutbay.webapp.common.client.CbHorizontalPanel;
import com.chuckanutbay.webapp.common.client.CbIconButton;
import com.chuckanutbay.webapp.common.client.CbMultiWordSuggestOracle;
import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.chuckanutbay.webapp.common.client.ServiceUtils.VoidAsyncCallback;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class TrayLabelGenerator implements EntryPoint, ClickHandler, TrayLabelTableUpdateHandler, Handler {
	private RootPanel rootPanel = RootPanel.get("TrayLabelGenerator");
	private CbHorizontalPanel headerPanel;
	private CbIconButton newTrayLabelsButton;
	private CbIconButton editTrayLabelsButton;
	private CbIconButton refreshButton;
	private CbIconButton printButton;
	private CbHorizontalPanel buttonsPanel;
	private CbVerticalPanel mainPanel;
	private static final String[] buttonStyles = {"font-size: 40px", "font-family: tahoma, geneva", "font-weight: bold", "padding: 4px"};
	private static final String[] addInventoryButtonStyles = {"font-size: 20px", "font-family: tahoma, geneva", "font-weight: bold", "padding: 1px"};
	private CbVerticalPanel newTrayLabelPanel;
	private static final int IPAD_HEIGHT_PX = 946;
	private static final int IPAD_WIDTH_PX = 768;
	private OpenOrdersCellBrowser openOrdersCellBrowser;
	private CbCellTable<TrayLabelDto> newTrayLabelsCellTable;
	private String currentLotCode = "2B168A1";
	private CbIconButton addInventoryButton;
	private CbHorizontalPanel inventoryTrayLabelsPanel;
	private List<QuickbooksItemDto> quickbooksItems = generateQuickbooksItems();
	private List<TrayLabelDto> trayLabelDtos = generateTrayLabelDtos();
	private CbMultiWordSuggestOracle suggestOracle;
	private SuggestBox inventorySuggestBox;
	private CbVerticalPanel editTrayLabelsPanel;
	private CbCellTable<TrayLabelDto> editTrayLabelsCellTable;

	@Override
	public void onModuleLoad() {
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
		//Setup Window
		Window.enableScrolling(false);
		
		mainPanel = new CbVerticalPanel()
			.addWidget(headerPanel, V_ALIGN_TOP)
			.addWidget(newTrayLabelPanel, V_ALIGN_TOP)
			.addWidget(buttonsPanel, V_ALIGN_BOTTOM)
			.setHeight(IPAD_HEIGHT_PX);
		
		rootPanel.add(newSimplePanel(mainPanel, "backgroundPanel"));
	}

	private void setupButtonsPanel() {
		newTrayLabelsButton = new CbIconButton(ADD_LARGE, "New", this, buttonStyles);
		editTrayLabelsButton = new CbIconButton(EDIT_LARGE, "Edit", this, buttonStyles);
		refreshButton = new CbIconButton(REFRESH_LARGE, "Refresh", this, buttonStyles);
		printButton = new CbIconButton(PRINT_LARGE, "Print", this, buttonStyles);
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
			.setTableData(trayLabelDtos)
			.setSelectionModel(new MultiSelectionModel<TrayLabelDto>(), true);
			editTrayLabelsCellTable.addColumns(getHeaderStrings(EDIT_TRAY_LABELS), getColumns(editTrayLabelsCellTable, this, EDIT_TRAY_LABELS));
		editTrayLabelsPanel = new CbVerticalPanel()
			.addWidget(newLabel("Edit Tray Labels", "title"), H_ALIGN_CENTER)
			.addWidget(editTrayLabelsCellTable, H_ALIGN_CENTER)
			.addWidget(editTrayLabelsCellTable.getPager(), H_ALIGN_CENTER);
		
	}

	private void setupNewTrayLabelPanel() {
		openOrdersCellBrowser = new OpenOrdersCellBrowser(new OpenOrdersTreeModel(generateOpenOrders()))
			.setColumnWidth(235)
			.setSize(700, 260)
			.setStyle("openOrdersCellBrowser")
			.setSelectionChangeHandler(this);
		addInventoryButton = new CbIconButton(ADD, "Add", this, addInventoryButtonStyles);
		suggestOracle = new CbMultiWordSuggestOracle(newArrayList("2201-12", "27001-6"));
		inventorySuggestBox = new SuggestBox(suggestOracle);
		inventoryTrayLabelsPanel = new CbHorizontalPanel()
			.addWidget(newLabel("Inventory:", "inventoryLabel"))
			.addWidget(inventorySuggestBox, V_ALIGN_MIDDLE)
			.addWidget(addInventoryButton)
			.setCellSpacing(4)
			.setStyle("addInventoryPanel");
		newTrayLabelsCellTable = new CbCellTable<TrayLabelDto>()
			.setSize(700, 320)
			.setVisibleRows(10);
			newTrayLabelsCellTable.addColumns(getHeaderStrings(NEW_TRAY_LABELS), getColumns(newTrayLabelsCellTable, this, NEW_TRAY_LABELS));
		newTrayLabelPanel = new CbVerticalPanel()
			.addWidget(newLabel("Open Orders", "title"), H_ALIGN_CENTER)
			.addWidget(openOrdersCellBrowser, H_ALIGN_CENTER)
			.addWidget(inventoryTrayLabelsPanel, H_ALIGN_RIGHT)
			.addWidget(newLabel("Tray Labels", "title"), H_ALIGN_CENTER)
			.addWidget(newTrayLabelsCellTable, H_ALIGN_CENTER)
			.addWidget(newTrayLabelsCellTable.getPager(), H_ALIGN_CENTER)
			.setCellSpacing(5)
			.setWidth(IPAD_WIDTH_PX);
	}
	
	private List<TrayLabelDto> generateTrayLabelDtos() {
		return newArrayList(
				new TrayLabelDto(1, new SalesOrderLineItemDto(1, 20, new QuickbooksItemDto("2201-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(2, new SalesOrderLineItemDto(2, 20, new QuickbooksItemDto("2101-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(3, new SalesOrderLineItemDto(3, 20, new QuickbooksItemDto("27001-6", "NewYork", 6, 1), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(4, new SalesOrderLineItemDto(4, 20, new QuickbooksItemDto("1601-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(5, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(6, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(2, "Alpine Distributors", new Date())), currentLotCode, 10),
				new TrayLabelDto(1, new SalesOrderLineItemDto(1, 20, new QuickbooksItemDto("2201-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(2, new SalesOrderLineItemDto(2, 20, new QuickbooksItemDto("2101-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(3, new SalesOrderLineItemDto(3, 20, new QuickbooksItemDto("27001-6", "NewYork", 6, 1), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(4, new SalesOrderLineItemDto(4, 20, new QuickbooksItemDto("1601-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(5, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(6, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(2, "Alpine Distributors", new Date())), currentLotCode, 10),
				new TrayLabelDto(1, new SalesOrderLineItemDto(1, 20, new QuickbooksItemDto("2201-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(2, new SalesOrderLineItemDto(2, 20, new QuickbooksItemDto("2101-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(3, new SalesOrderLineItemDto(3, 20, new QuickbooksItemDto("27001-6", "NewYork", 6, 1), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(4, new SalesOrderLineItemDto(4, 20, new QuickbooksItemDto("1601-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(5, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(6, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(2, "Alpine Distributors", new Date())), currentLotCode, 10),
				new TrayLabelDto(1, new SalesOrderLineItemDto(1, 20, new QuickbooksItemDto("2201-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(2, new SalesOrderLineItemDto(2, 20, new QuickbooksItemDto("2101-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(3, new SalesOrderLineItemDto(3, 20, new QuickbooksItemDto("27001-6", "NewYork", 6, 1), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(4, new SalesOrderLineItemDto(4, 20, new QuickbooksItemDto("1601-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(5, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10),
				new TrayLabelDto(6, new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(2, "Alpine Distributors", new Date())), currentLotCode, 10),
				new TrayLabelDto(7, new SalesOrderLineItemDto(6, 20, new QuickbooksItemDto("2103-12", "VeryBerry", 12, 3), new SalesOrderDto(1, "Costco", new Date())), currentLotCode, 10));
	}

	private List<SalesOrderLineItemDto> generateOpenOrders() {
		return newArrayList(
				new SalesOrderLineItemDto(1, 20, new QuickbooksItemDto("2201-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())),
				new SalesOrderLineItemDto(2, 20, new QuickbooksItemDto("2101-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())),
				new SalesOrderLineItemDto(3, 20, new QuickbooksItemDto("27001-6", "NewYork", 6, 1), new SalesOrderDto(1, "Costco", new Date())),
				new SalesOrderLineItemDto(4, 20, new QuickbooksItemDto("1601-12", "NewYork", 12, 3), new SalesOrderDto(1, "Costco", new Date())),
				new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(1, "Costco", new Date())),
				new SalesOrderLineItemDto(5, 20, new QuickbooksItemDto("2203-12", "VeryBerry", 12, 3), new SalesOrderDto(2, "Alpine Distributors", new Date())),
				new SalesOrderLineItemDto(6, 20, new QuickbooksItemDto("2103-12", "VeryBerry", 12, 3), new SalesOrderDto(1, "Costco", new Date())));
	}
	
	private List<QuickbooksItemDto> generateQuickbooksItems() {
		return newArrayList(
				new QuickbooksItemDto("2201-12", "Place in box", "NewYork", "3Inch", 12, 3, true),
				new QuickbooksItemDto("27001-6", null, "NewYork", "7Inch", 6, 1, false));
	}

	private void setupHeader() {
		headerPanel = new CbHorizontalPanel()
			.addWidget(newImage(LOGO, 400), H_ALIGN_LEFT)
			.addWidget(newLabel("Tray Label Generator", "title"), H_ALIGN_RIGHT, V_ALIGN_MIDDLE)
			.setWidth(IPAD_WIDTH_PX);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == newTrayLabelsButton) {
			mainPanel.replaceWidget(editTrayLabelsPanel, newTrayLabelPanel, H_ALIGN_CENTER);
			buttonsPanel.remove(0);
			buttonsPanel.insert(editTrayLabelsButton, 0);
		} else if (event.getSource() == editTrayLabelsButton) {
			mainPanel.replaceWidget(newTrayLabelPanel, editTrayLabelsPanel, H_ALIGN_CENTER);
			buttonsPanel.remove(0);
			buttonsPanel.insert(newTrayLabelsButton, 0);
		} else if (event.getSource() == refreshButton) {
			Window.Location.reload();
		} else if (event.getSource() == printButton) {
			//TODO: print button
		} else if (event.getSource() == addInventoryButton) {
			newTrayLabelsCellTable.addTableData(newInventoryTrayLabel(inventorySuggestBox.getText(), currentLotCode, quickbooksItems));
		}
		
	}

	@Override
	public void onTrayLabelUpdate(TrayLabelDto updatedObject) {
		if (isEditMode()) {
			//The editTrayLabelsCellTable triggered this method call.
			trayLabelService.updateTrayLabel(updatedObject, new VoidAsyncCallback<Void>());
		} else {
			//Do Nothing because the newTrayLabelsCellTable is what triggered this method call and its rows aren't in the database.
		}
	}
	
	private boolean isEditMode() {
		if (mainPanel.getWidgetIndex(editTrayLabelsButton) > 0) {
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
			TrayLabelDto convertedLineItem = toTrayLabelDto(selectedLineItem, currentLotCode);
			if (!tableData.contains(convertedLineItem)) {
				newTrayLabelsCellTable.addTableData(convertedLineItem);
				return;
			}
		}
		for (TrayLabelDto trayLabel : tableData) {
			if (trayLabel.getSalesOrderLineItemDto().getSalesOrderDto() == null) {
				//It is an inventory label; Do Nothing.
			} else if (!selectedLineItems.contains(trayLabel)) {
				newTrayLabelsCellTable.removeTableData(trayLabel);
				return;
			}
		}
	}

}
