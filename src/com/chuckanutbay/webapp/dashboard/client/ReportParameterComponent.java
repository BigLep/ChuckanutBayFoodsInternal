package com.chuckanutbay.webapp.dashboard.client;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;
import gwthelper.client.ComposedBasicListBox;
import gwthelper.client.ComposedFocusWidget;
import gwthelper.client.ComposedHPanel;
import gwthelper.client.ComposedLabel;
import gwthelper.client.ComposedTextBox;

/**
 * A {@link ReportParameterComponent} is like a line in a form. It is a widget
 * that has a field for user input and a label for the field.
 */
public class ReportParameterComponent<T extends ComposedFocusWidget<?, ?>> extends ComposedHPanel {
	private ComposedLabel parameterLabel;
	private String parameterName;
	private T widget;
	
	/**
	 * Currently only {@link ComposedBasicListBox}s and {@link ComposedTextBox}s are supported.
	 * @param parameterLabelText The text that will be used to label the input widget
	 * @param parameterName The name of the parameter input. Intended to be the parameter name passed to an iReport.
	 * @param widget The widget used to collect input value for the parameter from the user
	 * @throws IllegalArgumentException If widget is not of type ComposedBasicListBox or ComposedTextBox
	 */
	public ReportParameterComponent(String parameterLabelText, String parameterName, T widget) {
		if (widget instanceof ComposedBasicListBox || widget instanceof ComposedTextBox) {
			throw new IllegalArgumentException(widget + " is not of type ComposedBasicListBox or ComposedTextBox");
		}
		this.parameterName = parameterName;
		this.widget = widget;
		parameterLabel = new ComposedLabel()
			.setText(parameterLabelText)
			.setWidth(100);
		this.widget.setWidth(200);
		this
			.addWithAlignment(ALIGN_RIGHT, parameterLabel)
			.addWithAlignment(ALIGN_LEFT, this.widget)
			.setSpacing(5)
			.setWidth(305);
	}
	
	/**
	 * @return The name of the parameter. Intended to be the parameter name passed to an iReport.
	 */
	public String getParameterName() {
		return parameterName;
	}
	
	/**
	 * @return The value of the parameter. Intended to be the value of the parameter passed to an iReport.
	 */
	public Object getParameterValue() {
		if (widget instanceof ComposedBasicListBox) {
			ComposedBasicListBox lb = (ComposedBasicListBox) widget;
			return lb.getSelectedValue();
		} else if (widget instanceof ComposedTextBox){
			ComposedTextBox tb = (ComposedTextBox) widget;
			return tb.getText();
		} else {
			return null;
		}
	}

}
