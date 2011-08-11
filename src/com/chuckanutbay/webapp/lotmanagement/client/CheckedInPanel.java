package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.common.client.CollectionsUtils.isEmpty;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.populateFlexTableRow;
import static com.chuckanutbay.webapp.common.client.IconUtil.ADD;
import static com.chuckanutbay.webapp.common.client.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryItemService;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryLotService;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.CHECKED_IN_PANEL_HEADERS;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.CHECK_IN_TITLE;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.validateAndFormatLotCode;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.VOID_CALLBACK;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.createInventoryItemServiceCallback;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CheckedInPanel extends LotCodeManagerDialogBox implements ClickHandler, ChangeHandler, InventoryItemServerCommunicator, SelectionChangeEvent.Handler {
	private final FlexTable header = new FlexTable();
		private final TextBox lotCodeTextBox = new TextBox();
		private final ListBox ingredientListBox = new ListBox();
		private final TextBox ingredientCodeTextBox = new TextBox();
		private final TextBox ingredientQuantityTextBox = new TextBox();
		private final DateBox dateBox = new DateBox();
		private final Button addIngredientButton = createButtonWithIcon(ADD, "Add");
		private List<InventoryItemDto> inventoryItems;
		private List<InventoryLotDto> newInventoryLots = newArrayList();
	
	
	public CheckedInPanel() {
		super(CHECK_IN_TITLE, true, true);
		createInventoryItemService().getInventoryItems(createInventoryItemServiceCallback(this));
		center();
		addSelectionChangeHandler(this);
		lotCodeTextBox.selectAll();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		super.onClick(event);
		if (event.getSource() == addIngredientButton) {
			String lotCode = validateAndFormatLotCode(lotCodeTextBox.getText());
			if (lotCode == null) {
		    	lotCodeTextBox.selectAll();
		    	return;
		    }

		    // quantity must be between 1 and 10 chars that are numbers.
			String quantity = ingredientQuantityTextBox.getText();
			if (!quantity.matches("[0-9\\s]{1,20}")) {
		    	Window.alert("'" + quantity + "' is not a number.");
		    	ingredientQuantityTextBox.selectAll();
		    	return;
		    }
			
			if(ingredientListBox.getItemCount() > 0) {
				InventoryLotDto newLot = new InventoryLotDto(
						lotCodeTextBox.getText().toUpperCase().trim(), 
						new InventoryItemDto(
							ingredientCodeTextBox.getText(), 
							ingredientListBox.getItemText(ingredientListBox.getSelectedIndex())), 
						Integer.parseInt(ingredientQuantityTextBox.getText()), 
						dateBox.getValue());
				addCellTableRow(newLot);
				newInventoryLots.add(newLot);
			}
		}
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		if (event.getSource() instanceof TextBox) {
			matchIngredientDescriptionToCode();
		} else if (event.getSource() instanceof ListBox) {
			matchIngredientCodeToDescription();
		}
	}

	private void matchIngredientDescriptionToCode() {
		if (!isEmpty(inventoryItems)) {
			for (InventoryItemDto item : inventoryItems) {
				if (item.getId().equals(ingredientCodeTextBox.getText())) {
					ingredientListBox.setItemSelected(inventoryItems.indexOf(item), true);
					break;
				}
			}
		}
	}
	
	private void matchIngredientCodeToDescription() {
		if (!isEmpty(inventoryItems)) {
			for (InventoryItemDto item : inventoryItems) {
				if (item.getDescription().equals(ingredientListBox.getItemText(ingredientListBox.getSelectedIndex()))) {
					ingredientCodeTextBox.setText(item.getId());
					break;
				}
			}
		}
	}

	@Override
	void updateDB() {
		createInventoryLotService().setUnusedInventoryLots(newInventoryLots, VOID_CALLBACK);
	}

	@Override
	public void onSuccessfulGetInventoryItemsFromDatabase(
			List<InventoryItemDto> dbQBItemList) {
		if(!isEmpty(dbQBItemList)) {
			inventoryItems = dbQBItemList;
			for (InventoryItemDto item : dbQBItemList) {
				ingredientListBox.addItem(item.getDescription());
			}
			matchIngredientCodeToDescription();
		}
	}

	@Override
	Widget[] getHeaderWidgets() {
		//Set Up lotCodeTextBox
		lotCodeTextBox.setText("Lot Code...");
		lotCodeTextBox.setStyleName("lotCodeTextBox");
		ingredientListBox.setStyleName("ingredientListBox");
		//Set Up ingredientCodeTextBox
		ingredientCodeTextBox.setStyleName("ingredientCodeTextBox");
		//Set Up ingredientQuantityTextBox
		ingredientQuantityTextBox.setStyleName("ingredientCodeTextBox");
		//Set Up dateBox
		dateBox.setFormat(new DateBox.DefaultFormat(DATE_FORMAT));
		dateBox.setValue(new Date(), true);
		dateBox.setStyleName("dateBox");
		//Add Handlers
		addIngredientButton.addClickHandler(this);
		ingredientListBox.addChangeHandler(this);
		ingredientCodeTextBox.addChangeHandler(this);
		//Add components to newCheckedInIngredientPanel
		populateFlexTableRow(header, 0, 0, "Lot Code", "Description", "Part #", "Qty", "Date");
		header.setWidget(0,5,addIngredientButton);
		header.getFlexCellFormatter().setRowSpan(0, 5, 2);
		populateFlexTableRow(header, 1, 0, lotCodeTextBox, ingredientListBox, ingredientCodeTextBox, ingredientQuantityTextBox, dateBox);
		header.getRowFormatter().addStyleName(0, "boldText");
		header.setWidth("900px");
		return new Widget[] {header};
	}

	@Override
	CellTable<InventoryLotDto> getCellTable() {
		return newSingleSelectionCellTable(CHECKED_IN_PANEL_HEADERS);
	}
	
	@Override
	public void onSelectionChange(SelectionChangeEvent event) {
		InventoryLotDto dto = ((SingleSelectionModel<InventoryLotDto>) getSelectionModel()).getSelectedObject();
		removeCellTableRow(dto);
		newInventoryLots.remove(dto);
		cellTable.redraw();
	}
}



