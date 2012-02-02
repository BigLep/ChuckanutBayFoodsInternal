package com.chuckanutbay.webapp.timeclock.client;

import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newLabel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newSimplePanel;
import static com.chuckanutbay.webapp.common.client.IconUtil.CANCEL;
import static com.chuckanutbay.webapp.common.client.IconUtil.newButtonWithIcon;

import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.chuckanutbay.webapp.common.client.GwtWidgetHelper;
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
 * The panel uses the following syles: <br>
 * {@link CLOCK_IN_CONFIRMATION_PANEL_STYLE} <br>
 * {@link WELCOME_LABEL_STYLE} <br>
 * {@link INCORRECT_EMPLOYEE_BUTTON_STYLE} <br>
 * {@link INCORRECT_EMPLOYEE_BUTTON_PANEL_STYLE} <br>
 */
public class ClockInCancelationPanel extends SimplePanel {
	
	//  Style Constants
	public static final String CLOCK_IN_CONFIRMATION_PANEL_STYLE = "clockInConfirmationPanel";
	public static final String WELCOME_LABEL_STYLE = "welcomeLabel";
	public static final String INCORRECT_EMPLOYEE_BUTTON_STYLE = "incorrectEmployeeButton";
	public static final String INCORRECT_EMPLOYEE_BUTTON_PANEL_STYLE = "incorrectEmployeeButtonPanel";
	
	/**
	 * Constructor that sets up the whole panel and allows employees to cancel their clock in.
	 * @param errorHandler The class to notify if an employee requests a clock in cancellation.
	 * @param employee The employee that is confirming their clock-in.
	 * @param width The width of the panel.
	 * @param height The height of the panel.
	 */
	public ClockInCancelationPanel(final ClockInOutErrorHandler errorHandler, final EmployeeDto employee, int width, int height) {
		
		// Setup incorrectEmployeeButton
		Button incorrectEmployeeButton = newButtonWithIcon(CANCEL, "Not Me");
		incorrectEmployeeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				errorHandler.onClockInError(employee.getBarcodeNumber());
			}
		});
		incorrectEmployeeButton.setStyleName("INCORRECT_EMPLOYEE_BUTTON_STYLE");
		
		// Put it all together
		add(new CbVerticalPanel()
			// Add employee welcome label
			.addWidget(newSimplePanel(newLabel("Welcome " + employee.firstName + " " + employee.lastName), WELCOME_LABEL_STYLE))
			// Add incorrectEmployeeButton
			.addWidget(newSimplePanel(incorrectEmployeeButton, INCORRECT_EMPLOYEE_BUTTON_PANEL_STYLE))
			.setStyle(CLOCK_IN_CONFIRMATION_PANEL_STYLE)
			.setSize(width, height));
	}
}
