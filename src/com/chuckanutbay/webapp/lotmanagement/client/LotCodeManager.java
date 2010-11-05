package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makeButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makePopupVisible;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

final class LotCodeManager  implements ClickHandler, EntryPoint, MouseOverHandler, MouseOutHandler {
	
	//Main UI Components
	private VerticalPanel mainPanel = new VerticalPanel();
	private Label lotCodeManagerLabel = new Label("Lot Code Manager");
	private Label markInventoryLabel = new Label("Mark Inventory As:");
	private Label queryLabel = new Label("Query Lot Code Data:");
	private Label viewFullInventoryHistoryLabel = new Label("Inventory History:");
	private HorizontalPanel buttonsPanel = new HorizontalPanel();
	private HorizontalPanel markInventoryButtonsHPanel = new HorizontalPanel();
	private HorizontalPanel viewFullInventoryHistoryHPanel = new HorizontalPanel();
	private HorizontalPanel queryButtonsHPanel = new HorizontalPanel();
	private VerticalPanel markInventoryVPanel = new VerticalPanel();
	private VerticalPanel viewFullInventoryHistoryVPanel = new VerticalPanel();
	private VerticalPanel queryVPanel = new VerticalPanel();
	private Button checkedInButton = new Button();
	private Button inUseButton = new Button();
	private Button usedUpButton = new Button();
	private Button lotCodeSearch = new Button();
	private Button dateSearch = new Button ();
	private Button activeIngredientsButton = new Button ();
	private Button viewFullInventoryHistoryButton = new Button();
	private DecoratedPopupPanel lotCodeSearchPopup = new DecoratedPopupPanel(true);
	private DecoratedPopupPanel dateSearchPopup = new DecoratedPopupPanel(true);
	private DecoratedPopupPanel activeIngredientsPopup = new DecoratedPopupPanel(true);
	private DialogBox dialogBox;
	private static int POPUP_POSITION_FROM_TOP = 25;
	private static int POPUP_POSITION_FROM_LEFT = 5;
	
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
			checkedInPanel = new CheckedInPanel();
		}
		else if (sender == inUseButton) {
			inUsePanel = new InUsePanel();
		}
		else if (sender == usedUpButton) {
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
			makePopupVisible(lotCodeSearchPopup, sender, POPUP_POSITION_FROM_TOP, POPUP_POSITION_FROM_LEFT);
		}
		else if (sender == dateSearch) {
			makePopupVisible(dateSearchPopup, sender, POPUP_POSITION_FROM_TOP, POPUP_POSITION_FROM_LEFT);
		}
		else if (sender == activeIngredientsButton) {
			makePopupVisible(activeIngredientsPopup, sender, POPUP_POSITION_FROM_TOP, POPUP_POSITION_FROM_LEFT);
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

