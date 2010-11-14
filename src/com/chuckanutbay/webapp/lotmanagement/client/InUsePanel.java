package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makeButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.ArrayList;
import java.util.Date;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class InUsePanel extends LotCodeManagerPanel {
	//Checked-In Components
	private VerticalPanel inUseIngredientPanel = new VerticalPanel();
	private HorizontalPanel inUseDatePanel = new HorizontalPanel();
	private Label dateBoxLabel = new Label("In Use Date:");
	private DateBox dateBox = new DateBox();
	private FlexTable inUseIngredientFlexTable = new FlexTable();
	private DialogBox dialogBox;
	private ArrayList<InventoryLotDto> inUseIngredientList = newArrayList();
	private RpcHelper rpcHelper = new RpcHelper();

	public InUsePanel() {
		setUpPanel();
		rpcHelper.dbGetCheckedInIngredients(inUseIngredientList, this);
	}

	public void setUpPanel() {
		//Set Up Components
		//Set Up dateBox
		dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		dateBox.setValue(new Date(), true);
		dateBox.setStyleName("dateBox");
		//Set Up inUseDatePanel
		inUseDatePanel.setSpacing(5);
		//Set Up inUseIngredientFlexTable
		inUseIngredientFlexTable.setText(0,0,"Lot Code");
		inUseIngredientFlexTable.setText(0,1,"Ingredient Type");
		inUseIngredientFlexTable.setText(0,2,"Checked-In Date");
		inUseIngredientFlexTable.setText(0,3,"In-Use Date");
		inUseIngredientFlexTable.setText(0,4,"Mark");
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
				Button markIngredientButton = new Button();
				makeButtonWithIcon(markIngredientButton, icons.warningIcon(), "In Use");
				markIngredientButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						inUseIngredientList.get(rowToMark).setStartUseDatetime(dateBox.getValue());
						inUseIngredientFlexTable.clearCell((rowToMark + 1), 4);
						inUseIngredientFlexTable.setText((rowToMark + 1),3,dateFormat.format(inUseIngredientList.get(rowToMark).getStartUseDatetime()));
					}
				});
				// add new row to inUseIngredientFlexTable
				inUseIngredientFlexTable.setText((rowToMark + 1),0,checkedInIngredient.getCode());
				inUseIngredientFlexTable.setText((rowToMark + 1),1,checkedInIngredient.getInventoryItem().getDescription());
				inUseIngredientFlexTable.setText((rowToMark + 1),2,dateFormat.format(checkedInIngredient.getReceivedDatetime()));
				inUseIngredientFlexTable.setText((rowToMark + 1),3,"");
				inUseIngredientFlexTable.setWidget((rowToMark + 1),4,markIngredientButton);
				inUseIngredientFlexTable.getCellFormatter().addStyleName((rowToMark + 1),4,"center");
			}
		}
	}

	public Panel getPanel() {
		return inUseIngredientPanel;
	}

	void updateDB() {
		rpcHelper.dbSetInUseIngredients(inUseIngredientList);
	}
}
