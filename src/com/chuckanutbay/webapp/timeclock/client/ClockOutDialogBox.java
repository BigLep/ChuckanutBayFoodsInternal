package com.chuckanutbay.webapp.timeclock.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalPercentageDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;

public class ClockOutDialogBox extends DialogBox {
	
	private ClockInOutErrorHandler errorHandler;
	private ClockInOutServerCommunicator serverCommunicator;
	private EmployeeDto employee;
	private FlexTable flexTable;
	private int xPosition;
	private int yPosition;
	private List<EmployeeWorkIntervalPercentageDto> actvityPercentages = new ArrayList<EmployeeWorkIntervalPercentageDto>();
	private ListBox totalListBox;
	private Button clockOutButton;
	
	public ClockOutDialogBox(EmployeeDto employee, 
			Set<ActivityDto> activities, 
			ClockInOutErrorHandler errorHandler, 
			ClockInOutServerCommunicator serverCommunicator, 
			int x, int y, int width, int height) {
		GWT.log("ClockOutDialogBox: Initializing");
		this.employee = employee;
		
		//Find each activity and percentage that the employee did last time.
		for (EmployeeWorkIntervalPercentageDto percentage : employee.getEmployeeWorkIntervalPercentages()) {
			GWT.log("ClockOutDialogBox: " + percentage.getActivity().getName() + ", " + percentage.getPercentage());
			actvityPercentages.add(percentage);
		}
		
		//Find each activity that the employee din't do last time and set the percentage to 0
		for (ActivityDto activity : activities) {
			boolean foundMatch = false;
			for (EmployeeWorkIntervalPercentageDto percentage : actvityPercentages) {
				if (percentage.getActivity().equals(activity)) {
					foundMatch = true;
					break;
				}
			} if (!foundMatch) {
				GWT.log("ClockOutDialogBox: " + activity.getName() + ", 0");
				actvityPercentages.add(new EmployeeWorkIntervalPercentageDto(null, activity, 0));
			}
		}
		
		this.errorHandler = errorHandler;
		this.serverCommunicator = serverCommunicator;
		this.xPosition = x;
		this.yPosition = y;
		setupDialogBox();
		setupFlexTable();
		SimplePanel mainPanel = new SimplePanel();
		mainPanel.setPixelSize(width-4, height);
		flexTable.setWidth((width-4) + "px");
		mainPanel.add(flexTable);
		this.setWidget(mainPanel);
	}

	private void setupDialogBox() {
		this.setGlassStyleName("clockOutDialogBoxGlass");
		this.setStyleName("clockOutDialogBox");
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setPopupPosition(xPosition, yPosition);
		this.hide();
	}

	private void setupFlexTable() {
		flexTable = new FlexTable();
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.setCellPadding(0);
		flexTable.setCellSpacing(00);
		Label titleLabel = new Label(employee.firstName + " " + employee.lastName + " - Sign Out");
		titleLabel.setStyleName("clockOutTitleLabel");
		flexTable.setWidget(0, 0, titleLabel);
		int i = 0;
		for(EmployeeWorkIntervalPercentageDto activityPercentage : actvityPercentages) {
			Label label = new Label(activityPercentage.getActivity().getName());
			label.setStyleName("clockOutDialogBoxLabel");
			flexTable.setWidget(i+1, 0, label);
			ListBox listBox = new ListBox();
			listBox.addItem("0%");
			listBox.addItem("20%");
			listBox.addItem("40%");
			listBox.addItem("60%");
			listBox.addItem("80%");
			listBox.addItem("100%");
			listBox.setStyleName("clockOutDialogBoxListBox");
			listBox.setTitle("ListBox: " + i);
			listBox.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					ListBox listBox = ((ListBox) event.getSource());
					String selectedString = listBox.getItemText(listBox.getSelectedIndex());
					Integer selectedInteger = new Integer(selectedString.substring(0, selectedString.length()-1));
					EmployeeWorkIntervalPercentageDto percentageDto = actvityPercentages.get(new Integer(listBox.getTitle().substring(listBox.getTitle().length() -1)));
					percentageDto.setPercentage(selectedInteger);
					GWT.log("ClockOutDialogBox: Changed " + percentageDto.getActivity().getName() + " percentage to " + selectedInteger);
					totalListBox.setItemText(0, calcTotalPercentage() + "%");
					clockOutButton.setEnabled(calcTotalPercentage() == 100);
				}
			});
			listBox.setItemSelected(activityPercentage.getPercentage()/20, true);
			flexTable.setWidget(i+1, 1, listBox);
			i++;
		}
		Label label = new Label("Total");
		label.setStyleName("clockOutDialogBoxTotalLabel");
		flexTable.setWidget(i+1, 0, label);
		totalListBox = new ListBox();
		totalListBox.addItem(calcTotalPercentage() + "%");
		flexTable.setWidget(i+1, 1, totalListBox);
		clockOutButton = new Button("Clock-Out");
		clockOutButton.setStyleName("clockOutDialogBoxButton");
		clockOutButton.setEnabled(calcTotalPercentage() == 100);
		clockOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				serverCommunicator.clockOutOnDatabase(employee);
			}
		});
		flexTable.setWidget(i+2, 0, clockOutButton);
		flexTable.getCellFormatter().setAlignment(i+2, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		Button cancelButton = new Button("Cancel");
		cancelButton.setStyleName("clockOutDialogBoxButton");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				errorHandler.onClockOutError(employee);
			}
		});
		flexTable.setWidget(i+2, 1, cancelButton);
		flexTable.getCellFormatter().setAlignment(i+2, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
	}

	private int calcTotalPercentage() {
		int totalPercentage = 0;
		for (EmployeeWorkIntervalPercentageDto percentage : actvityPercentages) {
			totalPercentage += percentage.getPercentage();
		}
		GWT.log("ClockOutDialogBox: Total percentage is " + totalPercentage);
		return totalPercentage;
	}
}
