package com.chuckanutbay.webapp.dashboard.client;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import gwthelper.client.ComposedButton;
import gwthelper.client.ComposedDialogBox;
import gwthelper.client.ComposedVPanel;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.ReportDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Form for collecting input parameters for iReport.
 */
public class ReportDialogBox {
	protected ComposedDialogBox dialogBox = new ComposedDialogBox();
	protected ComposedVPanel mainPanel = new ComposedVPanel();
	protected ReportDto report;
	protected ReportPrinter printer;
	protected List<ReportParameterComponent<?>> parameterComponents = newArrayList();
	
	/**
	 * @param printer The printer to send the report to.
	 */
	public ReportDialogBox setReportPrinter(ReportPrinter printer) {
		this.printer = printer;
		return this;
	}
	
	/**
	 * @param reportBase The report to add parameters to. The report name will be
	 * used as the title for the dialog box.
	 * @return this for chaining
	 */
	public ReportDialogBox setReport(ReportDto reportBase) {
		this.report = reportBase;
		dialogBox.setText(report.getName());
		return this;
	}
	
	/**
	 * @param component A widget to act as an input source for a parameter.
	 * @return this for chaining
	 */
	public ReportDialogBox addParameterComponent(ReportParameterComponent<?> component) {
		parameterComponents.add(component);
		return this;
	}
	
	/**
	 * Builds the dialog box and displays it on the screen.
	 */
	public void build() {
		for (ReportParameterComponent<?> component : parameterComponents) {
			mainPanel.addWithAlignment(ALIGN_CENTER, component);
		}
		ComposedButton submitButton = new ComposedButton().setText("Submit");
		submitButton.addHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (ReportParameterComponent<?> component : parameterComponents) {
					report.addParameter(component.getParameterName(), component.getParameterValue());
				}
				printer.print(report);
				dialogBox.hide();
			}
		});
		mainPanel.addWithAlignment(ALIGN_CENTER, submitButton);
		dialogBox.add(mainPanel)
			.setGlassEnabled(true)
			.setAnimationEnabled(true)
			.setAutoHideEnabled(true)
			.center()
			.show();
	}
	

}
