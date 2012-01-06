package com.chuckanutbay.webapp.timeclock.client;

import static com.chuckanutbay.webapp.common.client.IconUtil.CANCEL;
import static com.chuckanutbay.webapp.common.client.IconUtil.newButtonWithIcon;

import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Allows an employee to cancel their clock in if there was a mistake.
 * This panel is intended to be displayed after an employee has clocked-in, to
 * cancel the clock-in if there was a mistake.
 * <p>
 * The panel style is defined by "clockInConfirmationPanel", "welcomeLabel", 
 * "incorrectEmployeeButton", and "incorrectEmployeeButtonPanel" in the css file
 */
public class ClockInCancelationPanel extends SimplePanel {
	
	/**
	 * Constructor that sets up the whole panel and allows employees to cancel their clock in.
	 * @param errorHandler The class to notify if an employee requests a clock in cancellation.
	 * @param employee The employee that is confirming their clock-in.
	 * @param width The width of the panel.
	 * @param height The height of the panel.
	 */
	public ClockInCancelationPanel(final ClockInOutErrorHandler errorHandler, final EmployeeDto employee, int width, int height) {	
		//Setup panel contents
		VerticalPanel vPanel = new VerticalPanel();
			//To create the welcome label:
			//Create a label and set the text
			Label employeeWelcomeLabel = new Label();
			employeeWelcomeLabel.setText("Welcome " + employee.firstName + " " + employee.lastName);
			//Create a panel, add the label to it, and set the style
			SimplePanel labelSimplePanel = new SimplePanel(employeeWelcomeLabel);
			labelSimplePanel.setStyleName("welcomeLabel");
		vPanel.add(labelSimplePanel);
		Button incorrectEmployeeButton = newButtonWithIcon(CANCEL, "Not Me");
		incorrectEmployeeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				errorHandler.onClockInError(employee.getBarcodeNumber());
			}
		});
		incorrectEmployeeButton.setStyleName("incorrectEmployeeButton");
		SimplePanel buttonSimplePanel = new SimplePanel(incorrectEmployeeButton);
		buttonSimplePanel.setStyleName("incorrectEmployeeButtonPanel");
		vPanel.add(buttonSimplePanel);
		
		//Setup panel
		add(vPanel);
		setStyleName("clockInConfirmationPanel");
		setPixelSize(width, height);
	}
}
