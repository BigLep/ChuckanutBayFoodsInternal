package com.chuckanutbay.webapp.dashboard.client;

import static com.chuckanutbay.reportgeneration.Print.HP_WIRELESS_P1102W;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createReportService;
import static com.chuckanutbay.webapp.common.shared.ReportDto.DIGITAL_LABELS;
import static com.chuckanutbay.webapp.common.shared.ReportDto.DIGITAL_LABEL_SUBREPORT;
import static com.chuckanutbay.webapp.common.shared.ReportDto.SCHEDULE;
import static com.chuckanutbay.webapp.dashboard.client.RpcHelper.createPrintReportCallback;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import gwthelper.client.ComposedButton;
import gwthelper.client.ComposedLabel;
import gwthelper.client.ComposedListBox;
import gwthelper.client.ComposedRootPanel;
import gwthelper.client.ComposedVPanel;

import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Serves as a dashboard to access the various applications.
 */
public class Dashboard implements EntryPoint, ClickHandler, ReportPrinter {

	private ComposedVPanel mainPanel;
	private ComposedVPanel reportsSubPanel;
	private final ComposedButton lotCodeManagerButton = new ComposedButton().setText("Lot Code Manager");
	private final ComposedButton timeClockButton = new ComposedButton().setText("Time Clock");
	private final ComposedButton timeClockReportButton = new ComposedButton().setText("Time Clock Report");
	private final ComposedButton trayLabelGeneratorButton = new ComposedButton().setText("Tray Label Generator");
	private final ComposedButton packagingTransactionManagerButton = new ComposedButton().setText("Packaging Transaction Manager");
	private final ComposedButton labelInventoryReportButton = new ComposedButton().setText("Label Inventory");
	private final ComposedButton scheduleButton = new ComposedButton().setText("Schedule");
	private final ComposedButton testButton = new ComposedButton().setText("Test");
	
	private final ComposedListBox<String> printerListBox = new ComposedListBox<String>();


	@Override
	public void onModuleLoad() {
		mainPanel = new ComposedVPanel()
			.addWithAlignment(ALIGN_CENTER, new ComposedLabel().setText("Chuckanut Bay Internal Web Apps").setStyleName("titleLabel"))
			.setSpacing(10)
			.setStyleName("mainPanel");
		
		for (ComposedButton button : newArrayList(lotCodeManagerButton, timeClockButton, timeClockReportButton, trayLabelGeneratorButton, packagingTransactionManagerButton)) {
			button
				.setStyleName("dashboardButton")
				.addHandler(this);
			mainPanel
				.addWithAlignment(ALIGN_CENTER, button);
		}
		
		reportsSubPanel = new ComposedVPanel()
			.addWithAlignment(ALIGN_CENTER, new ComposedLabel().setText("Print Report:").setStyleName("titleLabel"))
			.setSpacing(10)
			.setStyleName("subPanel");
		mainPanel.addWithAlignment(ALIGN_CENTER, reportsSubPanel);
		
		for (ComposedButton button : newArrayList(labelInventoryReportButton, scheduleButton, testButton)) {
			button
				.setStyleName("dashboardButton")
				.addHandler(this);
			reportsSubPanel
				.addWithAlignment(ALIGN_CENTER, button);
		}
		
		//***********************//
		//** Add Printers here **//
		//***********************//
		printerListBox.addItem(HP_WIRELESS_P1102W, HP_WIRELESS_P1102W);
		
		reportsSubPanel.addWithAlignment(ALIGN_CENTER, printerListBox);
		
		//Add mainPanel to rootPanel
		new ComposedRootPanel("Dashboard").add(mainPanel);
	}


	@Override
	public void onClick(ClickEvent event) {
		Object sender = event.getSource();
		if (lotCodeManagerButton.equals(sender)) {
			Window.open("LotCodeManager.html", "LotCodeManager", "");
		} else if (timeClockButton.equals(sender)) {
			Window.open("TimeClock.html", "TimeClock", "");
		} else if (timeClockReportButton.equals(sender)) {
			Window.open("TimeClockReport.html", "TimeClockReport", "");
		} else if (timeClockReportButton.equals(sender)) {
			Window.open("TrayLabelGenerator.html", "TrayLabelGenerator", "");
		} else if (packagingTransactionManagerButton.equals(sender)) {
			Window.open("PackagingTransactionManager.html", "PackagingTransactionManager", "");
		} else if (labelInventoryReportButton.equals(sender)) {
			print(new ReportDto().setName(DIGITAL_LABELS).setSubreport(DIGITAL_LABEL_SUBREPORT));
		} else if (scheduleButton.equals(sender)) {
			print(new ReportDto().setName(SCHEDULE));
		} else if (testButton.equals(sender)) {
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
		return printerListBox.getValue(printerListBox.getSelectedIndex());
	}
}
