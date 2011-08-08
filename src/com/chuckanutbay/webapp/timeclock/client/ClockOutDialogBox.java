package com.chuckanutbay.webapp.timeclock.client;

import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.getLastChar;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.removeLastChar;
import static com.chuckanutbay.webapp.timeclock.client.TimeClockUtil.stringToInt;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A {@link DialogBox} that allows employees to enter the percentage of time that they worked on each activity before clocking out.
 *
 */
public class ClockOutDialogBox extends DialogBox {
	
	private ClockInOutErrorHandler errorHandler;
	private ClockInOutServerCommunicator serverCommunicator;
	private EmployeeDto employee;
	private FlexTable flexTable;
	private FocusPanel focusPanel = new FocusPanel();
	private int xPosition;
	private int yPosition;
	private List<EmployeeWorkIntervalActivityPercentageDto> actvityPercentages = new ArrayList<EmployeeWorkIntervalActivityPercentageDto>();
	private ListBox totalListBox;
	private Button clockOutButton;
	private int width;
	private int height;
	
	public ClockOutDialogBox(EmployeeDto employee, 
			SortedSet<ActivityDto> activities, 
			ClockInOutErrorHandler errorHandler, 
			ClockInOutServerCommunicator serverCommunicator, 
			int x, int y, int width, int height) {
		
		GWT.log("ClockOutDialogBox: Initializing");
		
		//For each activity, create a new EmployeeWorkIntervalPercentageDto and set the percentage to 0.
		for (ActivityDto activity : activities) {
			GWT.log("ClockOutDialogBox:       " + activity.getName() + ", 0");
			actvityPercentages.add(new EmployeeWorkIntervalActivityPercentageDto(null, activity, 0));
		}
				
		//Change the percentages to what the employee did last work interval.
		for (EmployeeWorkIntervalActivityPercentageDto percentage : employee.getEmployeeWorkIntervalPercentages()) {
			for (EmployeeWorkIntervalActivityPercentageDto percentageToCheck : actvityPercentages) {
				if (percentage.getActivity().equals(percentageToCheck.getActivity())) {
					GWT.log("ClockOutDialogBox: Changing " + percentage.getActivity().getName() + " to " + percentage.getPercentage());
					percentageToCheck.setPercentage(percentage.getPercentage());
				}
			}
		}
		
		this.employee = employee;
		this.errorHandler = errorHandler;
		this.serverCommunicator = serverCommunicator;
		this.xPosition = x;
		this.yPosition = y;
		this.width = width;
		this.height = height;
		setupDialogBox();
		setupFlexTable();
		SimplePanel mainPanel = new SimplePanel();
		mainPanel.setPixelSize(this.width, height);
		mainPanel.add(flexTable);
		focusPanel.add(mainPanel);
		this.setWidget(focusPanel);
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				GWT.log("ASCII code: " + event.getNativeKeyCode());
				final int ENTER = 13;
				if (event.getNativeKeyCode() == ENTER) {
					clockOutButton.click();
				}
			}
		});
	}
	
	@Override
	public void show() {
		super.show();
		Timer timer = new Timer() {
			@Override
			public void run() {
				focusPanel.setFocus(true);
			}
		};
		timer.schedule(100);
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
		flexTable.setWidth(width + "px");
		flexTable.setStyleName("clockOutDialogBoxFlexTable");
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.setCellPadding(0);
		flexTable.setCellSpacing(0);
		
		//Setup titleLabel
		Label titleLabel = new Label(employee.firstName + " " + employee.lastName + " - Sign Out");
		titleLabel.setStyleName("clockOutTitleLabel");
		flexTable.setWidget(0, 0, titleLabel);
		
		//Setup activity labels and listboxes
		int i = 1;
		for(EmployeeWorkIntervalActivityPercentageDto activityPercentage : actvityPercentages) {
			Label label = new Label(activityPercentage.getActivity().getName());
			label.setStyleName("clockOutDialogBoxLabel");
			flexTable.setWidget(i, 0, label);
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
					Integer selectedInteger = new Integer(removeLastChar(selectedString));
					EmployeeWorkIntervalActivityPercentageDto percentageDto = actvityPercentages.get(stringToInt(getLastChar(listBox.getTitle())) - 1);
					percentageDto.setPercentage(selectedInteger);
					GWT.log("ClockOutDialogBox: Changed " + percentageDto.getActivity().getName() + " percentage to " + selectedInteger);
					updateTotalPercentageListBox();
					focusPanel.setFocus(true);
				}
			});
			listBox.setItemSelected(activityPercentage.getPercentage()/20, true);
			flexTable.setWidget(i, 1, listBox);
			i++;
		}
		
		//Setup total label and listbox.
		Label label = new Label("Total");
		label.setStyleName("clockOutDialogBoxTotalLabel");
		flexTable.setWidget(i, 0, label);
		
		totalListBox = new ListBox();
		flexTable.setWidget(i, 1, totalListBox);
		i++;
		
		//Setup Clock-Out and Cancel buttons;
		clockOutButton = new Button("Clock-Out");
		clockOutButton.setStyleName("clockOutDialogBoxButton");
		clockOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				savePercentages();
				GWT.log("ClockOutDialogBox: Requesting Clock-Out for " + employee.getFirstName() + " " + employee.getLastName());
				for (EmployeeWorkIntervalActivityPercentageDto activityPercentage : employee.getEmployeeWorkIntervalPercentages()) {
					GWT.log("ClockOutDialogBox:      " + activityPercentage.getActivity().getName() + " " + activityPercentage.getPercentage());
				}
				serverCommunicator.clockOutOnDatabase(employee);
			}
		});
		flexTable.setWidget(i, 0, clockOutButton);
		flexTable.getCellFormatter().setAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setStyleName("clockOutDialogBoxButton");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				errorHandler.onClockOutError(employee);
			}
		});
		flexTable.setWidget(i, 1, cancelButton);
		flexTable.getCellFormatter().setAlignment(i, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
		updateTotalPercentageListBox();
	}
	
	private void savePercentages() {
		List<EmployeeWorkIntervalActivityPercentageDto> percentages = new ArrayList<EmployeeWorkIntervalActivityPercentageDto>();
		for (EmployeeWorkIntervalActivityPercentageDto percentage : actvityPercentages) {
			if (percentage.getPercentage() > 0) {
				percentages.add(percentage);
			}
		}
		employee.setEmployeeWorkIntervalPercentages(percentages);
	}

	/**
	 * Re-evauluates the total percentage and determines if the clock out button should be enabled.
	 */
	private void updateTotalPercentageListBox() {
		Integer totalPercentage = calcTotalPercentage();
		if (totalListBox.getItemCount() > 0) {
			totalListBox.setItemText(0, totalPercentage + "%");
		} else {
			totalListBox.addItem(totalPercentage + "%");
		}
		clockOutButton.setEnabled(totalPercentage == 100);
	}
	
	/**
	 * Finds the sum of the percentages for each activity.
	 * @return The sum of the percentages.
	 */
	private int calcTotalPercentage() {
		int totalPercentage = 0;
		for (EmployeeWorkIntervalActivityPercentageDto percentage : actvityPercentages) {
			totalPercentage += percentage.getPercentage();
		}
		GWT.log("ClockOutDialogBox: Total percentage is " + totalPercentage);
		return totalPercentage;
	}
}
