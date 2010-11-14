package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makeButtonWithIcon;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

class CheckedInPanel extends LotCodeManagerPanel implements ClickHandler, ChangeHandler {
	//Checked-In Components
	private VerticalPanel checkedInIngredientPanel = new VerticalPanel();
	private HorizontalPanel newCheckedInIngredientPanel = new HorizontalPanel();
	private TextBox lotCodeTextBox = new TextBox();
	private ListBox ingredientListBox = new ListBox();
	private TextBox ingredientCodeTextBox = new TextBox();
	private DateBox dateBox = new DateBox();
	private Button addIngredientButton = new Button();
	private FlexTable checkedInIngredientFlexTable = new FlexTable();
	//private CellTable<InventoryLotDto> checkedInIngredientCellTable = new CellTable<InventoryLotDto>();
	private DialogBox dialogBox;
	private List<InventoryItemDto> inventoryItemList = newArrayList();
	private List<InventoryLotDto> checkedInIngredientList = newArrayList();
	private List<String> lotCodesList = newArrayList();
	private RpcHelper rpcHelper = new RpcHelper();
	
	
	public CheckedInPanel() {
		setUpPanel();
		//Get QBItems List from Database
		rpcHelper.dbGetQBItems(inventoryItemList, this);
		log("set qbItemList = to get items");
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up lotCodeTextBox
			lotCodeTextBox.setText("Lot Code...");
			lotCodeTextBox.setStyleName("lotCodeTextBox");
			ingredientListBox.setStyleName("ingredientListBox");
			//Set Up ingredientCodeTextBox
			ingredientCodeTextBox.setReadOnly(true);
			ingredientCodeTextBox.setStyleName("ingredientCodeTextBox");
			matchIngredientCodeAndType();
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
			dateBox.setValue(new Date(), true);
			dateBox.setStyleName("dateBox");
			//Set Up addIngredientButton
			makeButtonWithIcon(addIngredientButton, icons.addIcon(), "Add");
			addIngredientButton.setWidth("75px");
			//Set Up newCheckedInIngredientPanel
			newCheckedInIngredientPanel.setSpacing(5);
			newCheckedInIngredientPanel.setStyleName("headerPanel");
			//Set Up checkedInIngredientFlexTable
			checkedInIngredientFlexTable.setText(0,0,"Lot Code");
			checkedInIngredientFlexTable.setText(0,1,"Ingredient Type");
			checkedInIngredientFlexTable.setText(0,2,"Ingredient Code");
			checkedInIngredientFlexTable.setText(0,3,"Date");
			checkedInIngredientFlexTable.setText(0,4,"Remove");
			checkedInIngredientFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
			checkedInIngredientFlexTable.addStyleName("FlexTable");
	/**
			//Set up checkedInIngredientCellTable
			TextColumn<InventoryLotDto> lotCodeColumn = new TextColumn<InventoryLotDto>() {
				public String getValue(InventoryLotDto inventoryLotDto) {
					return inventoryLotDto.getCode(); 
				}
			};
			checkedInIngredientCellTable.addColumn(lotCodeColumn, "Lot Code");
			
			TextColumn<InventoryLotDto> itemDescriptionColumn = new TextColumn<InventoryLotDto>() {
				public String getValue(InventoryLotDto inventoryLotDto) {
					return inventoryLotDto.getInventoryItem().getDescription(); 
				}
			};
			checkedInIngredientCellTable.addColumn(itemDescriptionColumn, "Inventory Item");
			
			TextColumn<InventoryLotDto> itemCodeColumn = new TextColumn<InventoryLotDto>() {
				public String getValue(InventoryLotDto inventoryLotDto) {
					return inventoryLotDto.getInventoryItem().getId(); 
				}
			};
			checkedInIngredientCellTable.addColumn(itemCodeColumn, "Item Code");
			
			DateCell dateCell = new DateCell();
			Column<InventoryLotDto, Date> dateColumn = new Column<InventoryLotDto, Date>(dateCell) {
				public Date getValue(InventoryLotDto inventoryLotDto) {
					return inventoryLotDto.getReceivedDatetime(); 
				}
			};
			checkedInIngredientCellTable.addColumn(dateColumn, "Date");
			
			ButtonCell buttonCell = new ButtonCell();
			Column<InventoryLotDto, String> removeButtonColumn = new Column<InventoryLotDto, String>(buttonCell) {
				public String getValue(InventoryLotDto inventoryLotDto) {
					 Button removeButton = new Button();
					    makeButtonWithIcon(removeButton, icons.deleteIcon(), "Remove");
					    removeButton.addClickHandler(new ClickHandler() {
					        public void onClick(ClickEvent event) {
					        	checkedInIngredientCellTable
					          }
					        });
					return removeButton.getHTML(); 
				}
			};
			checkedInIngredientCellTable.addColumn(removeButtonColumn, "Remove");
			
	*/
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
		rpcHelper.dbSetCheckedInIngredients(checkedInIngredientList);
		log("updateDB (checked in)");
	}
	
}



