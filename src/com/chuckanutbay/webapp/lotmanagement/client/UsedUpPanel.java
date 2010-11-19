package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class UsedUpPanel extends LotCodeManagerPanel {
	//Checked-In Components
	private final VerticalPanel usedUpIngredientPanel = new VerticalPanel();
	private final HorizontalPanel usedUpDatePanel = new HorizontalPanel();
	private final Label dateBoxLabel = new Label("In Use Date:");
	private final DateBox dateBox = new DateBox();
	private DialogBox dialogBox;
	private final FlexTable usedUpIngredientFlexTable = new FlexTable();
	private final List<InventoryLotDto> usedUpIngredientList = newArrayList();
	private final RpcHelper rpcHelper = new RpcHelper();
	
	public UsedUpPanel() {
		setUpPanel();
		rpcHelper.dbGetInUseIngredients(usedUpIngredientList, this);
	}
	
	@Override
	public void setUpPanel() {
		log("starting set up");
		//Set Up Components
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
			dateBox.setValue(new Date(), true);
			dateBox.setStyleName("dateBox");
			//Set Up usedUpDatePanel
			usedUpDatePanel.setSpacing(5);
			//Set Up usedUpIngredientFlexTable
			usedUpIngredientFlexTable.setText(0,0,"Lot Code");
			usedUpIngredientFlexTable.setText(0,1,"Ingredient Type");
			usedUpIngredientFlexTable.setText(0,2,"Quantity");
			usedUpIngredientFlexTable.setText(0,3,"Checked-In Date");
			usedUpIngredientFlexTable.setText(0,4,"In-Use Date");
			usedUpIngredientFlexTable.setText(0,5,"Used-Up Date");
			usedUpIngredientFlexTable.setText(0,6,"Mark");
			usedUpIngredientFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
			usedUpIngredientFlexTable.addStyleName("FlexTable");
		//Add components to usedUpDatePanel
		usedUpDatePanel.add(dateBoxLabel);
		usedUpDatePanel.add(dateBox);
		//Assemble usedUpIngredientPanel
		usedUpIngredientPanel.setSpacing(5);
		usedUpIngredientPanel.add(usedUpDatePanel);
		usedUpIngredientPanel.add(usedUpIngredientFlexTable);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "Mark Inventory As Used-Up", true, true);
	}
	
	@Override
	public void populateFlexTable() {
		log("populating");
		if(usedUpIngredientList.isEmpty()) {
			Window.alert("There are no Ingredients to mark");
		}
		else {
			final ArrayList<Integer> hashCodeList = new ArrayList<Integer>();
			for (InventoryLotDto inUseIngredient : usedUpIngredientList) {
				hashCodeList.add(inUseIngredient.hashCode());
			    final int rowToMark = hashCodeList.indexOf(inUseIngredient.hashCode());
			    // create markIngredientButton with handler
			    Image markIngredientButton = new Image(icons.cancelIcon());
			    markIngredientButton.addClickHandler(new ClickHandler() {
			        @Override
					public void onClick(ClickEvent event) {
			        	usedUpIngredientList.get(rowToMark).setEndUseDatetime(dateBox.getValue());
			        	usedUpIngredientFlexTable.clearCell((rowToMark + 1), 6);
						usedUpIngredientFlexTable.setText((rowToMark + 1),5,dateFormat.format(usedUpIngredientList.get(rowToMark).getEndUseDatetime()));
			        }
			        });
			    // add new row to usedUpIngredientFlexTable
			    usedUpIngredientFlexTable.setText((rowToMark + 1),0,inUseIngredient.getCode());
				usedUpIngredientFlexTable.setText((rowToMark + 1),1,inUseIngredient.getInventoryItem().getDescription());
				usedUpIngredientFlexTable.setText((rowToMark + 1),2,Integer.toString(inUseIngredient.getQuantity()));
				usedUpIngredientFlexTable.setText((rowToMark + 1),3,dateFormat.format(inUseIngredient.getReceivedDatetime()));
				usedUpIngredientFlexTable.setText((rowToMark + 1),4,dateFormat.format(inUseIngredient.getStartUseDatetime()));
				usedUpIngredientFlexTable.setText((rowToMark + 1),5,"");
				usedUpIngredientFlexTable.setWidget((rowToMark + 1),6,markIngredientButton);
				usedUpIngredientFlexTable.getCellFormatter().addStyleName((rowToMark + 1),6,"pointer");
			}
		}
	}

	@Override
	public Panel getPanel() {
		return usedUpIngredientPanel;
	}

	@Override
	void updateDB() {
		rpcHelper.dbSetUsedUpIngredients(usedUpIngredientList);
	}
}
