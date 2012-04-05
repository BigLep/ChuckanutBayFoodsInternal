package com.chuckanutbay.webapp.dashboard.client;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;

import java.util.List;

import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

public class ReportDialogBox {
	protected DialogBox dialogBox = new DialogBox();
	protected CbVerticalPanel mainPanel = new CbVerticalPanel();
	protected ReportDto report;
	protected ReportPrinter printer;
	protected List<ReportParameterComponent<?>> parameterComponents = newArrayList();
	
	public ReportDialogBox setReportPrinter(ReportPrinter printer) {
		this.printer = printer;
		return this;
	}
	
	public ReportDialogBox setReport(ReportDto reportBase) {
		this.report = reportBase;
		dialogBox.setText(report.getName());
		return this;
	}
	
	public ReportDialogBox addParameterComponent(ReportParameterComponent<?> component) {
		parameterComponents.add(component);
		return this;
	}
	
	public void build() {
		for (ReportParameterComponent<?> component : parameterComponents) {
			mainPanel.addWidget(component.getAsWidget(), ALIGN_CENTER);
		}
		Button submitButton = new Button("Submit");
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (ReportParameterComponent<?> component : parameterComponents) {
					report.addParameter(component.getParameterName(), component.getParameterValue());
				}
				printer.print(report);
				dialogBox.hide();
			}
		});
		mainPanel.addWidget(submitButton, ALIGN_CENTER);
		dialogBox.add(mainPanel);
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		dialogBox.setAutoHideEnabled(true);
		dialogBox.center();
		dialogBox.show();
	}
	

}
