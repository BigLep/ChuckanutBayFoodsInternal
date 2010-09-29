package com.chuckanutbay.LotCodeManager.client;
import static com.chuckanutbay.LotCodeManager.client.LotCodeUtil.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

final class LotCodeManager  implements ClickHandler, EntryPoint, MouseOverHandler, MouseOutHandler {
	
	//Main UI Components
	VerticalPanel mainPanel = new VerticalPanel();
	Label lotCodeManagerLabel = new Label("Lot Code Manager");
	Label markInventoryLabel = new Label("Mark Inventory As:");
	Label queryLabel = new Label("Query Lot Code Data:");
	Label viewFullInventoryHistoryLabel = new Label("Inventory History:");
	HorizontalPanel buttonsPanel = new HorizontalPanel();
	HorizontalPanel markInventoryButtonsHPanel = new HorizontalPanel();
	HorizontalPanel viewFullInventoryHistoryHPanel = new HorizontalPanel();
	HorizontalPanel queryButtonsHPanel = new HorizontalPanel();
	VerticalPanel markInventoryVPanel = new VerticalPanel();
	VerticalPanel viewFullInventoryHistoryVPanel = new VerticalPanel();
	VerticalPanel queryVPanel = new VerticalPanel();
	Button checkedInButton = new Button();
	Button inUseButton = new Button();
	Button usedUpButton = new Button();
	Button lotCodeSearch = new Button();
	Button dateSearch = new Button ();
	Button activeIngredientsButton = new Button ();
	Button viewFullInventoryHistoryButton = new Button();
	DecoratedPopupPanel lotCodeSearchPopup = new DecoratedPopupPanel(true);
	DecoratedPopupPanel dateSearchPopup = new DecoratedPopupPanel(true);
	DecoratedPopupPanel activeIngredientsPopup = new DecoratedPopupPanel(true);
	DialogBox dialogBox;
	
	//LotCodeMangaerPanels to be made
	CheckedInPanel checkedInPanel;
	InUsePanel inUsePanel;
	UsedUpPanel usedUpPanel;
	LotCodeSearchPanel lotCodeSearchPanel;
	DateSearchPanel dateSearchPanel;
	ActiveIngredientsPanel activeIngredientsPanel;
	FullInventoryHistoryPanel fullInventoryHistoryPanel;
	
	public void onModuleLoad() {
		
		//Set Up iconButtons
		makeButtonWithIcon(checkedInButton, icons.checkmarkIcon(), "Checked-In");
		makeButtonWithIcon(inUseButton, icons.warningIcon(), "In Use");
		makeButtonWithIcon(usedUpButton, icons.cancelIcon(), "Used Up");
		makeButtonWithIcon(lotCodeSearch, icons.searchIcon(), "Lot Code Search");
		makeButtonWithIcon(dateSearch, icons.dateIcon(), "Date Search");
		makeButtonWithIcon(activeIngredientsButton, icons.listIcon(), "Active Ingredients");
		makeButtonWithIcon(viewFullInventoryHistoryButton, icons.databaseIcon(), "View Full Inventory History");
		
		//Add Handlers
		checkedInButton.addClickHandler(this);
		inUseButton.addClickHandler(this);
		usedUpButton.addClickHandler(this);
		lotCodeSearch.addClickHandler(this);
		dateSearch.addClickHandler(this);
		activeIngredientsButton.addClickHandler(this);
		viewFullInventoryHistoryButton.addClickHandler(this);
		lotCodeSearch.addMouseOverHandler(this);
		dateSearch.addMouseOverHandler(this);
		activeIngredientsButton.addMouseOverHandler(this);
		lotCodeSearch.addMouseOutHandler(this);
		dateSearch.addMouseOutHandler(this);
		activeIngredientsButton.addMouseOutHandler(this);
		
		//Set Text of Pop-Ups
		lotCodeSearchPopup.setWidget(new Label("Search for the information of a specific Lot Code"));
		dateSearchPopup.setWidget(new Label("Search for the Lot Codes in use on a specific date"));
		activeIngredientsPopup.setWidget(new Label("Displays all currently In-Use items"));
		
		//Set up mainPanel
			//Set up lotCodeManagerLabel Style
			lotCodeManagerLabel.setStyleName("lotCodeManagerLabel");
			//Set up HorizontalButtonPanels
				//Add Buttons to markInventoryButtonsHPanel
				markInventoryButtonsHPanel.add(checkedInButton);
				markInventoryButtonsHPanel.add(inUseButton);
				markInventoryButtonsHPanel.add(usedUpButton);
				markInventoryButtonsHPanel.setSpacing(5);
				//Add Buttons to queryButtonsHPanel
				queryButtonsHPanel.add(lotCodeSearch);
				queryButtonsHPanel.add(dateSearch);
				queryButtonsHPanel.add(activeIngredientsButton);
				queryButtonsHPanel.setSpacing(5);
				//Add Buttons to viewFullInventoryHistoryHPanel
				viewFullInventoryHistoryHPanel.add(viewFullInventoryHistoryButton);
				viewFullInventoryHistoryHPanel.setSpacing(5);
			
			//Set up VerticalButtonPanels
				markInventoryVPanel.add(markInventoryLabel);
				markInventoryVPanel.add(markInventoryButtonsHPanel);
				markInventoryVPanel.setStyleName("buttonsPanel");
				queryVPanel.add(queryLabel);
				queryVPanel.add(queryButtonsHPanel);
				queryVPanel.setStyleName("buttonsPanel");
				viewFullInventoryHistoryVPanel.add(viewFullInventoryHistoryLabel);
				viewFullInventoryHistoryVPanel.add(viewFullInventoryHistoryHPanel);
				viewFullInventoryHistoryVPanel.setStyleName("buttonsPanel");
			
			//Set up ButtonsPanel
				buttonsPanel.add(markInventoryVPanel);
				buttonsPanel.add(queryVPanel);
				buttonsPanel.add(viewFullInventoryHistoryVPanel);
				buttonsPanel.setSpacing(5);
				
		mainPanel.add(lotCodeManagerLabel);
		mainPanel.add(buttonsPanel);
		mainPanel.setSpacing(5);
		mainPanel.setWidth("1000px");
		mainPanel.setCellHorizontalAlignment(buttonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHorizontalAlignment(lotCodeManagerLabel, HasHorizontalAlignment.ALIGN_CENTER);
		//Add mainPanel to rootPanel
		RootPanel.get("LotCodeManager").add(mainPanel);
	} 
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == checkedInButton) {
			//Assemble checkedInPanel
			checkedInPanel = new CheckedInPanel();
		}
		else if (sender == inUseButton) {
			//Assemble inUsePanel
			inUsePanel = new InUsePanel();
		}
		else if (sender == usedUpButton) {//Assemble inUsePanel
			usedUpPanel = new UsedUpPanel();
		}
		else if (sender == lotCodeSearch) {
			lotCodeSearchPanel = new LotCodeSearchPanel();
		}
		else if (sender == dateSearch) {
			dateSearchPanel = new DateSearchPanel();
		}
		else if (sender == activeIngredientsButton) {
			activeIngredientsPanel = new ActiveIngredientsPanel();
		}
		else if (sender == viewFullInventoryHistoryButton) {
			fullInventoryHistoryPanel = new FullInventoryHistoryPanel();
		}
	}
	
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == lotCodeSearch) {
			makePopupVisible(lotCodeSearchPopup, sender, 25, 5);
		}
		else if (sender == dateSearch) {
			makePopupVisible(dateSearchPopup, sender, 25, 5);
		}
		else if (sender == activeIngredientsButton) {
			makePopupVisible(activeIngredientsPopup, sender, 25, 5);
		}
	}

	public void onMouseOut(MouseOutEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == lotCodeSearch) {
			lotCodeSearchPopup.hide();
		}
		else if (sender == dateSearch) {
			dateSearchPopup.hide();
		}
		else if (sender == activeIngredientsButton) {
			activeIngredientsPopup.hide();
		}
	}
}

