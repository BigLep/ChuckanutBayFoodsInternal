package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.common.shared.IconUtil.WARNING;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.ArrayList;
import java.util.Date;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class InUsePanel extends LotCodeManagerPanel {
	//Checked-In Components
	private final VerticalPanel inUseIngredientPanel = new VerticalPanel();
	private final HorizontalPanel inUseDatePanel = new HorizontalPanel();
	private final Label dateBoxLabel = new Label("In Use Date:");
	private final DateBox dateBox = new DateBox();
	private final FlexTable inUseIngredientFlexTable = new FlexTable();
	private DialogBox dialogBox;
	private final ArrayList<InventoryLotDto> inUseIngredientList = newArrayList();
	private final RpcHelper rpcHelper = new RpcHelper();

	public InUsePanel() {
		setUpPanel();
		rpcHelper.dbGetCheckedInIngredients(inUseIngredientList, this);
	}

	@Override
	public void setUpPanel() {
		//Set Up Components
		//Set Up dateBox
		dateBox.setFormat(new DateBox.DefaultFormat(DATE_FORMAT));
		dateBox.setValue(new Date(), true);
		dateBox.setStyleName("dateBox");
		//Set Up inUseDatePanel
		inUseDatePanel.setSpacing(5);
		//Set Up inUseIngredientFlexTable
		inUseIngredientFlexTable.setText(0,0,"Lot Code");
		inUseIngredientFlexTable.setText(0,1,"Ingredient Type");
		inUseIngredientFlexTable.setText(0,2,"Quantity");
		inUseIngredientFlexTable.setText(0,3,"Checked-In Date");
		inUseIngredientFlexTable.setText(0,4,"In-Use Date");
		inUseIngredientFlexTable.setText(0,5,"Mark");
		inUseIngredientFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		inUseIngredientFlexTable.addStyleName("FlexTable");
		//Add components to inUseDatePanel
		inUseDatePanel.add(dateBoxLabel);
		inUseDatePanel.add(dateBox);
		//Assemble inUseIngredientPanel
		inUseIngredientPanel.setSpacing(5);
		inUseIngredientPanel.add(inUseDatePanel);
		inUseIngredientPanel.add(inUseIngredientFlexTable);
		//Make Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "Mark Inventory As In-Use", true, true);
	}

	@Override
	public void populateFlexTable() {
		if(inUseIngredientList.isEmpty()){
			Window.alert("There are no Ingredients to mark");
		}
		else {
			final ArrayList<Integer> hashCodeList = new ArrayList<Integer>();
			for (InventoryLotDto checkedInIngredient : inUseIngredientList) {
				hashCodeList.add(checkedInIngredient.hashCode());
				final int rowToMark = hashCodeList.indexOf(checkedInIngredient.hashCode());
				// create markIngredientButton with handler
				Image markIngredientButton = new Image(WARNING);
				markIngredientButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						inUseIngredientList.get(rowToMark).setStartUseDatetime(dateBox.getValue());
						inUseIngredientFlexTable.clearCell((rowToMark + 1), 5);
						inUseIngredientFlexTable.setText((rowToMark + 1),4,DATE_FORMAT.format(inUseIngredientList.get(rowToMark).getStartUseDatetime()));
					}
				});
				// add new row to inUseIngredientFlexTable
				inUseIngredientFlexTable.setText((rowToMark + 1),0,checkedInIngredient.getCode());
				inUseIngredientFlexTable.setText((rowToMark + 1),1,checkedInIngredient.getInventoryItem().getDescription());
				inUseIngredientFlexTable.setText((rowToMark + 1),2,Integer.toString(checkedInIngredient.getQuantity()));
				inUseIngredientFlexTable.setText((rowToMark + 1),3,DATE_FORMAT.format(checkedInIngredient.getReceivedDatetime()));
				inUseIngredientFlexTable.setText((rowToMark + 1),4,"");
				inUseIngredientFlexTable.setWidget((rowToMark + 1),5,markIngredientButton);
				inUseIngredientFlexTable.getCellFormatter().addStyleName((rowToMark + 1),5,"pointer");
			}
		}
	}

	@Override
	public Panel getPanel() {
		return inUseIngredientPanel;
	}

	@Override
	void updateDB() {
		rpcHelper.dbSetInUseIngredients(inUseIngredientList);
	}
}
