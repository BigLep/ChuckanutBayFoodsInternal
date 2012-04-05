package com.chuckanutbay.webapp.dashboard.client;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import com.chuckanutbay.webapp.common.client.CbHorizontalPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 *
 * @param <T> Only {@link TextBox} and {@link ListBox} are supported currently
 */
public class ReportParameterComponent<T extends FocusWidget> {
	private CbHorizontalPanel mainPanel = new CbHorizontalPanel();
	private Label parameterLabel;
	private String parameterName;
	private T widget;
	
	public ReportParameterComponent(String parameterLabelText, String parameterName, T widget) {
		this.parameterName = parameterName;
		this.widget = widget;
		parameterLabel = new Label(parameterLabelText);
		parameterLabel.setWidth("100px");
		this.widget.setWidth("200px");
		mainPanel
			.addWidget(parameterLabel, ALIGN_RIGHT)
			.addWidget(this.widget, ALIGN_LEFT)
			.setCellSpacing(5)
			.setWidth(305);
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public Object getParameterValue() {
		if (widget instanceof ListBox) {
			ListBox lb = (ListBox) widget;
			return lb.getItemText(lb.getSelectedIndex());
		} else if (widget instanceof TextBox){
			TextBox tb = (TextBox) widget;
			return tb.getText();
		} else {
			return null;
		}
	}
	
	public CbHorizontalPanel getAsWidget() {
		return mainPanel;
	}

}
