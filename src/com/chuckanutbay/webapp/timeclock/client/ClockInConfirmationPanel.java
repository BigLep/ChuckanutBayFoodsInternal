package com.chuckanutbay.webapp.timeclock.client;

import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import static com.chuckanutbay.webapp.common.client.IconUtil.*;

/**
 * Allows an employee to cancel their clock in if there was a mistake.
 * @author mloeppky
 *
 */
public class ClockInConfirmationPanel extends SimplePanel implements ClickHandler{
	
	private ClockInOutErrorHandler errorHandler;
	private EmployeeDto employee;
	private VerticalPanel vPanel = new VerticalPanel();
	private Button incorrectEmployeeButton = createButtonWithIcon(CANCEL, "Not Me");
	private Label employeeWelcomeLabel = new Label();
	
	/**
	 * Constructor that sets up the whole panel and allows employees to cancel their clock in.
	 * @param errorHandler The class to notify if an employee requests a clock in cancellation.
	 * @param employee The employee that is confirming their clock-in.
	 * @param width The width of the panel.
	 * @param height The height of the panel.
	 */
	public ClockInConfirmationPanel(ClockInOutErrorHandler errorHandler, EmployeeDto employee, int width, int height) {
		this.errorHandler = errorHandler;
		this.employee = employee;
		this.setStyleName("clockInConfirmationPanel");
		this.add(vPanel);
		employeeWelcomeLabel.setText("Welcome " + employee.firstName + " " + employee.lastName);
		SimplePanel labelSimplePanel = new SimplePanel(employeeWelcomeLabel);
		labelSimplePanel.setStyleName("welcomeLabel");
		vPanel.add(labelSimplePanel);
		incorrectEmployeeButton.addClickHandler(this);
		incorrectEmployeeButton.setStyleName("incorrectEmployeeButton");
		SimplePanel buttonSimplePanel = new SimplePanel(incorrectEmployeeButton);
		buttonSimplePanel.setStyleName("incorrectEmployeeButtonPanel");
		vPanel.add(buttonSimplePanel);
		this.setPixelSize(width, height);
	}

	@Override
	public void onClick(ClickEvent event) {
		errorHandler.onClockInError(employee.getBarcodeNumber());
	}
}
