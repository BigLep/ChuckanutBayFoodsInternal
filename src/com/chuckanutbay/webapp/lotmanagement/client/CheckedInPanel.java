package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.common.client.IconUtil.ADD;
import static com.chuckanutbay.webapp.common.client.IconUtil.DELETE;
import static com.chuckanutbay.webapp.common.client.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.common.client.IconUtil.newImage;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class CheckedInPanel extends LotCodeManagerPanel implements ClickHandler, ChangeHandler {
	//Checked-In Components
	private final VerticalPanel checkedInIngredientPanel = new VerticalPanel();
	private final FlexTable newCheckedInFlexTable = new FlexTable();
	private final TextBox lotCodeTextBox = new TextBox();
	private final ListBox ingredientListBox = new ListBox();
	private final TextBox ingredientCodeTextBox = new TextBox();
	private final TextBox ingredientQuantityTextBox = new TextBox();
	private final DateBox dateBox = new DateBox();
	private final Button addIngredientButton = new Button();
	private final FlexTable checkedInIngredientFlexTable = new FlexTable();
	private DialogBox dialogBox;
	private final List<InventoryItemDto> inventoryItemList = newArrayList();
	private final List<InventoryLotDto> checkedInIngredientList = newArrayList();
	private final List<String> lotCodesList = newArrayList();
	private final RpcHelper rpcHelper = new RpcHelper();
	
	
	public CheckedInPanel() {
		setUpPanel();
		//Get QBItems List from Database
		rpcHelper.dbGetQBItems(inventoryItemList, this);
		log("set qbItemList = to get items");
	}
	
	@Override
	public void setUpPanel() {
		//Set Up Components
			//Set Up lotCodeTextBox
			lotCodeTextBox.setText("Lot Code...");
			lotCodeTextBox.setStyleName("lotCodeTextBox");
			ingredientListBox.setStyleName("ingredientListBox");
			//Set Up ingredientCodeTextBox
			ingredientCodeTextBox.setStyleName("ingredientCodeTextBox");
			matchIngredientCodeToDescription();
			//Set Up ingredientQuantityTextBox
			ingredientQuantityTextBox.setStyleName("ingredientCodeTextBox");
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(DATE_FORMAT));
			dateBox.setValue(new Date(), true);
			dateBox.setStyleName("dateBox");
			//Set Up addIngredientButton
			createButtonWithIcon(addIngredientButton, ADD, "Add");
			//Set Up newCheckedInIngredientPanel
			newCheckedInFlexTable.setStyleName("FlexTable");
			//Set Up checkedInIngredientFlexTable
			checkedInIngredientFlexTable.setText(0,0,"Lot Code");
			checkedInIngredientFlexTable.setText(0,1,"Ingredient Type");
			checkedInIngredientFlexTable.setText(0,2,"Quantity");
			checkedInIngredientFlexTable.setText(0,3,"Date");
			checkedInIngredientFlexTable.setText(0,4,"Remove");
			checkedInIngredientFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
			checkedInIngredientFlexTable.addStyleName("FlexTable");
		//Add Handlers
		addIngredientButton.addClickHandler(this);
		ingredientListBox.addChangeHandler(this);
		ingredientCodeTextBox.addChangeHandler(this);
		//Add components to newCheckedInIngredientPanel
		newCheckedInFlexTable.setText(0, 0, "Lot Code");
		newCheckedInFlexTable.setText(0, 1, "Description");
		newCheckedInFlexTable.setText(0, 2, "Part #");
		newCheckedInFlexTable.setText(0, 3, "Quantity");
		newCheckedInFlexTable.setText(0, 4, "Date");
		newCheckedInFlexTable.setWidget(0,5,addIngredientButton);
		newCheckedInFlexTable.getFlexCellFormatter().setRowSpan(0, 5, 2);
		newCheckedInFlexTable.setWidget(1,0,lotCodeTextBox);
		newCheckedInFlexTable.setWidget(1,1,ingredientListBox);
		newCheckedInFlexTable.setWidget(1,2,ingredientCodeTextBox);
		newCheckedInFlexTable.setWidget(1,3,ingredientQuantityTextBox);
		newCheckedInFlexTable.setWidget(1,4,dateBox);
		newCheckedInFlexTable.getRowFormatter().addStyleName(0, "boldText");
		//Assemble checkedInIngredientPanel
		checkedInIngredientPanel.setSpacing(5);
		checkedInIngredientPanel.add(newCheckedInFlexTable);
		checkedInIngredientPanel.add(checkedInIngredientFlexTable);
		checkedInIngredientPanel.setCellVerticalAlignment(newCheckedInFlexTable, HasVerticalAlignment.ALIGN_MIDDLE);
		dialogBox = new LotCodeManagerDialogBox(this, "Check-In Inventory", true, true);
		highlightLotCodeBox();
		checkedInIngredientPanel.setStyleName("roundedCorners");
	}
	
	@Override
	public Panel getPanel() {
		return checkedInIngredientPanel;
	}
	
	public void highlightLotCodeBox() {
		lotCodeTextBox.selectAll();
	}
	
	@Override
	public void populateFlexTable() {
		//Set Up ingredientListBox (if there is anything to add)
		if(inventoryItemList.size() > 0) {
			for (InventoryItemDto item : inventoryItemList) {
				ingredientListBox.addItem(item.getDescription());
			}
			matchIngredientCodeToDescription();
		}
	}
	
	public void populateCheckedInIngredientsFlexTable() {
		final String lotCode = lotCodeTextBox.getText().toUpperCase().trim();
		lotCodesList.add(lotCode);
	    // create removeCheckedInIngredientButton with handler
	    Image removeButton = newImage(DELETE);
	    removeButton.addClickHandler(new ClickHandler() {
	        @Override
			public void onClick(ClickEvent event) {
	            int rowToRemove = lotCodesList.indexOf(lotCode);
	            checkedInIngredientList.remove(rowToRemove);
	            lotCodesList.remove(rowToRemove);
	            checkedInIngredientFlexTable.removeRow(rowToRemove + 1);
			    lotCodeTextBox.selectAll();
	          }
	        });
	    // add new row to checkedInIngredientFlexTable
	    int size = checkedInIngredientList.size();
	    checkedInIngredientFlexTable.setText(size,0,checkedInIngredientList.get(size - 1).getCode());
		checkedInIngredientFlexTable.setText(size,1,checkedInIngredientList.get(size - 1).getInventoryItem().getDescription());
		checkedInIngredientFlexTable.setText(size,2,Integer.toString(checkedInIngredientList.get(size - 1).getQuantity()));
		checkedInIngredientFlexTable.setText(size,3,DATE_FORMAT.format(checkedInIngredientList.get(size - 1).getReceivedDatetime()));
		checkedInIngredientFlexTable.setWidget(size,4,removeButton);
		checkedInIngredientFlexTable.getCellFormatter().addStyleName(size,4,"pointer");
	    lotCodeTextBox.setText("Lot Code ...");
	    lotCodeTextBox.selectAll();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == addIngredientButton) {
			// check if lot code is unique
			String lotCode = lotCodeTextBox.getText().toUpperCase().trim();
			String quantity = ingredientQuantityTextBox.getText();
			for (InventoryLotDto checkedInIngredient : checkedInIngredientList) {
				if (checkedInIngredient.getCode().equals(lotCode)) {
					Window.alert("'" + lotCode + "' is already in the table");
				    lotCodeTextBox.selectAll();
				    return;
				}
			}
		    // lot code must be between 1 and 20 chars that are numbers, or letters.
			if (!lotCode.matches("[0-9A-Z\\s]{1,20}")) {
		    	Window.alert("'" + lotCode + "' is not a valid Lot Code.");
		    	lotCodeTextBox.selectAll();
		    	return;
		    }
		    // quantity must be between 1 and 10 chars that are numbers.
			else if (!quantity.matches("[0-9\\s]{1,20}")) {
		    	Window.alert("'" + quantity + "' is not a number.");
		    	ingredientQuantityTextBox.selectAll();
		    	return;
		    }
			else { 
				if(ingredientListBox.getItemCount() > 0) {
					checkedInIngredientList.add(new InventoryLotDto(lotCodeTextBox.getText().toUpperCase().trim(), new InventoryItemDto(ingredientCodeTextBox.getText(), ingredientListBox.getItemText(ingredientListBox.getSelectedIndex())), Integer.parseInt(ingredientQuantityTextBox.getText()), dateBox.getValue()));
				}
				else {
					checkedInIngredientList.add(new InventoryLotDto(lotCodeTextBox.getText().toUpperCase().trim(), new InventoryItemDto(ingredientCodeTextBox.getText(), "-"), Integer.parseInt(ingredientQuantityTextBox.getText()), dateBox.getValue()));
				}
				populateCheckedInIngredientsFlexTable();
			}
		}
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		if (event.getSource() instanceof TextBox) {
			matchIngredientDescriptionToCode();
		}
		if (event.getSource() instanceof ListBox) {
			matchIngredientCodeToDescription();
		}
	}

	private void matchIngredientDescriptionToCode() {
		for (InventoryItemDto item : inventoryItemList) {
			if (item.getId().equals(ingredientCodeTextBox.getText())) {
				ingredientListBox.setItemSelected(inventoryItemList.indexOf(item), true);
				break;
			}
		}
	}
	
	private void matchIngredientCodeToDescription() {
		for (InventoryItemDto item : inventoryItemList) {
			if (item.getDescription().equals(ingredientListBox.getItemText(ingredientListBox.getSelectedIndex()))) {
				ingredientCodeTextBox.setText(item.getId());
				break;
			}
		}
	}

	@Override
	void updateDB() {
		rpcHelper.dbSetCheckedInIngredients(checkedInIngredientList);
		log("updateDB (checked in)");
	}
	
}



