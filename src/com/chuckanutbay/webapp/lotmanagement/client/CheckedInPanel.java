package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

class CheckedInPanel extends LotCodeManagerPanel implements ClickHandler, ChangeHandler {
	//Checked-In Components
	VerticalPanel checkedInIngredientPanel = new VerticalPanel();
	HorizontalPanel newCheckedInIngredientPanel = new HorizontalPanel();
	TextBox lotCodeTextBox = new TextBox();
	ListBox ingredientListBox = new ListBox();
	TextBox ingredientCodeTextBox = new TextBox();
	DateBox dateBox = new DateBox();
	Button addIngredientButton = new Button();
	FlexTable checkedInIngredientFlexTable = new FlexTable();
	DialogBox dialogBox;
	List<InventoryItemDto> inventoryItemList = new ArrayList<InventoryItemDto>();
	List<InventoryLotDto> checkedInIngredientList = new ArrayList<InventoryLotDto>();
	List<String> lotCodesList = new ArrayList<String>();
	
	public CheckedInPanel() {
		setUpPanel();
		//Get QBItems List from Database
		dbGetQBItems(inventoryItemList, this);
		log("set qbItemList = to get items");
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up lotCodeTextBox
			lotCodeTextBox.setText("Lot Code...");
			lotCodeTextBox.setWidth("150px");
			//Set Up ingredientCodeTextBox
			ingredientCodeTextBox.setReadOnly(true);
			ingredientCodeTextBox.setWidth("50px");
			matchIngredientCodeAndType();
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
			dateBox.setValue(new Date(), true);
			dateBox.setWidth("70px");
			//Set Up addIngredientButton
			makeButtonWithIcon(addIngredientButton, icons.addIcon(), "Add");
			addIngredientButton.setWidth("150px");
			//Set Up newCheckedInIngredientPanel
			newCheckedInIngredientPanel.setSpacing(5);
			newCheckedInIngredientPanel.setStyleName("headerPanel");
			//Set Up checkedInIngredientFlexTable
			checkedInIngredientFlexTable.setWidth("600px");
			checkedInIngredientFlexTable.setText(0,0,"Lot Code");
			checkedInIngredientFlexTable.setText(0,1,"Ingredient Type");
			checkedInIngredientFlexTable.setText(0,2,"Ingredient Code");
			checkedInIngredientFlexTable.setText(0,3,"Date");
			checkedInIngredientFlexTable.setText(0,4,"Remove");
			checkedInIngredientFlexTable.getRowFormatter().addStyleName(0, "checkedInIngredientFlexTableHeader");
			checkedInIngredientFlexTable.addStyleName("checkedInIngredientFlexTable");
			
		//Add Handlers
		addIngredientButton.addClickHandler(this);
		ingredientListBox.addChangeHandler(this);
		//Add components to newCheckedInIngredientPanel
		newCheckedInIngredientPanel.add(lotCodeTextBox);
		newCheckedInIngredientPanel.add(ingredientListBox);
		newCheckedInIngredientPanel.add(ingredientCodeTextBox);
		newCheckedInIngredientPanel.add(dateBox);
		newCheckedInIngredientPanel.add(addIngredientButton);
		//Assemble checkedInIngredientPanel
		checkedInIngredientPanel.setSpacing(5);
		checkedInIngredientPanel.add(newCheckedInIngredientPanel);
		checkedInIngredientPanel.add(checkedInIngredientFlexTable);
		checkedInIngredientPanel.setCellVerticalAlignment(newCheckedInIngredientPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		dialogBox = new LotCodeManagerDialogBox(this, "Check-In Inventory", true, true);
		highlightLotCodeBox();
	}
	
	private void matchIngredientCodeAndType() {
		if (inventoryItemList.size() > 0) {
			for (InventoryItemDto item : inventoryItemList) {
				if (item.getDescription().equals(ingredientListBox.getItemText(ingredientListBox.getSelectedIndex()))) {
					ingredientCodeTextBox.setText(item.getId());
					break;
				}
			}
		}
	}

	public Panel getPanel() {
		return checkedInIngredientPanel;
	}
	
	public void highlightLotCodeBox() {
		lotCodeTextBox.selectAll();
	}
	
	public void populateFlexTable() {
		//Set Up ingredientListBox (if there is anything to add)
		if(inventoryItemList.size() > 0) {
			for (InventoryItemDto item : inventoryItemList) {
				ingredientListBox.addItem(item.getDescription());
			}
			matchIngredientCodeAndType();
		}
	}
	
	public void populateCheckedInIngredientsFlexTable() {
		final String lotCode = lotCodeTextBox.getText().toUpperCase().trim();
		lotCodesList.add(lotCode);
	    // create removeCheckedInIngredientButton with handler
	    Button removeCheckedInIngredientButton = new Button();
	    makeButtonWithIcon(removeCheckedInIngredientButton, icons.deleteIcon(), "Remove");
	    removeCheckedInIngredientButton.addClickHandler(new ClickHandler() {
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
		checkedInIngredientFlexTable.setText(size,2,checkedInIngredientList.get(size - 1).getInventoryItem().getId());
		checkedInIngredientFlexTable.setText(size,3,dateFormat.format(checkedInIngredientList.get(size - 1).getReceivedDatetime()));
		checkedInIngredientFlexTable.setWidget(size,4,removeCheckedInIngredientButton);
		checkedInIngredientFlexTable.getCellFormatter().addStyleName(size,4,"checkedInIngredientFlexTableRemoveButton");
	    lotCodeTextBox.setText("Lot Code ...");
	    lotCodeTextBox.selectAll();
	}
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == addIngredientButton) {
			// check if lot code is unique
			String lotCode = lotCodeTextBox.getText().toUpperCase().trim();
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
			else { 
				if(ingredientListBox.getItemCount() > 0) {
					checkedInIngredientList.add(new InventoryLotDto(lotCodeTextBox.getText().toUpperCase().trim(), new InventoryItemDto(ingredientCodeTextBox.getText(), ingredientListBox.getItemText(ingredientListBox.getSelectedIndex())), dateBox.getValue()));
				}
				else {
					checkedInIngredientList.add(new InventoryLotDto(lotCodeTextBox.getText().toUpperCase().trim(), new InventoryItemDto(ingredientCodeTextBox.getText(), "-"), dateBox.getValue()));
				}
				populateCheckedInIngredientsFlexTable();
			}
		}
	}
	
	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == ingredientListBox) {
			matchIngredientCodeAndType();
		}
	}

	void updateDB() {
		dbSetCheckedInIngredients(checkedInIngredientList);
		log("updateDB (checked in)");
	}
	
}



