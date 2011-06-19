package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ActiveIngredientsPanel extends LotCodeManagerPanel {
	//Checked-In Components
	private final VerticalPanel activeIngredientsPanel = new VerticalPanel();
	private final HorizontalPanel dateToSearchPanel = new HorizontalPanel();
	private final Label dateToSearchLabel = new Label("Today's Date:");
	private final TextBox dateBox = new TextBox();
	private final FlexTable activeIngredientsFlexTable = new FlexTable();
	private DialogBox dialogBox;
	private final List<InventoryLotDto> activeIngredientList = newArrayList();
	private final RpcHelper rpcHelper = new RpcHelper();
	
	public ActiveIngredientsPanel() { 
		setUpPanel();
		rpcHelper.dbGetInUseIngredients(activeIngredientList, this);
	}
	
	@Override
	public void setUpPanel() {
		//Set Up Components
			//Set Up dateBox
			dateBox.setText(DATE_FORMAT.format(new Date()));
			dateBox.setReadOnly(true);
			dateBox.setStyleName("dateBox");
			//Set Up dateToSearchPanel
			dateToSearchPanel.setStyleName("headerPanel");
			//Set Up activeIngredientsFlexTable
			setupactiveIngredientsFlexTableHeader();
		//Add components to dateToSearchPanel
		dateToSearchPanel.add(dateToSearchLabel);
		dateToSearchPanel.add(dateBox);
		//Assemble activeIngredientsPanel
		activeIngredientsPanel.setSpacing(5);
		activeIngredientsPanel.add(dateToSearchPanel);
		activeIngredientsPanel.add(activeIngredientsFlexTable);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "View Active Ingredients", false, true);
	}

	private void setupactiveIngredientsFlexTableHeader() {
		activeIngredientsFlexTable.setText(0,0,"Lot Code");
		activeIngredientsFlexTable.setText(0,1,"Ingredient Type");
		activeIngredientsFlexTable.setText(0,2,"Quantity");
		activeIngredientsFlexTable.setText(0,3,"Checked-In Date");
		activeIngredientsFlexTable.setText(0,4,"In-Use Date");
		activeIngredientsFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		activeIngredientsFlexTable.addStyleName("FlexTable");
		
	}

	@Override
	public Panel getPanel() {
		return activeIngredientsPanel;
	}
	
	@Override
	public void populateFlexTable() {
		if(activeIngredientList.isEmpty()){
			Window.alert("There are no Active Ingredients");
		}
		else {
		    int row = 1;
			for (InventoryLotDto activeIngredient : activeIngredientList) {
			    // add new row to activeIngredientsFlexTable
			    activeIngredientsFlexTable.setText(row,0,activeIngredient.getCode());
				activeIngredientsFlexTable.setText(row,1,activeIngredient.getInventoryItem().getDescription());
				activeIngredientsFlexTable.setText(row,2,Integer.toString(activeIngredient.getQuantity()));
				activeIngredientsFlexTable.setText(row,3,DATE_FORMAT.format(activeIngredient.getReceivedDatetime()));
				activeIngredientsFlexTable.setText(row,4,DATE_FORMAT.format(activeIngredient.getStartUseDatetime()));
				row++;
			}
		}
	}

	@Override
	void updateDB() {
		// Nothing to Update
	}
}