package com.chuckanutbay.webapp.dashboard.client;

import static com.chuckanutbay.reportgeneration.Print.HP_WIRELESS_P1102W;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_CENTER;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newLabel;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createReportService;
import static com.chuckanutbay.webapp.common.shared.ReportDto.DIGITAL_LABELS;
import static com.chuckanutbay.webapp.common.shared.ReportDto.DIGITAL_LABEL_SUBREPORT;
import static com.chuckanutbay.webapp.common.shared.ReportDto.SCHEDULE;
import static com.chuckanutbay.webapp.dashboard.client.RpcHelper.createPrintReportCallback;
import static com.google.common.collect.Lists.newArrayList;

import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Serves as a dashboard to access the various applications.
 */
public class Dashboard implements EntryPoint, ClickHandler, ReportPrinter {

	private CbVerticalPanel mainPanel;
	private final Button lotCodeManagerButton = new Button("Lot Code Manager");
	private final Button timeClockButton = new Button("Time Clock");
	private final Button timeClockReportButton = new Button("Time Clock Report");
	private final Button trayLabelGeneratorButton = new Button("Tray Label Generator");
	private final Button packagingTransactionManagerButton = new Button("Packaging Transaction Manager");
	private final Button printLabelInventoryReportButton = new Button("Print Label Inventory Report");
	private final Button printScheduleButton = new Button("Print Schedule Report");
	private final Button printTestButton = new Button("Print Test");
	
	private final ListBox printerListBox = new ListBox();


	@Override
	public void onModuleLoad() {
		mainPanel = new CbVerticalPanel()
			.addWidget(newLabel("Chuckanut Bay Internal Web Apps", "titleLabel"), H_ALIGN_CENTER)
			.setCellSpacing(10)
			.setStyle("mainPanel");
		
		for (Button button : newArrayList(lotCodeManagerButton, timeClockButton, timeClockReportButton, trayLabelGeneratorButton, packagingTransactionManagerButton, printLabelInventoryReportButton, printScheduleButton, printTestButton)) {
			button.setStyleName("dashboardButton");
			button.addClickHandler(this);
			mainPanel.addWidget(button, H_ALIGN_CENTER);
		}
		
		//***********************//
		//** Add Printers here **//
		//***********************//
		printerListBox.addItem(HP_WIRELESS_P1102W);
		
		mainPanel.addWidget(printerListBox, H_ALIGN_CENTER);
		
		//Add mainPanel to rootPanel
		RootPanel.get("Dashboard").add(mainPanel);
	}


	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (sender == lotCodeManagerButton) {
			Window.open("LotCodeManager.html", "LotCodeManager", "");
		} else if (sender == timeClockButton) {
			Window.open("TimeClock.html", "TimeClock", "");
		} else if (sender == timeClockReportButton) {
			Window.open("TimeClockReport.html", "TimeClockReport", "");
		} else if (sender == trayLabelGeneratorButton) {
			Window.open("TrayLabelGenerator.html", "TrayLabelGenerator", "");
		} else if (sender == packagingTransactionManagerButton) {
			Window.open("PackagingTransactionManager.html", "PackagingTransactionManager", "");
		} else if (sender == printLabelInventoryReportButton) {
			print(new ReportDto().setName(DIGITAL_LABELS).setSubreport(DIGITAL_LABEL_SUBREPORT));
		} else if (sender == printScheduleButton) {
			print(new ReportDto().setName(SCHEDULE));
		} else if (sender == printTestButton) {
			//Create a ListBox
			ListBox testListBox = new ListBox();
			testListBox.addItem("Testing");
			testListBox.addItem("Testing2");
			
			//Create a TextBox
			TextBox testTextBox = new TextBox();
			
			//Create a ReportDialogBox
			new ReportDialogBox()
				//Have it return the parameters here
				.setReportPrinter(this)
				
				//Tell it what report it is working with
				.setReport(
						//Create the report object
						new ReportDto()
						//Set the report name (Should be a constant from ReportGenerator.java)
						.setName("ReportName")
						//Set a subreport name if needed
						.setSubreport("SubreportName"))
				
				//Add ListBoxes or TextBoxes to collect user input
						//In < > you can specify TextBox or ListBox
						//The parameters for the constructor are label text, parameter name, and the 
				.addParameterComponent(new ReportParameterComponent<ListBox>("ListBox:", "PARAMETER1", testListBox))
				.addParameterComponent(new ReportParameterComponent<TextBox>("TextBox:", "PARAMETER2", testTextBox))
				
				//Put it all together and display the dialog box on the screen
				.build();
		}
	}
	
	public void print(ReportDto report) {
		String selectedPrinter = getSelectedPrinter();
		createReportService().printReport(report, selectedPrinter, createPrintReportCallback(this));
	}


	public void successfulPrint() {
		Window.alert("Your report was successfully printed to the " + getSelectedPrinter());
	}
	
	public String getSelectedPrinter() {
		return printerListBox.getItemText(printerListBox.getSelectedIndex());
	}
}
