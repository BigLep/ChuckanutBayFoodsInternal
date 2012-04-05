package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;

public class EmployeeWorkIntervalDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public Date startDateTime;
	public Date endDateTime;
	public double hoursWorked;
	public String comment;
	
	public EmployeeWorkIntervalDto(){}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public double getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	
	public void addHours(double hours) {
		hoursWorked += hours;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
